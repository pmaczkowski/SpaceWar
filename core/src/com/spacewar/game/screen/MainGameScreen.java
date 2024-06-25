package com.spacewar.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import com.spacewar.game.SpaceWarGame;


public class MainGameScreen implements Screen {

    public static final float SPEED = 120;

    Texture img;
    GridPoint2 position;

    SpaceWarGame spaceWarGame;

    public MainGameScreen(SpaceWarGame game) {
        this.spaceWarGame = game;
        this.position = new GridPoint2(0, 0);
    }

    @Override
    public void show() {
        img = new Texture("badlogic.jpg");
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            position.y += SPEED * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            position.y -= SPEED * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= SPEED * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += SPEED * Gdx.graphics.getDeltaTime();
        }

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spaceWarGame.batch.begin();
        spaceWarGame.batch.draw(img, position.x, position.y);
        spaceWarGame.batch.end();

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
