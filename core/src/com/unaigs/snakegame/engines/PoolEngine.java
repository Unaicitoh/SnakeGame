package com.unaigs.snakegame.engines;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.unaigs.snakegame.SnakeGame;
import com.unaigs.snakegame.data.Assets;
import com.unaigs.snakegame.entities.Food;
import com.unaigs.snakegame.entities.Snake;
import com.unaigs.snakegame.screens.MainScreen;

public class PoolEngine {

	public List<Food> food;
	private SnakeGame game;
	
	public PoolEngine(SnakeGame game) {
		this.game=game;
		food=new ArrayList<>();
	}
	
	public void add(Food food) {
		this.food.add(food);
	}
	
	public void update(Snake snake) {
		updateFood(snake);
	}

	private void updateFood(Snake snake) {
		for(int i=0; i<food.size(); i++) {
			for(Vector2 pos :snake.getPos()) {
				if(food.get(i).getPos().x==pos.x && food.get(i).getPos().y==pos.y) {
					food.get(i).setAlive(false);
					checkRewards(food.get(i));
				}
			}
			if(!food.get(i).isAlive()) {
				food.get(i).release();
				food.remove(i);
				i--;
			}
		}
	}
	
	private void checkRewards(Food food) {
		@SuppressWarnings("unused")
		long id;
		switch(food.getType()) {
		case 3:
			id = game.assets.sounds.get(Assets.RABBIT_EFFECT_S).play();
			MainScreen.score+=250;
			Snake.MOVE_TIME-=.025f;
			if(Snake.MOVE_TIME<.05f) {
				Snake.MOVE_TIME=.05f;
			}
			break;
		case 2:
			id = game.assets.sounds.get(Assets.COOKIE_EFFECT_S).play(.3f);

			MainScreen.score-=50;
			if(MainScreen.score<0) {
				MainScreen.score=0;
			}
			Snake.MOVE_TIME=.175f;
			break;
		default:
			id = game.assets.sounds.get(Assets.FRUIT_EFFECT_S).play(.25f);

			MainScreen.score+=50;
		}
		
	}

	public void draw(SpriteBatch batch) {
		for(int i=0;i<food.size();i++) {
			food.get(i).draw(batch);
		}
	}
	
	public void clear() {
		while(!food.isEmpty()) {
			food.get(0).reset();
			food.remove(0);
		}
	}
	
}
