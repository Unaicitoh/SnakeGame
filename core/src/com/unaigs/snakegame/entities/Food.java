package com.unaigs.snakegame.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.Pools;
import com.unaigs.snakegame.data.Assets;
import com.unaigs.snakegame.engines.PoolEngine;

public class Food extends Sprite implements Poolable{

	private Vector2 pos=new Vector2();
	private boolean alive=false;
	private FoodGameListener listener;
	private int type=0;
	
	public interface FoodGameListener {
		void onFoodEaten();
	}
	
	private static Pool<Food> foodPool = Pools.get(Food.class);
	
	public static Food create(Vector2 pos, PoolEngine pool, FoodGameListener listener, Assets assets) {
		Food food = foodPool.obtain();
		food.init(pos,pool, listener, assets);
		return food;
	}
	
	public void init(Vector2 pos, PoolEngine pool, FoodGameListener listener, Assets assets) {
		this.listener=listener;
		this.pos.set(pos);
		alive=true;
		int foodType = MathUtils.random(9);
		if(foodType<9) {
			int rnd = MathUtils.random(9);
			if(rnd<5) {
				this.type=0;
				set(assets.atlas.createSprite("apple"));
			}else if(rnd<9) {
				this.type=1;
				set(assets.atlas.createSprite("cherry"));
			}else{
				this.type=2;
				set(assets.atlas.createSprite("cookie"));
			}
		}else {
			this.type=3;
			set(assets.atlas.createSprite("rabbit"));
		}
		pool.add(this);
	}
	
	public void draw(SpriteBatch batch) {
		
		setPosition(pos.x,pos.y);
		setSize(Assets.TILE_SIZE,Assets.TILE_SIZE);
		super.draw(batch);
	}
	
	@Override
	public void reset() {
		pos.set(new Vector2());
		alive=false;
	}
	
	public void release() {
		listener.onFoodEaten();
		foodPool.free(this);
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Vector2 getPos() {
		return pos;
	}

	public int getType() {
		return type;
	}

	
}
