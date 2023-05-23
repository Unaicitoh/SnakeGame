package com.unaigs.snakegame.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.LongArray;

public class GameProgress {

	private static Preferences prefs;
	private static LongArray scores= new LongArray(5);
	
	private static final String PREFERENCES_NAME="snake_progress";
	
	private static final String SCORES_KEY="score";

	
	public static void save(long score) {
		prefs=Gdx.app.getPreferences(PREFERENCES_NAME);
		scores.add(score);
		scores.sort();
		scores.removeIndex(0);
		for(int i=0 ; i<scores.size;i++) {
			prefs.putLong(SCORES_KEY+i, scores.get(scores.size-1-i));
		}
		prefs.flush();
		
	}

	public static void load() {
		prefs=Gdx.app.getPreferences(PREFERENCES_NAME);
		if(scores.isEmpty()) {
			for(int i=0; i<5; i++) {
				scores.add(0);
			}
		}
		for(int i=0; i<scores.size; i++) {
			scores.set(i, prefs.getLong(SCORES_KEY+i,0));
		}
		
	}
	
}
