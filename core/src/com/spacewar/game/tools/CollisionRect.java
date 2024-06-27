package com.spacewar.game.tools;

import com.badlogic.gdx.math.Vector2;

public class CollisionRect {

    private Vector2 position;
    private int width;
    private int height;

    public CollisionRect(Vector2 position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public void move(Vector2 position) {
        this.position = position;
    }

    public boolean collidesWith(CollisionRect rect) {
        return position.x < rect.position.x + rect.width
                && position.y < rect.position.y + rect.height
                && position.x + width > rect.position.x
                && position.y + height > rect.position.y;
    }

}
