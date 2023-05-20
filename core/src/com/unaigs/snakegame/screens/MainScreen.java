package com.unaigs.snakegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.unaigs.snakegame.Assets;
import com.unaigs.snakegame.SnakeGame;

public class MainScreen extends ScreenAdapter {
	


	private SnakeGame game;
	private Stage stage;
	private SpriteBatch batch;
	
	public MainScreen(SnakeGame game) {
		this.game=game;
	}
	
	@Override
	public void show() {
		batch=new SpriteBatch();
		stage = new Stage(new ExtendViewport(16*Assets.TILE_SIZE, 12*Assets.TILE_SIZE),batch);
		Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0,0,0,1);
		stage.act(delta);
		stage.draw();
		drawBackground(game.assets.getAtlas());
		
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height,true);
	}


	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
		stage.dispose();
		batch.dispose();

	}
	
	private void drawBackground(TextureAtlas atlas) {
		batch.begin();
		Sprite roof = atlas.createSprite("wall_up");
		Sprite wall = atlas.createSprite("wall_left");
		Sprite grass = atlas.createSprite("grass");
		
		for(int x=0; x<stage.getWidth(); x+= Assets.TILE_SIZE) {
			for(int y=0; y<stage.getHeight(); y+=Assets.TILE_SIZE) {
				batch.draw(grass, x, y, 0, 0, Assets.TILE_SIZE, Assets.TILE_SIZE, 1, 1, 0);
			}
		}
		
		for(int x=0; x<=stage.getWidth() ; x+=Assets.TILE_SIZE) {
			batch.draw(roof, x, 0, 0, 0, Assets.TILE_SIZE, Assets.TILE_SIZE, 1, 1, 0);
			batch.draw(roof, x, stage.getHeight()-Assets.TILE_SIZE, 0, 0, Assets.TILE_SIZE, Assets.TILE_SIZE, 1, 1, 0);
		}
		for(int y=0; y<=stage.getHeight() ; y+=Assets.TILE_SIZE) {
			if(y!=0) {
				batch.draw(wall, 0, y, 0, 0, Assets.TILE_SIZE, Assets.TILE_SIZE, 1, 1, 0);
				batch.draw(wall, stage.getWidth()-Assets.TILE_SIZE, y, 0, 0, Assets.TILE_SIZE, Assets.TILE_SIZE, 1, 1, 0);
			}
			
		}
		batch.end();
	}

}
