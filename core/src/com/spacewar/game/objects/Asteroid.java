package com.spacewar.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.spacewar.game.tools.CollisionRect;

import static com.spacewar.game.SpaceWarGame.WINDOW_HEIGHT;

public class Asteroid {

    private static final int SPEED = 250;
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;

    private Texture texture;
    private Vector2 position = new Vector2();
    private boolean toRemove = false;
    private CollisionRect collisionRect;

    public Asteroid(float positionX) {
        this.position.x = positionX;
        this.position.y = WINDOW_HEIGHT;
        texture = new Texture("asteroid.png");
        collisionRect = new CollisionRect(position, WIDTH, HEIGHT);
    }

    public void update(float delta) {
        position.y -= SPEED * delta;
        if (position.y < -HEIGHT) {
            toRemove = true;
        }
        collisionRect.move(position);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, WIDTH, HEIGHT);
    }

    public boolean isToRemove() {
        return toRemove;
    }

    public CollisionRect getCollisionRect() {
        return collisionRect;
    }

    public Vector2 getPosition() {
        return position;
    }
}
