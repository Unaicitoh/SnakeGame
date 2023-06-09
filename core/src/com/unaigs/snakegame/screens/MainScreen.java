package com.unaigs.snakegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.unaigs.snakegame.SnakeGame;
import com.unaigs.snakegame.data.Assets;
import com.unaigs.snakegame.data.GameProgress;
import com.unaigs.snakegame.engines.PoolEngine;
import com.unaigs.snakegame.entities.Food;
import com.unaigs.snakegame.entities.Food.FoodGameListener;
import com.unaigs.snakegame.entities.Snake;
import com.unaigs.snakegame.entities.Snake.SnakeMovementListener;
import com.unaigs.snakegame.util.Direction;
import com.unaigs.snakegame.util.FontHelper;

public class MainScreen extends ScreenAdapter implements SnakeMovementListener, FoodGameListener{
	
	private SnakeGame game;
	private Stage stage;
	private SpriteBatch batch;
	private Snake snake;
	private Table table;
	private PoolEngine pool;
	private FontHelper fhelper;
	private boolean gameOver=false;
	private static final float FOOD_SPAWN=2f;
	private static final int MAX_FOOD=10;
	private float shakeTime=1.5f;


	private float lastFoodSpawn;
	private float gameTime;
	private Direction lastDirection = Direction.RIGHT;
	public static long score;
	public static float visualScore;
	private boolean highscore;
	private long id=0;
	protected boolean fadeOut=false;
	protected float fadeOutDuration=1f;
	float t=1;
	public MainScreen(SnakeGame game) {
		this.game=game;

	}
	
