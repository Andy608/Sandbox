package com.bountive.sandbox.screen.transitions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface ITransition {

	public float getDuration();
	
	public void render(SpriteBatch batch, Texture currentScreen, Texture nextScreen, float alpha);
}
