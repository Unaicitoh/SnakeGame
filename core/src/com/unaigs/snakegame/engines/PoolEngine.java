package com.unaigs.snakegame.engines;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.unaigs.snakegame.SnakeGame;
import com.unaigs.snakegame.entities.Food;
import com.unaigs.snakegame.entities.Snake;
import com.unaigs.snakegame.screens.MainScreen;

public class PoolEngine {

	public List<Food> food;
	
	public PoolEngine(SnakeGame game) {
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
					checkRewards(food.get(i), snake);
				}
			}
			if(!food.get(i).isAlive()) {
				food.get(i).release();
				food.remove(i);
				i--;
			}
		}
	}
	
	private void checkRewards(Food food, Snake snake) {

		switch(food.getType()) {
		case 3: 
			MainScreen.score+=250;
			snake.MOVE_TIME-=.025f;
			break;
		case 2:
			MainScreen.score+=150;
			snake.MOVE_TIME=.175f;
			break;
		default:
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
