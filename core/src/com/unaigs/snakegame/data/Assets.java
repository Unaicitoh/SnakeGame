package com.unaigs.snakegame.data;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ArrayMap;
import com.ray3k.stripe.scenecomposer.SceneComposerStageBuilder;

public class Assets {

	public static final int TILE_SIZE = 8;
	public static final int SCREEN_W = 960;
	public static final int SCREEN_H = 640;
	public static final String SNAKE_BODY = "body";
	public static final String SNAKE_HEAD_RIGHT = "head_right";
	public static final String SNAKE_HEAD_LEFT = "head_left";
	public static final String SNAKE_HEAD_UP = "head_up";
	public static final String SNAKE_HEAD_DOWN = "head_down";
	public static final String SNAKE_INTRO_S = "snake_intro_s";
	public static final String GAME_OVER_S = "gameOver_s";
	public static final String SNAKE_MOVEMENT_S = "snake_movement_s";
	public static final String SPAWN_EFFECT_S = "spawn_effect_s";
	public static final String RABBIT_EFFECT_S = "rabbit_effect_s";
	public static final String FRUIT_EFFECT_S = "fruit_effect_s";
	public static final String COOKIE_EFFECT_S = "cookie_effect_s";
	public static final String GAME_START_S = "gameStart_s";
	
	public Skin skinUI;
	public SceneComposerStageBuilder builder;
	public TextureAtlas atlas;
	public Map<String,Sprite> snakeParts;
	public BitmapFont mainFont;
	public BitmapFont scoreFont;
	public BitmapFont scoresFont;
	public ArrayMap<String, Sound> sounds;
	public Music music;
	
	public Assets() {
		skinUI= new Skin(Gdx.files.internal("menu_skin.json"));
		builder = new SceneComposerStageBuilder();
		atlas = new TextureAtlas("SnakeGame.atlas");
		snakeParts = new HashMap<>();
		sounds = new ArrayMap<>();
		snakeParts.put(SNAKE_HEAD_RIGHT, atlas.createSprite(SNAKE_HEAD_RIGHT));
		snakeParts.put(SNAKE_HEAD_LEFT, atlas.createSprite(SNAKE_HEAD_LEFT));
		snakeParts.put(SNAKE_HEAD_UP, atlas.createSprite(SNAKE_HEAD_UP));
		snakeParts.put(SNAKE_HEAD_DOWN, atlas.createSprite(SNAKE_HEAD_DOWN));
		snakeParts.put(SNAKE_BODY, atlas.createSprite(SNAKE_BODY));
		mainFont= new BitmapFont(Gdx.files.internal("mainFont.fnt"));
		scoreFont= new BitmapFont(Gdx.files.internal("scoreFont.fnt"));
		scoresFont= new BitmapFont(Gdx.files.internal("scoresFont.fnt"));
		sounds.put(SNAKE_INTRO_S, Gdx.audio.newSound(Gdx.files.internal("snake_hissing.mp3")));
		sounds.put(GAME_OVER_S, Gdx.audio.newSound(Gdx.files.internal("gameOver_sound.mp3")));
		sounds.put(SNAKE_MOVEMENT_S, Gdx.audio.newSound(Gdx.files.internal("snake_movement_sound.mp3")));
		sounds.put(SPAWN_EFFECT_S, Gdx.audio.newSound(Gdx.files.internal("drops_sound.mp3")));
		sounds.put(RABBIT_EFFECT_S, Gdx.audio.newSound(Gdx.files.internal("rabbit_sound.mp3")));
		sounds.put(FRUIT_EFFECT_S, Gdx.audio.newSound(Gdx.files.internal("fruit_sound.mp3")));
		sounds.put(COOKIE_EFFECT_S, Gdx.audio.newSound(Gdx.files.internal("cookie_sound.mp3")));
		sounds.put(GAME_START_S, Gdx.audio.newSound(Gdx.files.internal("gameStart.mp3")));

		music = Gdx.audio.newMusic(Gdx.files.internal("intro_music.mp3"));

	}
	
	public void dispose() {
		for(int i=0; i<sounds.size; i++) {
			sounds.getValueAt(i).dispose();
		}
		skinUI.dispose();
		atlas.dispose();
		mainFont.dispose();
		scoreFont.dispose();
		scoresFont.dispose();
		music.dispose();
	}
	
}
