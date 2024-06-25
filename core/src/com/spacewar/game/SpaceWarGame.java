package com.spacewar.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spacewar.game.screen.MainMenuScreen;

public class SpaceWarGame extends Game {

	public static final int WINDOW_WIDTH = 480;
	public static final int WINDOW_HEIGHT = 720;

	public static final SpriteBatch batch = new SpriteBatch();

	@Override
	public void create () {
		setScreen(new MainMenuScreen(this));
	}

}
