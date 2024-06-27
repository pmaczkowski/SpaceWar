package com.spacewar.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.spacewar.game.SpaceWarGame;

import static com.spacewar.game.SpaceWarGame.WINDOW_HEIGHT;
import static com.spacewar.game.SpaceWarGame.WINDOW_WIDTH;

public class MainMenuScreen implements Screen {

    private static final int EXIT_BUTTON_WIDTH = 300;
    private static final int EXIT_BUTTON_HEIGHT = 150;
    private static final int PLAY_BUTTON_WIDTH = 330;
    private static final int PLAY_BUTTON_HEIGHT = 150;
    private static final int PLAY_BUTTON_X = (WINDOW_WIDTH - PLAY_BUTTON_WIDTH) / 2;
    private static final int PLAY_BUTTON_Y = 100 + EXIT_BUTTON_HEIGHT + 10;
    private static final int EXIT_BUTTON_X = (WINDOW_WIDTH - EXIT_BUTTON_WIDTH) / 2;
    private static final int EXIT_BUTTON_Y = 100;

    private final Texture playButtonActive = new Texture("play_button_active.png");
    private final Texture playButtonInactive = new Texture("play_button_inactive.png");
    private final Texture exitButtonActive = new Texture("exit_button_active.png");
    private final Texture exitButtonInactive = new Texture("exit_button_inactive.png");

    private SpaceWarGame game;

    public MainMenuScreen(SpaceWarGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        //
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpaceWarGame.batch.begin();
        if (isPlayButtonActive()) {
            drawPlayButton(playButtonActive);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new MainGameScreen(game));
            }
        } else {
            drawPlayButton(playButtonInactive);
        }
        if (isExitButtonActive()) {
            drawExitButton(exitButtonActive);
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            drawExitButton(exitButtonInactive);
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
        playButtonActive.dispose();
        playButtonInactive.dispose();
        exitButtonActive.dispose();
        exitButtonInactive.dispose();
    }

    private boolean isPlayButtonActive() {
        int x = (WINDOW_WIDTH - PLAY_BUTTON_WIDTH) / 2;
        return Gdx.input.getX() < x + PLAY_BUTTON_WIDTH
                && Gdx.input.getX() > x
                && WINDOW_HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT
                && WINDOW_HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y;
    }

    private boolean isExitButtonActive() {
        int x = (WINDOW_WIDTH - EXIT_BUTTON_WIDTH) / 2;
        return Gdx.input.getX() < x + EXIT_BUTTON_WIDTH
                && Gdx.input.getX() > x
                && WINDOW_HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT
                && WINDOW_HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y;
    }

    private void drawPlayButton(Texture playButton) {
        SpaceWarGame.batch.draw(playButton, PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
    }

    private void drawExitButton(Texture exitButton) {
        SpaceWarGame.batch.draw(exitButton, EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
    }

}
