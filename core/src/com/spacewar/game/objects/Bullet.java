package com.spacewar.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bullet {

    private static final int SPEED = 500;
    private static final int DEFAULT_Y = 40;
    private Texture texture;

    private Vector2 position;

    private boolean toRemove = false;

    public Bullet(float positionX) {
        this.position = new Vector2(positionX, DEFAULT_Y);
        texture = new Texture("Bullet.png");
    }

    public void update(float delta) {
        position.y += SPEED * delta;
        if (position.y > Gdx.graphics.getHeight()) {
            toRemove = true;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public boolean isToRemove() {
        return toRemove;
    }

}
