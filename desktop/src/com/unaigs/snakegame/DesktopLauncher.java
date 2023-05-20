package com.unaigs.snakegame;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(Assets.SCREEN_W, Assets.SCREEN_H);
		config.setTitle("Snake Game");
		config.setWindowIcon("snakeIcon.png");
		config.setResizable(true);
		new Lwjgl3Application(new SnakeGame(), config);
	}
}
