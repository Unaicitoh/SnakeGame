package com.unaigs.snakegame.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.unaigs.snakegame.Assets;

public class Snake extends Sprite{

	private List<Vector2> pos;
	private Vector2 vel;
	private HashMap<String,Sprite> parts;
	private Direction direction = Direction.RIGHT;
	private static final float MOVE_TIME = .15f;
	private float lastMoveTime;
	private boolean changeDir=true;
	private boolean canMove=true;

	
	public Snake(Assets assets) {
		setPos(new ArrayList<Vector2>());
		vel = new Vector2(Assets.TILE_SIZE,Assets.TILE_SIZE);
		parts = assets.getSnakeParts();
		lastMoveTime=MOVE_TIME;
		for(int i =4; i>=1 ; i--) {
			getPos().add(new Vector2((i*Assets.TILE_SIZE)+Assets.TILE_SIZE, Assets.TILE_SIZE*2));
		}
		
	}
	
	public void draw(SpriteBatch batch) {
		drawSnake(batch);
	}

	private void drawSnake(SpriteBatch batch) {
		setScale(1.01f);
		for(int i=0 ; i<getPos().size(); i++) {
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
					setPosition(getPos().get(i).x, getPos().get(i).y);
					if(canMove)
						super.draw(batch);
			}else {
					set(parts.get("body"));
					setPosition(getPos().get(i).x, getPos().get(i).y);
					super.draw(batch);
				}
			}
		}
	
	public void update(float delta, Stage stage) {
		lastMoveTime-=delta;
		canMove(stage);
		if(lastMoveTime<=0 && isCanMove()) {
			moveSnake();
		}
		
	}

	private void moveSnake() {
		lastMoveTime=MOVE_TIME;
		changeDir=true;
		for(int i=getPos().size()-1; i>0; i--) {
			getPos().get(i).set(getPos().get(i-1));
		}
		switch(direction) {
		case UP:
				getPos().get(0).y+= vel.y;
			break;
		case DOWN:
				getPos().get(0).y += vel.y;
			break;
		case LEFT:
				getPos().get(0).x += vel.x;
			break;
		default:
				getPos().get(0).x += vel.x;

		}
		
		Gdx.app.log("Snake",this.toString());
	}

	private void canMove(Stage stage) {
		if(pos.get(0).x<Assets.TILE_SIZE || pos.get(0).y >= stage.getHeight()-Assets.TILE_SIZE ||
				pos.get(0).x>=stage.getWidth()-Assets.TILE_SIZE || pos.get(0).y < Assets.TILE_SIZE) {
			setCanMove(false);
		}else {
			setCanMove(true);
		}
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void setVel(float x, float y) {
		Vector2 velo = new Vector2(x,y);
		this.vel = velo;
	}


	public boolean isChangeDir() {
		return changeDir;
	}

	public void setChangeDir(boolean changeDir) {
		this.changeDir = changeDir;
	}

	public List<Vector2> getPos() {
		return pos;
	}

	public void setPos(List<Vector2> pos) {
		this.pos = pos;
	}


	public boolean isCanMove() {
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	@Override
	public String toString() {
		return "Snake [pos=" + getPos() + ", vel=" + vel + ", direction=" + direction + "]";
	}
	
}
