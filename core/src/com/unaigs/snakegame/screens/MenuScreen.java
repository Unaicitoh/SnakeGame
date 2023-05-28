package com.unaigs.snakegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
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
		stage = new Stage(new FitViewport(Assets.SCREEN_W,Assets.SCREEN_H));
		
		game.assets.builder.build(stage,game.assets.skinUI,Gdx.files.internal("menu_skin_main.json"));
		
		TextButton textButton= stage.getRoot().findActor("startButton");
		textButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				game.assets.music.stop();
				game.assets.sounds.get(Assets.GAME_START_S).play(.3f);
				game.setScreen(new MainScreen(game));
			}
		});
		
		textButton=stage.getRoot().findActor("scoresButton");
		textButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				game.setScreen(new ScoresScreen(game));
			}
		});
		
		textButton=stage.getRoot().findActor("exitButton");
		textButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
					new Dialog("Quitting...", game.assets.skinUI) {
						{
							setColor(Color.BLACK);
							padTop(60);
							getTitleLabel().setFontScale(1.25f);
							text("Are you sure you want to exit SNAKE?");
							button("Yes, exit", true);
							button("No, keep playing", false);
						}
						
						@Override
						protected void result(Object object) {
							if((boolean) object) {
								Gdx.app.exit();
							}
						}
						
					}.show(stage);

			}
		});

		stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1.5f), Actions.run(new Runnable() {
			
			@Override
			public void run() {
				Gdx.input.setInputProcessor(stage);
			}
		})));
		
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
