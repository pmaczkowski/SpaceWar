package com.spacewar.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Explosion {

    private static final float FRAME_LENGTH = 0.2f;
    private static final int SIZE = 32;

    private static Animation<TextureRegion> animation = new Animation<>(FRAME_LENGTH, TextureRegion.split(new Texture("explosion.png"), SIZE, SIZE)[0]);
    private Vector2 position;
    private float stateTime = 0;

    private boolean toRemove = false;

    public Explosion(Vector2 position) {
        this.position = position;
    }

    public void update(float delta) {
        stateTime += delta;
        if (animation.isAnimationFinished(stateTime)) {
            toRemove = true;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(animation.getKeyFrame(stateTime), position.x, position.y);
    }

    public boolean isToRemove() {
        return toRemove;
    }

}
