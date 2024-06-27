package com.spacewar.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.spacewar.game.SpaceWarGame;
import com.spacewar.game.objects.Asteroid;
import com.spacewar.game.objects.Bullet;
import com.spacewar.game.objects.SpaceShip;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.spacewar.game.SpaceWarGame.WINDOW_WIDTH;

public class MainGameScreen implements Screen {

    private static final float SHOOT_WAIT_TIME = 0.3f;
    private static final float MIN_ASTEROID_SPAWN_TIME = 0.3f;
    private static final float MAX_ASTEROID_SPAWN_TIME = 0.6f;

    private SpaceShip spaceShip;

    private Random rand;
    private float shootTimer;
    private float asteroidSpawnTimer;
    private ArrayList<Bullet> bullets = new ArrayList<>();
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
        shootTimer += delta;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootTimer >= SHOOT_WAIT_TIME) {
            bullets.addAll(spaceShip.shoot());
            shootTimer = 0;
        }
        spawnAsteroid(delta);
        ArrayList<Asteroid> asteroidsToRemove = new ArrayList<>();
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        removeAsteroids(delta, asteroidsToRemove);
        removeBullets(delta, bulletsToRemove);

        spaceShip.update(delta);

        for (Bullet bullet : bullets) {
            for (Asteroid asteroid : asteroids) {
                if (bullet.getCollisionRect().collidesWith(asteroid.getCollisionRect())) {
                    asteroidsToRemove.add(asteroid);
                    bulletsToRemove.add(bullet);
                }
            }
        }
        asteroids.removeAll(asteroidsToRemove);
        bullets.removeAll(bulletsToRemove);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        SpaceWarGame.batch.begin();
        for (Bullet bullet : bullets) {
            bullet.render(SpaceWarGame.batch);
        }
        for (Asteroid asteroid : asteroids) {
            asteroid.render(SpaceWarGame.batch);
        }
        spaceShip.render();
        SpaceWarGame.batch.end();

    }

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }

    private void spawnAsteroid(float delta) {
        asteroidSpawnTimer -= delta;
        if (asteroidSpawnTimer <= 0) {
            asteroidSpawnTimer = rand.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;
            asteroids.add(new Asteroid(rand.nextInt(WINDOW_WIDTH - Asteroid.WIDTH)));
        }
    }

    private void removeAsteroids(float delta, ArrayList<Asteroid> asteroidsToRemove) {
        for (Asteroid asteroid : asteroids) {
            asteroid.update(delta);
            if (asteroid.isToRemove()) {
                asteroidsToRemove.add(asteroid);
            }
        }
    }

    private void removeBullets(float delta, ArrayList<Bullet> bulletsToRemove) {
        for (Bullet bullet : bullets) {
            bullet.update(delta);
            if (bullet.isToRemove()) {
                bulletsToRemove.add(bullet);
            }
        }

    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
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
