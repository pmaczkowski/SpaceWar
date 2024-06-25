package com.spacewar.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import static com.spacewar.game.SpaceWarGame.WINDOW_HEIGHT;
import static com.spacewar.game.SpaceWarGame.WINDOW_WIDTH;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT);
		config.setResizable(false);
		config.setTitle("SpaceWar");
		new Lwjgl3Application(new SpaceWarGame(), config);
	}
}
