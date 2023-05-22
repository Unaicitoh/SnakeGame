package com.unaigs.snakegame;

/**
 * @author unaig
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.unaigs.snakegame.screens.MenuScreen;

public class SnakeGame extends Game {
	
	public Assets assets;
	
	@Override
	public void create () {
		Gdx.app.log("START", "RUNNING GAME");
		assets= new Assets();
		setScreen(new MenuScreen(this));
	}
	
	@Override
	public void dispose () {
		assets.dispose();
	}
}
