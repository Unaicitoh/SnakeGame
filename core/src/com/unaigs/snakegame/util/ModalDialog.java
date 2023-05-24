package com.unaigs.snakegame.util;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ModalDialog extends Dialog{

	public ModalDialog(String title, Skin skin) {
		super(title, skin);
		}
	

	@Override
	public void setSize(float width, float height) {

	    // Get the stage's viewport dimensions
	    float stageWidth = getStage().getViewport().getWorldWidth();
	    float stageHeight = getStage().getViewport().getWorldHeight();

	    // Calculate the position to center the dialog
	    float dialogX = (stageWidth - width) / 2f;
	    float dialogY = (stageHeight - height) / 2f;

	    setPosition(dialogX, dialogY);
	}

}
