package com.unaigs.snakegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ray3k.stripe.scenecomposer.SceneComposerStageBuilder;

public class Assets {

	public static final int TILE_SIZE = 16;
	
	private Skin menuUI;
	private SceneComposerStageBuilder builder;
	
	private TextureAtlas atlas;
	
	public Assets() {
		menuUI= new Skin(Gdx.files.internal("menu_skin.json"));
		builder = new SceneComposerStageBuilder();
		atlas = new TextureAtlas("SnakeGame.atlas");
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
}
