package com.spacewar.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import static com.spacewar.game.SpaceWarGame.WINDOW_HEIGHT;

public class Asteroid {

    private static final int SPEED = 250;
    private static final int WIDTH = 16;
    private static final int HEIGHT = 16;
    private static final int DEFAULT_Y = WINDOW_HEIGHT - 20;

    private Texture texture;
    private Vector2 position;
    private boolean toRemove = false;

    private Random rand = new Random();

    public Asteroid() {
        position = new Vector2(rand.nextInt(Gdx.graphics.getWidth() - WIDTH), DEFAULT_Y);
        texture = new Texture("asteroid.png");
    }

    public void update(float delta) {
        position.y -= SPEED * delta;
        if (position.y < -HEIGHT) {
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
