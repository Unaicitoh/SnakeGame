package com.unaigs.snakegame.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.unaigs.snakegame.Assets;

public class Snake{

	private List<Vector2> pos;
	private Vector2 vel;
	private HashMap<String,Sprite> parts;
	private Direction direction = Direction.RIGHT;
	
	public Snake(Assets assets) {
		pos = new ArrayList<Vector2>();
		vel = new Vector2(Assets.TILE_SIZE,Assets.TILE_SIZE);
		parts = new HashMap<String,Sprite>();
		parts.put("head_right", assets.getAtlas().createSprite("head_right"));
		parts.put("head_left", assets.getAtlas().createSprite("head_left"));
		parts.put("head_up", assets.getAtlas().createSprite("head_up"));
		parts.put("head_down", assets.getAtlas().createSprite("head_down"));
		parts.put("body", assets.getAtlas().createSprite("body"));

		for(int i =4; i>=1 ; i--) {
			pos.add(new Vector2((i*Assets.TILE_SIZE)+Assets.TILE_SIZE, Assets.TILE_SIZE*2));
		}
		

	}
	
	
	public void draw(SpriteBatch batch) {
		Sprite snake= new Sprite();
		for(int i=0 ; i<pos.size(); i++) {
			if(i==0) {
				switch(direction) {
				case UP:
					setHeadSprite(snake,"head_up");
					break;
				case DOWN:
					setHeadSprite(snake,"head_down");
					break;
				case RIGHT:
					setHeadSprite(snake,"head_right");
					break;
				default:
					setHeadSprite(snake,"head_left");
				}
				snake.setPosition(pos.get(i).x, pos.get(i).y);
				snake.draw(batch);
			}else {
				snake.set(parts.get("body"));
				snake.setPosition(pos.get(i).x, pos.get(i).y);
				snake.draw(batch);
			}
			
		}
	}

	public void update(float delta) {
		switch(direction) {
		case UP:
			Gdx.app.log("POSITION", "x: "+pos.get(0).x+" / Y: "+pos.get(0).y);
			pos.get(0).y+=vel.y*delta;
			break;
		case DOWN:
			Gdx.app.log("POSITION", "x: "+pos.get(0).x+" / Y: "+pos.get(0).y);

			pos.get(0).y+=-vel.x*delta;
			break;
		case LEFT:
			Gdx.app.log("POSITION", "x: "+pos.get(0).x+" / Y: "+pos.get(0).y);

			pos.get(0).x+=-vel.x*delta;
			break;
		default:
			Gdx.app.log("POSITION", "x: "+pos.get(0).x+" / Y: "+pos.get(0).y);

			pos.get(0).x+=vel.x*delta;
		}

		
	}


	private void setHeadSprite(Sprite snake, String head) {
		snake.set(parts.get(head));
	}


	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
