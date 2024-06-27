package com.spacewar.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.spacewar.game.SpaceWarGame;
import com.spacewar.game.tools.CollisionRect;

import java.util.ArrayList;
import java.util.List;

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
    private CollisionRect collisionRect;

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

        collisionRect = new CollisionRect(position, SHIP_WIDTH, SHIP_HEIGHT);
    }

    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            goLeft();
        } else {
            if (roll < 2) {
                rollTimer += Gdx.graphics.getDeltaTime();
                if (abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4) {
                    rollTimer -= ROLL_TIMER_SWITCH_TIME;
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
                    rollTimer -= ROLL_TIMER_SWITCH_TIME;
                    roll--;
                }
            }
        }
        stateTime += delta;
        collisionRect.move(position);
    }

    public void render() {
        SpaceWarGame.batch.draw(shipAnimations[roll].getKeyFrame(stateTime, true), position.x, position.y, SHIP_WIDTH, SHIP_HEIGHT);
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
            rollTimer -= ROLL_TIMER_SWITCH_TIME;
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
            rollTimer -= ROLL_TIMER_SWITCH_TIME;
            roll++;
        }
    }

    public List<Bullet> shoot() {
        int offset = 4;
        if (roll == 1 || roll == 3) {
            offset = 8;
        }
        if (roll == 0 || roll == 4) {
            offset = 16;
        }
        ArrayList<Bullet> newBullets = new ArrayList<>();
        newBullets.add(new Bullet(position.x + offset));
        newBullets.add(new Bullet(position.x + SHIP_WIDTH - offset));
        return newBullets;
    }

    public CollisionRect getCollisionRect() {
        return collisionRect;
    }

}
