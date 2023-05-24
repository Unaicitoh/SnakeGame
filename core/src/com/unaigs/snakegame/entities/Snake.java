package com.unaigs.snakegame.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.unaigs.snakegame.data.Assets;
import com.unaigs.snakegame.util.Direction;

public class Snake extends Sprite{

	private List<Vector2> pos;
	private Vector2 vel;
	private HashMap<String,Sprite> parts;
	private Direction direction = Direction.RIGHT;
	public static float MOVE_TIME = .175f;
	private float lastMoveTime;
	public boolean changeDir=true;
	private boolean canMove=true;
	private Vector2 eatenPart;
	private Sprite head;
	SnakeMovementListener listener;

	public interface SnakeMovementListener {
		void onGameEnd();
	}
	
	public Snake(Assets assets,SnakeMovementListener listener) {
		this.listener=listener;
		eatenPart=new Vector2();
		head=new Sprite();
		pos=new ArrayList<>();
		vel = new Vector2(Assets.TILE_SIZE,Assets.TILE_SIZE);
		parts = (HashMap<String, Sprite>) assets.snakeParts;
		lastMoveTime=MOVE_TIME;
		for(int i =5; i>=1 ; i--) {
			pos.add(new Vector2((float)i*Assets.TILE_SIZE+Assets.TILE_SIZE, Assets.TILE_SIZE*2f));
		}
		
	}
	
	public void draw(SpriteBatch batch) {
		drawSnake(batch);
	}

	public void update(float delta, Stage stage) {
		lastMoveTime-=delta;
		canMove(stage);
		if(lastMoveTime<=0 && canMove) {
			moveSnake();
		}
		
	}

	private void drawSnake(SpriteBatch batch) {
		setScale(1.01f);
		for(int i=0 ; i<pos.size(); i++) {
			if(i==0) {
				switch(direction) {
				case UP:
					head=parts.get(Assets.SNAKE_HEAD_UP);
					break;
				case DOWN:
					head=parts.get(Assets.SNAKE_HEAD_DOWN);
					break;
				case RIGHT:
					head=parts.get(Assets.SNAKE_HEAD_RIGHT);
					break;
				default:
					head=parts.get(Assets.SNAKE_HEAD_LEFT);

				}
					set(head);
					setPosition(pos.get(i).x, pos.get(i).y);
					if(canMove)
						super.draw(batch);
			}else {
					if (eatenPart.x==pos.get(i).x && eatenPart.y==pos.get(i).y) {
						set(head);
						setPosition(eatenPart.x,eatenPart.y);
					}else {
						set(parts.get(Assets.SNAKE_BODY));						
						setPosition(pos.get(i).x, pos.get(i).y);
					}
					super.draw(batch);
				}
			}
		}
	
	private void moveSnake() {
		lastMoveTime=MOVE_TIME;
		changeDir=true;
		
		for(int i=pos.size()-1; i>0; i--) {
			pos.get(i).set(pos.get(i-1));
		}
		switch(direction) {
		case UP:
				pos.get(0).y+= vel.y;
			break;
		case DOWN:
				pos.get(0).y += vel.y;
			break;
		case LEFT:
				pos.get(0).x += vel.x;
			break;
		default:
				pos.get(0).x += vel.x;

		}
	}

	private void canMove(Stage stage) {
		if(pos.get(0).x<Assets.TILE_SIZE || pos.get(0).y >= stage.getHeight()-Assets.TILE_SIZE ||
				pos.get(0).x>=stage.getWidth()-Assets.TILE_SIZE || pos.get(0).y < Assets.TILE_SIZE) {
			canMove=false;
			listener.onGameEnd();
		}else {
			canMove=true;
		}
		for(int i=1; i<pos.size();i++) {
			if(pos.get(0).x==pos.get(i).x && pos.get(0).y==pos.get(i).y) {
				eatenPart.set(pos.get(i));
				canMove=false;
				listener.onGameEnd();
			}
		}
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Vector2 getVel() {
		return vel;
	}

	public void setVel(float x, float y) {
		Vector2 velo = new Vector2(x,y);
		this.vel = velo;
	}


	public List<Vector2> getPos() {
		return pos;
	}

	@Override
	public String toString() {
		return "Snake [pos=" + pos + ", vel=" + vel + ", direction=" + direction + "]";
	}
	
}
