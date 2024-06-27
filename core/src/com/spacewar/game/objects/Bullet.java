package com.spacewar.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.spacewar.game.tools.CollisionRect;

public class Bullet {

    private static final int SPEED = 500;
    private static final int WIDTH = 3;
    private static final int HEIGHT = 12;
    private static final int DEFAULT_Y = 40;

    private Texture texture;
    private Vector2 position;
    private boolean toRemove = false;
    private CollisionRect collisionRect;

    public Bullet(float positionX) {
        this.position = new Vector2(positionX, DEFAULT_Y);
        texture = new Texture("Bullet.png");
        collisionRect = new CollisionRect(position, WIDTH, HEIGHT);
    }

    public void update(float delta) {
        position.y += SPEED * delta;
        if (position.y > Gdx.graphics.getHeight()) {
            toRemove = true;
        }
        collisionRect.move(position);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public boolean isToRemove() {
        return toRemove;
    }

    public CollisionRect getCollisionRect() {
        return collisionRect;
    }

}
