package com.unaigs.snakegame;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ray3k.stripe.scenecomposer.SceneComposerStageBuilder;

public class Assets {

	public static final int TILE_SIZE = 8;
	public static final int SCREEN_W = 960;
	public static final int SCREEN_H = 640;
	
	private Skin menuUI;
	private SceneComposerStageBuilder builder;
	private TextureAtlas atlas;
	private HashMap<String,Sprite> snakeParts;
	
	public Assets() {
		menuUI= new Skin(Gdx.files.internal("menu_skin.json"));
		builder = new SceneComposerStageBuilder();
		atlas = new TextureAtlas("SnakeGame.atlas");
		snakeParts = new HashMap<String,Sprite>();
		snakeParts.put("head_right", atlas.createSprite("head_right"));
		snakeParts.put("head_left", atlas.createSprite("head_left"));
		snakeParts.put("head_up", atlas.createSprite("head_up"));
		snakeParts.put("head_down", atlas.createSprite("head_down"));
		snakeParts.put("body", atlas.createSprite("body"));
	}
	
	public void dispose() {
		menuUI.dispose();
		atlas.dispose();
	}
	
	public Skin getMenuUI() {
		return menuUI;
	}
	
	public SceneComposerStageBuilder getSceneBuilder() {
		return builder;
	}

	public TextureAtlas getAtlas() {
		return atlas;
	}

	public HashMap<String, Sprite> getSnakeParts() {
		return snakeParts;
	}
}
