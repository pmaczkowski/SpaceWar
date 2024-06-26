package com.spacewar.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.spacewar.game.SpaceWarGame;
import com.spacewar.game.objects.Asteroid;
import com.spacewar.game.objects.SpaceShip;

import java.util.ArrayList;
import java.util.Random;


public class MainGameScreen implements Screen {

    private static final float MIN_ASTEROID_SPAWN_TIME = 0.3f;
    private static final float MAX_ASTEROID_SPAWN_TIME = 0.6f;

    private SpaceShip spaceShip;

    private Random rand;
    private float asteroidSpawnTimer;
    private ArrayList<Asteroid> asteroids = new ArrayList<>();


    public MainGameScreen() {
        this.spaceShip = new SpaceShip();
        rand = new Random();
        asteroidSpawnTimer = rand.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;
    }

    @Override
    public void show() {
        //
    }

    @Override
    public void render(float delta) {
        asteroidSpawnTimer -= delta;
        if (asteroidSpawnTimer <= 0) {
            asteroidSpawnTimer = rand.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;
            asteroids.add(new Asteroid());
        }
        ArrayList<Asteroid> asteroidsToRemove = new ArrayList<>();
        for (Asteroid asteroid : asteroids) {
            asteroid.update(delta);
            if (asteroid.isToRemove()) {
                asteroidsToRemove.add(asteroid);
            }
        }
        asteroids.removeAll(asteroidsToRemove);


        spaceShip.update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        SpaceWarGame.batch.begin();
        spaceShip.render();
        for (Asteroid asteroid : asteroids) {
            asteroid.render(SpaceWarGame.batch);
        }
        SpaceWarGame.batch.end();

    }

    @Override
    public void resize(int width, int height) {
//
    }

    @Override
    public void pause() {
//
    }

    @Override
    public void resume() {
//
    }

    @Override
    public void hide() {
//
    }

    @Override
    public void dispose() {
//
    }
}
