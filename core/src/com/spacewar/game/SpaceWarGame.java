package com.spacewar.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spacewar.game.screen.GameOverScreen;
import com.spacewar.game.screen.MainMenuScreen;

public class SpaceWarGame extends Game {

	public static final int WINDOW_WIDTH = 480;
	public static final int WINDOW_HEIGHT = 720;

	public static SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MainMenuScreen(this));
	}

}
