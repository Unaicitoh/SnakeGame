package com.unaigs.snakegame;

/**
 * @author unaig
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.unaigs.snakegame.data.Assets;
import com.unaigs.snakegame.data.GameProgress;
import com.unaigs.snakegame.screens.MenuScreen;

public class SnakeGame extends Game {
	
	public Assets assets;
	
	@Override
	public void create () {
		Gdx.app.log("START", "RUNNING GAME");
		assets= new Assets();
		GameProgress.load();
		setScreen(new MenuScreen(this));
	}
	
	@Override
	public void dispose () {
		Gdx.app.log("END", "FINISHING GAME");
		assets.dispose();
	}
}
