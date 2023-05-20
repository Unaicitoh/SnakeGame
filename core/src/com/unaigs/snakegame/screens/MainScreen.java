package com.unaigs.snakegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.unaigs.snakegame.Assets;
import com.unaigs.snakegame.SnakeGame;

public class MainScreen extends ScreenAdapter {
	
	private static final int SCREEN_W = 16 * Assets.TILE_SIZE;
	private static final int SCREEN_H = 9 * Assets.TILE_SIZE;

	private SnakeGame game;
	private Stage stage;
	private SpriteBatch batch;
	
	public MainScreen(SnakeGame game) {
		this.game=game;
	}
	
	@Override
	public void show() {
		batch=new SpriteBatch();
		stage = new Stage(new ExtendViewport(SCREEN_W, SCREEN_H),batch);
		Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0,0,0,1);
		stage.act(delta);
		drawBackground(game.assets.getAtlas());
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height,true);
	}


	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
		stage.dispose();

	}
	
	private void drawBackground(TextureAtlas atlas) {
		batch.begin();
		Sprite roof = atlas.createSprite("wall_up");
		for(int x=0; x<=stage.getWidth() ; x+=Assets.TILE_SIZE) {
			batch.draw(roof, x, 0, 0, 0, Assets.TILE_SIZE, Assets.TILE_SIZE, 1, 1, 0);
		}
		batch.end();
	}

}
