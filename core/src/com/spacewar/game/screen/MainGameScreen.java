package com.spacewar.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.spacewar.game.SpaceWarGame;
import com.spacewar.game.objects.Asteroid;
import com.spacewar.game.objects.Bullet;
import com.spacewar.game.objects.Explosion;
import com.spacewar.game.objects.SpaceShip;

import java.util.ArrayList;
import java.util.Random;

import static com.spacewar.game.SpaceWarGame.WINDOW_HEIGHT;
import static com.spacewar.game.SpaceWarGame.WINDOW_WIDTH;
import static com.spacewar.game.SpaceWarGame.batch;

public class MainGameScreen implements Screen {

    private static final float SHOOT_WAIT_TIME = 0.3f;
    private static final float MIN_ASTEROID_SPAWN_TIME = 0.3f;
    private static final float MAX_ASTEROID_SPAWN_TIME = 0.6f;

    private SpaceShip spaceShip;
    private ArrayList<Asteroid> asteroids = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private ArrayList<Explosion> explosions = new ArrayList<>();

    private BitmapFont scoreFont;
    private int score;
    private Texture blank;
    private float health;
    private Random rand;
    private float shootTimer;
    private float asteroidSpawnTimer;


    public MainGameScreen() {
        this.spaceShip = new SpaceShip();
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
        score = 0;
        health = 1;
        blank = new Texture("blank.png");
        rand = new Random();
        asteroidSpawnTimer = rand.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;
    }

    @Override
    public void show() {
        //
    }

    @Override
    public void render(float delta) {
        spawnAsteroid(delta);
        shoot(delta);
        spaceShip.update(delta);
        removeObjects(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        SpaceWarGame.batch.begin();

        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "" + score);
        scoreFont.draw(batch, scoreLayout, (float) WINDOW_WIDTH / 2 - scoreLayout.width / 2, WINDOW_HEIGHT - scoreLayout.height);

        for (Bullet bullet : bullets) {
            bullet.render(SpaceWarGame.batch);
        }
        for (Asteroid asteroid : asteroids) {
            asteroid.render(SpaceWarGame.batch);
        }
        for (Explosion explosion : explosions) {
            explosion.render(SpaceWarGame.batch);
        }
        if (health >= 0.6f) {
            batch.setColor(Color.OLIVE);
        } else if (health >= 0.4f) {
            batch.setColor(Color.GREEN);
        } else if (health >= 0.2f) {
            batch.setColor(Color.ORANGE);
        } else {
            batch.setColor(Color.RED);
        }
        batch.draw(blank, 0, 0, WINDOW_WIDTH * health, 5);
        batch.setColor(Color.WHITE);
        spaceShip.render();
        SpaceWarGame.batch.end();
    }

    private void shoot(float delta) {
        shootTimer += delta;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootTimer >= SHOOT_WAIT_TIME) {
            bullets.addAll(spaceShip.shoot());
            shootTimer = 0;
        }
    }

    private void removeObjects(float delta) {
        ArrayList<Asteroid> asteroidsToRemove = new ArrayList<>();
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        ArrayList<Explosion> explosionsToRemove = new ArrayList<>();
        removeAsteroids(delta, asteroidsToRemove);
        removeBullets(delta, bulletsToRemove);
        removeExplosions(delta, explosionsToRemove);
        checkHits(bulletsToRemove, asteroidsToRemove);
        checkShipCollisions(asteroidsToRemove);
        asteroids.removeAll(asteroidsToRemove);
        bullets.removeAll(bulletsToRemove);
        explosions.removeAll(explosionsToRemove);
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

    private void removeExplosions(float delta, ArrayList<Explosion> explosionsToRemove) {
        for (Explosion explosion : explosions) {
            explosion.update(delta);
            if (explosion.isToRemove()) {
                explosionsToRemove.add(explosion);
            }
        }
    }

    private void checkHits(ArrayList<Bullet> bulletsToRemove, ArrayList<Asteroid> asteroidsToRemove) {
        for (Bullet bullet : bullets) {
            for (Asteroid asteroid : asteroids) {
                if (bullet.getCollisionRect().collidesWith(asteroid.getCollisionRect())) {
                    asteroidsToRemove.add(asteroid);
                    bulletsToRemove.add(bullet);
                    explosions.add(new Explosion(asteroid.getPosition()));
                    score += 100;
                }
            }
        }
    }

    private void checkShipCollisions(ArrayList<Asteroid> asteroidsToRemove) {
        for (Asteroid asteroid : asteroids) {
            if (asteroid.getCollisionRect().collidesWith(spaceShip.getCollisionRect())) {
                health -= 0.1;
                asteroidsToRemove.add(asteroid);
            }
        }
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
