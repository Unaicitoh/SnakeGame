package com.unaigs.snakegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.unaigs.snakegame.SnakeGame;
import com.unaigs.snakegame.data.Assets;

public class MenuScreen extends ScreenAdapter {

	private SnakeGame game;
	
	private Stage stage;
	
	public MenuScreen(SnakeGame game) {

		this.game=game;
	}
	
	@Override
	public void show() {
		stage = new Stage(new ExtendViewport(Assets.SCREEN_W,Assets.SCREEN_W));
		Gdx.input.setInputProcessor(stage);

		
		game.assets.builder.build(stage,game.assets.skinUI,Gdx.files.internal("menu_skin_main.json"));
		
		TextButton textButton= stage.getRoot().findActor("startButton");
		textButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				game.setScreen(new MainScreen(game));
			}
		});
		
		textButton=stage.getRoot().findActor("exitButton");
		textButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0,0,0,1);
		stage.act(delta);
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height,true);
	}


	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
		stage.dispose();
	}

}
