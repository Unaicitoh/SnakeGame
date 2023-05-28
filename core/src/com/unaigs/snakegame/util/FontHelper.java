package com.unaigs.snakegame.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.unaigs.snakegame.SnakeGame;

public class FontHelper {

	public SnakeGame game;
	public SpriteBatch batch;
	public static final String MAIN_FONT = "main";
	public static final String SCORE_FONT = "score";
	public static final String SCORES_FONT = "scores";


	public FontHelper(SnakeGame game, SpriteBatch batch) {
		this.game=game;
		this.batch=batch;
	}
	
	public void drawShadowed(String s, float x, float y, float target, int align, String fontName, Color color) {
		BitmapFont font= new BitmapFont();
		if(fontName.equalsIgnoreCase(MAIN_FONT)){
			font=game.assets.mainFont;
			font.setColor(color);
			drawOutline(s, x, y, target, align,4,font);
		}else if(fontName.equalsIgnoreCase(SCORE_FONT)){
			font=game.assets.scoreFont;
			font.setColor(color);
			drawOutline(s, x, y, target, align,1,font);
		}else {
			font=game.assets.scoresFont;
			font.setColor(color);
			drawOutline(s, x, y, target, align,8,font);
		}
		font.setColor(Color.WHITE);
		font.draw(batch,s,x,y,target,align,false);
	}

	private void drawOutline(String s, float x, float y, float target, int align, int outlineWidth, BitmapFont font) {
		
		for(int i=-outlineWidth; i<=1; i++) {
			for(int j=-1; j<=1; j++) {
				font.draw(batch,s,x+i,y+j,target,align,false);
			}
		}
	}
	
}
