package com.unaigs.snakegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {

	private Skin menuUI;

	public Assets() {
		menuUI= new Skin(Gdx.files.internal("menu_skin.json"));
	}
	public Skin getMenuUI() {
		return menuUI;
	}
	public void dispose() {
		menuUI.dispose();
	}
}
