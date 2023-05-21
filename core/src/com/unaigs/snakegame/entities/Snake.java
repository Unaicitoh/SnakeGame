package com.unaigs.snakegame.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.unaigs.snakegame.Assets;

public class Snake extends Sprite{

	private List<Vector2> pos;
	private Vector2 vel;
	private HashMap<String,Sprite> parts;
	private Direction direction = Direction.RIGHT;
	private static final float MOVE_TIME = .15f;
	private float moveTime;
	
	public Snake(Assets assets) {
		pos = new ArrayList<Vector2>();
		vel = new Vector2(Assets.TILE_SIZE,Assets.TILE_SIZE);
		parts = assets.getSnakeParts();
		moveTime=MOVE_TIME;
		for(int i =4; i>=1 ; i--) {
			pos.add(new Vector2((i*Assets.TILE_SIZE)+Assets.TILE_SIZE, Assets.TILE_SIZE*2));
		}
		
	}
	
	public void draw(SpriteBatch batch) {
		drawSnake(batch);
	}

	private void drawSnake(SpriteBatch batch) {
		setScale(1.01f);
		for(int i=0 ; i<pos.size(); i++) {
			if(i==0) {
				switch(direction) {
				case UP:
					set(parts.get("head_up"));
					break;
				case DOWN:
					set(parts.get("head_down"));
					break;
				case RIGHT:
					set(parts.get("head_right"));
					break;
				default:
					set(parts.get("head_left"));
				}
				setPosition(pos.get(i).x, pos.get(i).y);
				super.draw(batch);
			}else {
				set(parts.get("body"));
				setPosition(pos.get(i).x, pos.get(i).y);
				super.draw(batch);
			}
		}
	}
	
	public void update(float delta) {
		moveTime-=delta;
		if(moveTime<=0) {
			if(insideField()) {
				moveSnake();				
			}
		}
		
	}

	private boolean insideField() {
		boolean canMove=false;
		pos.forEach((p)->{
			//TODO pa mañana
		});
		return canMove;
	}

	private void moveSnake() {
		moveTime=MOVE_TIME;
		for(int i=pos.size()-1; i>0; i--) {
			pos.get(i).set(pos.get(i-1));
		}
		switch(direction) {
		case UP:
				pos.get(0).y+=vel.y;
			break;
		case DOWN:
				pos.get(0).y += -vel.x;
			break;
		case LEFT:
				pos.get(0).x += -vel.x;
			break;
		default:
				pos.get(0).x += vel.x;

		}
		Gdx.app.log("Snake",this.toString());
	}



	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return "Snake [pos=" + pos + ", vel=" + vel + ", direction=" + direction + "]";
	}
	
}
