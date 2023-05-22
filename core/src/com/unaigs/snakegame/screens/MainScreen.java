package com.unaigs.snakegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.unaigs.snakegame.Assets;
import com.unaigs.snakegame.SnakeGame;
import com.unaigs.snakegame.entities.Direction;
import com.unaigs.snakegame.entities.Snake;
import com.unaigs.snakegame.entities.Snake.SnakeMovementListener;

public class MainScreen extends ScreenAdapter implements SnakeMovementListener{
	
	private SnakeGame game;
	private Stage stage;
	private SpriteBatch batch;
	private Snake snake;
	private Direction lastDirection = Direction.RIGHT;
	private boolean gameOver=false;
	private TextButton textButton;

	public MainScreen(SnakeGame game) {
		this.game=game;
	}
	
	@Override
	public void show() {
		batch=new SpriteBatch();
		stage = new Stage(new StretchViewport(36f*Assets.TILE_SIZE, 24f*Assets.TILE_SIZE),batch);
		Gdx.input.setInputProcessor(stage);
		snake=new Snake(game.assets, this);
		textButton = new TextButton("Restart", game.assets.menuUI);
		textButton.addListener(new ClickListener() {

			@Override
			public void clicked (InputEvent event, float x, float y) {
				game.setScreen(new MainScreen(game));
			}
			
		});
		stage.addListener(new InputListener() {
			@Override
			public boolean keyDown (InputEvent event, int keycode) {
				if(!gameOver && snake.changeDir) {
					handleInput(keycode);
				}
				return false;
			}
		});


		
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0,0,0,1);
		update(delta);
		batch.begin();
		drawBackground(game.assets.atlas);
		snake.draw(batch);
		drawUI();
		batch.end();
		stage.draw();
	}
	
	private void drawShadowed(String s, float x, float y, float target, int align) {
		game.assets.mainFont.setColor(Color.BLACK);
		for(int i=-1; i<=1; i++) {
			for(int j=-1; j<=1; j++) {				
				game.assets.mainFont.draw(batch,s,x+i,y+j,target,align,false);
			}
		}
		game.assets.mainFont.setColor(Color.WHITE);
		game.assets.mainFont.draw(batch,s,x,y,target,align,false);
	}

	private void update(float delta) {
		stage.act(delta);
		snake.update(delta, stage);
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

	private void drawUI() {
		if(gameOver) {
			drawShadowed("GAME OVER",0,stage.getHeight()/2+Assets.TILE_SIZE,stage.getWidth(),Align.center);
			stage.addActor(textButton);
		}
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
	
	private void handleInput(int keycode) {
		switch(keycode) {
		case Keys.UP:
			if (lastDirection!=Direction.DOWN && lastDirection!=Direction.UP) {
				snake.setDirection(Direction.UP);
				snake.setVel(0,Assets.TILE_SIZE);
				lastDirection = Direction.UP;
				snake.changeDir=false;
			}
			break;
		case Keys.DOWN:
			if (lastDirection!=Direction.UP && lastDirection!=Direction.DOWN) {
				snake.setDirection(Direction.DOWN);
				snake.setVel(0,-Assets.TILE_SIZE);
				lastDirection = Direction.DOWN;
				snake.changeDir=false;
			}
			break;
		case Keys.LEFT:
			if (lastDirection!=Direction.RIGHT&& lastDirection!=Direction.LEFT) {
				snake.setDirection(Direction.LEFT);
				snake.setVel(-Assets.TILE_SIZE,0);
				lastDirection = Direction.LEFT;
				snake.changeDir=false;
			}
			break;
		default:
			if (lastDirection!=Direction.LEFT && lastDirection!=Direction.RIGHT) {
				snake.setDirection(Direction.RIGHT);
				snake.setVel(Assets.TILE_SIZE,0);
				lastDirection = Direction.RIGHT;
				snake.changeDir=false;
			}
		}
	}

	@Override
	public void onGameEnd() {
		gameOver=true;
	}

}
