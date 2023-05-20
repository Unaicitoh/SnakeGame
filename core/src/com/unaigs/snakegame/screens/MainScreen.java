package com.unaigs.snakegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.unaigs.snakegame.Assets;
import com.unaigs.snakegame.SnakeGame;
import com.unaigs.snakegame.entities.Direction;
import com.unaigs.snakegame.entities.Snake;

public class MainScreen extends ScreenAdapter{
	


	private SnakeGame game;
	private Stage stage;
	private SpriteBatch batch;
	private Snake snake;
	public MainScreen(SnakeGame game) {
		this.game=game;
	}
	
	@Override
	public void show() {
		batch=new SpriteBatch();
		stage = new Stage(new ExtendViewport(32*Assets.TILE_SIZE, 24*Assets.TILE_SIZE),batch);
		Gdx.input.setInputProcessor(stage);
		snake=new Snake(game.assets);
		
		stage.addListener(new InputListener() {
			@Override
			public boolean keyDown (InputEvent event, int keycode) {
				Gdx.app.log("KEY",""+keycode);
				switch(keycode) {
				case Keys.UP:
					snake.setDirection(Direction.UP);
					break;
				case Keys.DOWN:
					snake.setDirection(Direction.DOWN);
					break;
				case Keys.LEFT:
					snake.setDirection(Direction.LEFT);
					break;
				default:
					snake.setDirection(Direction.RIGHT);
				}
				return false;
			}
		});
		
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0,0,0,1);
		update(delta);
		stage.draw();
		batch.begin();
		drawBackground(game.assets.getAtlas());
		snake.draw(batch);
		batch.end();
	}

	private void update(float delta) {
		stage.act(delta);
		snake.update(delta);				
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
		Sprite roof = atlas.createSprite("wall_up");
		Sprite wall = atlas.createSprite("wall_left");
		Sprite grass = atlas.createSprite("grass");
		
		for(int x=0; x<stage.getWidth(); x+= Assets.TILE_SIZE) {
			for(int y=0; y<stage.getHeight(); y+=Assets.TILE_SIZE) {
				batch.draw(grass, x, y, 0, 0, Assets.TILE_SIZE, Assets.TILE_SIZE, 1.01f, 1.01f, 0);
			}
		}
		
		for(int x=0; x<=stage.getWidth() ; x+=Assets.TILE_SIZE) {
			batch.draw(roof, x, 0, 0, 0, Assets.TILE_SIZE, Assets.TILE_SIZE, 1.01f, 1.01f, 0);
			batch.draw(roof, x, stage.getHeight()-Assets.TILE_SIZE, 0, 0, Assets.TILE_SIZE, Assets.TILE_SIZE, 1.01f, 1.01f, 0);
		}
		for(int y=0; y<=stage.getHeight() ; y+=Assets.TILE_SIZE) {
			if(y!=0) {
				batch.draw(wall, 0, y, 0, 0, Assets.TILE_SIZE, Assets.TILE_SIZE, 1.01f, 1.01f, 0);
				batch.draw(wall, stage.getWidth()-Assets.TILE_SIZE, y, 0, 0, Assets.TILE_SIZE, Assets.TILE_SIZE, 1.01f, 1.01f, 0);
			}
			
		}

	}

}
