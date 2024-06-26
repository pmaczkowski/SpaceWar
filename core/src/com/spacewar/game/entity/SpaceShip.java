package com.spacewar.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.spacewar.game.SpaceWarGame;

import static com.spacewar.game.SpaceWarGame.WINDOW_WIDTH;
import static java.lang.Math.abs;

public class SpaceShip {

    private static final float SPEED = 300;
    private static final float ANIMATION_SPEED = 0.5f;
    private static final float ROLL_TIMER_SWITCH_TIME = 0.25f;
    private static final int SHIP_WIDTH_PIXEL = 17;
    private static final int SHIP_HEIGHT_PIXEL = 32;
    private static final float SHIP_WIDTH = (float) SHIP_WIDTH_PIXEL * 3;
    private static final float SHIP_HEIGHT = (float) SHIP_HEIGHT_PIXEL * 3;
    private static final float SHIP_Y = 15;

    private Animation<TextureRegion>[] shipAnimations;
    private int roll;
    private float stateTime;
    private float rollTimer;

    private final Vector2 position;

    public SpaceShip() {
        this.position = new Vector2((WINDOW_WIDTH - SHIP_WIDTH) / 2, SHIP_Y);
        this.roll = 2;
        this.rollTimer = 0;
        this.shipAnimations = new Animation[5];

        TextureRegion[][] shipTextures = TextureRegion.split(new Texture("ship.png"), SHIP_WIDTH_PIXEL, SHIP_HEIGHT_PIXEL);

        this.shipAnimations[0] = new Animation<>(ANIMATION_SPEED, shipTextures[2]);
        this.shipAnimations[1] = new Animation<>(ANIMATION_SPEED, shipTextures[1]);
        this.shipAnimations[2] = new Animation<>(ANIMATION_SPEED, shipTextures[0]);
        this.shipAnimations[3] = new Animation<>(ANIMATION_SPEED, shipTextures[3]);
        this.shipAnimations[4] = new Animation<>(ANIMATION_SPEED, shipTextures[4]);
    }

    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            goLeft();
        } else {
            if (roll < 2) {
                rollTimer += Gdx.graphics.getDeltaTime();
                if (abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4) {
                    rollTimer = 0;
                    roll++;
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            goRight();
        } else {
            if (roll > 2) {
                rollTimer -= Gdx.graphics.getDeltaTime();
                if (abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) {
                    rollTimer = 0;
                    roll--;
                }
            }
        }

        stateTime += delta;
    }

    public void render() {
        SpaceWarGame.batch.draw(shipAnimations[roll].getKeyFrame(stateTime, true), position.x, position.y, SHIP_WIDTH, SHIP_HEIGHT);
    }

    public void dispose() {
        // Dispose resources if needed
    }

    private void goLeft() {
        position.x -= SPEED * Gdx.graphics.getDeltaTime();
        if (position.x < 0) {
            position.x = 0;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT) && roll > 0) {
            rollTimer = 0;
            roll--;
        }
        rollTimer -= Gdx.graphics.getDeltaTime();
        if (abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) {
            rollTimer = 0;
            roll--;
        }
    }

    private void goRight() {
        position.x += SPEED * Gdx.graphics.getDeltaTime();
        if (position.x > WINDOW_WIDTH - SHIP_WIDTH) {
            position.x = WINDOW_WIDTH - SHIP_WIDTH;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT) && roll < 4) {
            rollTimer = 0;
            roll++;
        }
        rollTimer += Gdx.graphics.getDeltaTime();
        if (abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4) {
            rollTimer = 0;
            roll++;
        }
    }

}
