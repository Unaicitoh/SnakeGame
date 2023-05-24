package com.unaigs.snakegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.unaigs.snakegame.SnakeGame;
import com.unaigs.snakegame.data.Assets;
import com.unaigs.snakegame.data.GameProgress;
import com.unaigs.snakegame.util.FontHelper;

public class ScoresScreen extends ScreenAdapter {

	private SnakeGame game;
	 
	private Stage stage;
	private SpriteBatch batch;
	private FontHelper fhelper;

	public ScoresScreen(SnakeGame game) {
		this.game=game;
	}
	
	@Override
	public void show() {
		batch= new SpriteBatch();
		fhelper= new FontHelper(game, batch);
		stage = new Stage(new ExtendViewport(Assets.SCREEN_W,Assets.SCREEN_W),batch);
		Gdx.input.setInputProcessor(stage);
		GameProgress.load();

		game.assets.builder.build(stage,game.assets.skinUI,Gdx.files.internal("scores_skin_ui.json"));
		
		TextButton textButton= stage.getRoot().findActor("resetProgress");
		textButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				GameProgress.reset();
				GameProgress.load();
			}
		});
		
		textButton=stage.getRoot().findActor("backMenu");
		textButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				game.setScreen(new MenuScreen(game));

			}
		});
		
	}

	private void drawScores() {
		for(int i=0; i<5; i++) {
			fhelper.drawShadowed("#"+(i+1)+" "+GameProgress.getScores().get(i), 0, stage.getHeight()/1.6f-(i*80), stage.getWidth(), Align.center, fhelper.SCORES_FONT);
		}
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0,0,0,1);
		stage.act(delta);
		stage.draw();
		batch.begin();
		drawScores();
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height,true);
	}


	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
		stage.dispose();
		batch.dispose();
	}

}
