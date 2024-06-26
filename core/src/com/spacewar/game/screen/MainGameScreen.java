package com.spacewar.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.spacewar.game.SpaceWarGame;
import com.spacewar.game.entity.SpaceShip;


public class MainGameScreen implements Screen {

    private SpaceShip spaceShip;

    public MainGameScreen() {
        this.spaceShip = new SpaceShip();
    }

    @Override
    public void show() {
        //
    }

    @Override
    public void render(float delta) {
        spaceShip.update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        SpaceWarGame.batch.begin();
        spaceShip.render();
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
