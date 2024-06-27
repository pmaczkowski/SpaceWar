package com.spacewar.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.spacewar.game.SpaceWarGame;

import static com.spacewar.game.SpaceWarGame.WINDOW_HEIGHT;
import static com.spacewar.game.SpaceWarGame.WINDOW_WIDTH;

public class GameOverScreen implements Screen {

    private static final int GAME_OVER_BANNER_WIDTH = 350;
    private static final int GAME_OVER_BANNER_HEIGHT = 100;
    private final int GAME_OVER_BANNER_Y = WINDOW_HEIGHT - GAME_OVER_BANNER_HEIGHT - 20;

    private SpaceWarGame game;
    private BitmapFont scoreFont;
    private int score;
    private final int highScore;

    private final Texture gameOverBanner;

    public GameOverScreen(SpaceWarGame game, int score) {
        this.game = game;
        this.score = score;

        Preferences preferences = Gdx.app.getPreferences("spacewargame");
        this.highScore = preferences.getInteger("highscore", 0);
        if (score > highScore) {
            preferences.putInteger("highscore", score);
            preferences.flush();
        }

        gameOverBanner = new Texture("game_over.png");
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));

    }

    @Override
    public void show() {
        //
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(gameOverBanner, WINDOW_WIDTH / 2 - GAME_OVER_BANNER_WIDTH / 2, WINDOW_HEIGHT - GAME_OVER_BANNER_HEIGHT - 15, GAME_OVER_BANNER_WIDTH, GAME_OVER_BANNER_HEIGHT);

        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "Score: \n" + score, Color.WHITE, 0, Align.left, false);
        GlyphLayout highScoreLayout = new GlyphLayout(scoreFont, "Highscore: \n" + highScore, Color.WHITE, 0, Align.left, false);

        scoreFont.draw(game.batch, scoreLayout, WINDOW_WIDTH / 2 - scoreLayout.width / 2, WINDOW_HEIGHT - GAME_OVER_BANNER_HEIGHT - 15 * 2);
        scoreFont.draw(game.batch, highScoreLayout, WINDOW_WIDTH / 2 - highScoreLayout.width / 2, WINDOW_HEIGHT - GAME_OVER_BANNER_HEIGHT - scoreLayout.height - 15 * 3);

        float touchX = Gdx.input.getX();
        float touchY = WINDOW_HEIGHT - Gdx.input.getY();

        GlyphLayout tryAgainLayout = new GlyphLayout(scoreFont, "Try Again");
        GlyphLayout mainMenuLayout = new GlyphLayout(scoreFont, "Main Menu");

        float tryAgainX = WINDOW_WIDTH / 2 - tryAgainLayout.width /2;
        float tryAgainY = WINDOW_HEIGHT / 2 - tryAgainLayout.height / 2;
        float mainMenuX = WINDOW_WIDTH / 2 - mainMenuLayout.width /2;
        float mainMenuY = WINDOW_HEIGHT / 2 - mainMenuLayout.height / 2 - tryAgainLayout.height - 15;

        if (touchX >= tryAgainX && touchX < tryAgainX + tryAgainLayout.width && touchY >= tryAgainY - tryAgainLayout.height && touchY < tryAgainY) {
            tryAgainLayout.setText(scoreFont, "Try Again", Color.YELLOW, 0, Align.left, false);
        }
        if (touchX >= mainMenuX && touchX < mainMenuX + mainMenuLayout.width && touchY >= mainMenuY - mainMenuLayout.height && touchY < mainMenuY) {
            mainMenuLayout.setText(scoreFont, "Main Menu", Color.YELLOW, 0, Align.left, false);
        }

        if (Gdx.input.isTouched()) {
            //Try again
            if (touchX > tryAgainX && touchX < tryAgainX + tryAgainLayout.width && touchY > tryAgainY - tryAgainLayout.height && touchY < tryAgainY) {
                this.dispose();
                game.batch.end();
                game.setScreen(new MainGameScreen(game));
                return;
            }

            //main menu
            if (touchX > mainMenuX && touchX < mainMenuX + mainMenuLayout.width && touchY > mainMenuY - mainMenuLayout.height && touchY < mainMenuY) {
                this.dispose();
                game.batch.end();
                game.setScreen(new MainMenuScreen(game));
                return;
            }
        }

        scoreFont.draw(game.batch, tryAgainLayout, tryAgainX, tryAgainY);
        scoreFont.draw(game.batch, mainMenuLayout, mainMenuX, mainMenuY);

        game.batch.end();

    }

    @Override
    public void resize(int i, int i1) {
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
