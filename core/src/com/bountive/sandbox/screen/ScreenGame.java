package com.bountive.sandbox.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.bountive.sandbox.SandBox;
import com.bountive.sandbox.screen.transitions.FadeTransition;

public class ScreenGame extends AbstractScreen {

	public ScreenGame(SandBox instance) {
		super(instance);
	}

	@Override
	public void dispose() {
		//dispose assets
	}

	@Override
	public void pause() {
	}

	@Override
	public void updateLogic(float deltaTime) {
		if (Gdx.input.justTouched()) {
			ScreenManager.getInstance(instance).switchScreenWithTransition(new ScreenMainMenu(instance), FadeTransition.createTransition(1));
		}
	}

	@Override
	public void updateGraphics(float deltaTime) {
		Gdx.gl.glClearColor(1, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public InputProcessor getInputProcessor() {
		return null;
	}
}
