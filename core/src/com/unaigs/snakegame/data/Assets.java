package com.unaigs.snakegame.data;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ray3k.stripe.scenecomposer.SceneComposerStageBuilder;

public class Assets {

	public static final int TILE_SIZE = 8;
	public static final int SCREEN_W = 960;
	public static final int SCREEN_H = 640;
	
	public Skin skinUI;
	public SceneComposerStageBuilder builder;
	public TextureAtlas atlas;
	public Map<String,Sprite> snakeParts;
	public BitmapFont mainFont;
	public BitmapFont scoreFont;
	public BitmapFont scoresFont;


	
	public Assets() {
		skinUI= new Skin(Gdx.files.internal("menu_skin.json"));
		builder = new SceneComposerStageBuilder();
		atlas = new TextureAtlas("SnakeGame.atlas");
		snakeParts = new HashMap<>();
		snakeParts.put("head_right", atlas.createSprite("head_right"));
		snakeParts.put("head_left", atlas.createSprite("head_left"));
		snakeParts.put("head_up", atlas.createSprite("head_up"));
		snakeParts.put("head_down", atlas.createSprite("head_down"));
		snakeParts.put("body", atlas.createSprite("body"));
		mainFont= new BitmapFont(Gdx.files.internal("mainFont.fnt"));
		scoreFont= new BitmapFont(Gdx.files.internal("scoreFont.fnt"));
		scoresFont= new BitmapFont(Gdx.files.internal("scoresFont.fnt"));

	}
	
	public void dispose() {
		skinUI.dispose();
		atlas.dispose();
		mainFont.dispose();
	}
	
}