	@Override
	public void show() {
		id = game.assets.sounds.get(Assets.SNAKE_INTRO_S).play();
		
		batch=new SpriteBatch();
		fhelper= new FontHelper(game, batch);
		stage = new Stage(new FitViewport(36f*Assets.TILE_SIZE, 24f*Assets.TILE_SIZE),batch);
		pool= new PoolEngine(game);
		score=0;
		visualScore=score;
		gameTime=0;
		lastFoodSpawn=0;
		highscore=false;
		GameProgress.load();
		Gdx.input.setInputProcessor(stage);
		snake=new Snake(game.assets, this);
		TextButton textButton = new TextButton("RESTART",game.assets.skinUI);
		textButton.setTransform(true);
		textButton.getLabel().setFontScale(.5f);
		textButton.addListener(new ClickListener() {
			
			@Override
			public void clicked (InputEvent event, float x, float y) {
				game.setScreen(new MainScreen(game));
			}
			
		});
		table = new Table();
		table.setFillParent(true);
		table.align(Align.bottom).padBottom(15);
		table.add(textButton).width(100).height(35).fillX();
		textButton = new TextButton("BACK TO MENU",game.assets.skinUI);
		textButton.setTransform(true);
		textButton.getLabel().setFontScale(.5f);
		table.row();
		table.add(textButton).height(35).fillX();
		textButton.addListener(new ClickListener() {
			
			@Override
			public void clicked (InputEvent event, float x, float y) {
				fadeOut=true;
				Gdx.input.setInputProcessor(null);
				if(!game.assets.music.isPlaying()) game.assets.music.play();

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
		if(fadeOut) {
			t-=delta;
			batch.setColor(1,1,1,Math.max(0, t));
			if(t<0) game.setScreen(new MenuScreen(game));
			table.setVisible(false);
		}
		batch.begin();
		drawBackground(game.assets.atlas);
		pool.draw(batch);
		snake.draw(batch);
		drawUI(delta);
		batch.end();
		stage.draw();
		batch.setColor(1,1,1,1);

	}
	
	private void update(float delta) {
		if(!gameOver) {
			gameTime+=delta;
			stage.act(delta);
			snake.update(delta, stage);
			pool.update(snake);
			float deltaTime=Math.min(delta, 1/60f);
			if(score>visualScore) {
				visualScore = Math.min(score, visualScore+100*deltaTime);
			}else if(visualScore>score) {
				visualScore = Math.max(score, visualScore-100*deltaTime);
			}
			if(gameTime>lastFoodSpawn+FOOD_SPAWN && pool.food.size()<MAX_FOOD) {
				spawnFood();
			}			
		}
	}

	private void spawnFood() {
		id=game.assets.sounds.get(Assets.SPAWN_EFFECT_S).play();
		game.assets.sounds.get(Assets.SPAWN_EFFECT_S).setVolume(id, .3f);
		boolean emptyTile=false;
		int x =0;
		int y=0;
		do {
			x = MathUtils.random(1,34)*Assets.TILE_SIZE;
			y = MathUtils.random(1,22)*Assets.TILE_SIZE;
			for(Vector2 pos:snake.getPos()) {
				if(pos.x==x && pos.y==y) 
					emptyTile=false;
				else 
					emptyTile=true;
			}
			for(Food f :pool.food) {
				if(f.getPos().x==x && f.getPos().y==y) 
					emptyTile=false;
				else 
					emptyTile=true;
			}
		}while(!emptyTile);
		
		if(emptyTile) {			
			Food.create(new Vector2(x,y),pool,this, game.assets);
			

			lastFoodSpawn=gameTime;
		}
		
	}


	private void drawUI(float delta) {
		float offsetX = 0;
		String scoreStr= String.format("%07d", (long) visualScore);
		fhelper.drawShadowed(scoreStr, 0, stage.getHeight()-Assets.TILE_SIZE/3, stage.getWidth()-Assets.TILE_SIZE/3,Align.right, FontHelper.SCORE_FONT,Color.BLACK);
		if(gameOver) {
			shakeTime-=delta;
			long shakeAlpha = System.currentTimeMillis() % 360;
			float shakeDist = 2f;
			if(shakeTime>0) {
				offsetX += MathUtils.sinDeg(shakeAlpha) * shakeDist;

			}	
			fhelper.drawShadowed("GAME OVER",offsetX,stage.getHeight()*2/3+Assets.TILE_SIZE,stage.getWidth(),Align.center,FontHelper.MAIN_FONT,Color.BLACK);
			stage.addActor(table);
			if(highscore) {
				fhelper.drawShadowed("NEW RECORD! "+score,0,stage.getHeight()/2+Assets.TILE_SIZE,stage.getWidth(),Align.center,FontHelper.SCORE_FONT,Color.BLACK);
			}
		}
	}

	private void drawBackground(TextureAtlas atlas) {
		Sprite roof = atlas.createSprite("wall_up");
		Sprite wall = atlas.createSprite("wall_left");
		Sprite grass = atlas.createSprite("grass");
		Sprite grass2 = atlas.createSprite("grass2");

		
		for(int x=0; x<stage.getWidth(); x+= Assets.TILE_SIZE) {
			for(int y=0; y<stage.getHeight(); y+=Assets.TILE_SIZE) {
				batch.draw(grass, x, y, 0, 0, Assets.TILE_SIZE, Assets.TILE_SIZE, 1.01f, 1.01f, 0);
				if(x%(Assets.TILE_SIZE*3)==0 && y%(Assets.TILE_SIZE*5)==0) {
					for(int i=-1; i<1 ;i++) {
						for(int j=-1; j<=1; j++) {
							if(j%2!=0)
								batch.draw(grass2, x+(i*Assets.TILE_SIZE), y+(j*Assets.TILE_SIZE), 0, 0, Assets.TILE_SIZE, Assets.TILE_SIZE, 1.01f, 1.01f, 0);
						}
					}
				}
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
		game.assets.sounds.get(Assets.SNAKE_MOVEMENT_S).stop();

		switch(keycode) {
		case Keys.UP:
			if (lastDirection!=Direction.DOWN && lastDirection!=Direction.UP) {
				snake.setDirection(Direction.UP);
				snake.setVel(0,Assets.TILE_SIZE);
				lastDirection = Direction.UP;
				snake.changeDir=false;
				game.assets.sounds.get(Assets.SNAKE_MOVEMENT_S).play(.3f,MathUtils.random(1,1.1f),0);

			}
			break;
		case Keys.DOWN:
			if (lastDirection!=Direction.UP && lastDirection!=Direction.DOWN) {
				snake.setDirection(Direction.DOWN);
				snake.setVel(0,-Assets.TILE_SIZE);
				lastDirection = Direction.DOWN;
				snake.changeDir=false;
				game.assets.sounds.get(Assets.SNAKE_MOVEMENT_S).play(.3f,MathUtils.random(1,1.1f),0);

			}
			break;
		case Keys.LEFT:
			if (lastDirection!=Direction.RIGHT&& lastDirection!=Direction.LEFT) {
				snake.setDirection(Direction.LEFT);
				snake.setVel(-Assets.TILE_SIZE,0);
				lastDirection = Direction.LEFT;
				snake.changeDir=false;
				game.assets.sounds.get(Assets.SNAKE_MOVEMENT_S).play(.3f,MathUtils.random(1,1.1f),0);

			}
			break;
		default:
			if (lastDirection!=Direction.LEFT && lastDirection!=Direction.RIGHT) {
				snake.setDirection(Direction.RIGHT);
				snake.setVel(Assets.TILE_SIZE,0);
				lastDirection = Direction.RIGHT;
				snake.changeDir=false;
				game.assets.sounds.get(Assets.SNAKE_MOVEMENT_S).play(.3f,MathUtils.random(1,1.1f),0);

			}
		}
	}

	@Override
	public void onGameEnd() {
		game.assets.sounds.get(Assets.SNAKE_INTRO_S).stop();
		gameOver=true;
		id=game.assets.sounds.get(Assets.GAME_OVER_S).play();

		if(GameProgress.getHighscore()<score) {
			highscore=true;
		}
		GameProgress.save(score);
		
		
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height,true);
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
		pool.clear();
		batch.dispose();
		stage.dispose();
	}

	@Override
	public void onFoodEaten() {
		int size = snake.getPos().size();
		Vector2 pos = snake.getPos().get(size-1);
		snake.getPos().add(new Vector2(pos.x,pos.y));
	}

}
