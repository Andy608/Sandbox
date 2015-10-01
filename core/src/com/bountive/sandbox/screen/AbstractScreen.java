package com.bountive.sandbox.screen;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.bountive.sandbox.SandBox;

public abstract class AbstractScreen implements Screen {
	
	protected SandBox instance;
	
	public AbstractScreen(SandBox gameInstance) {
		instance = gameInstance;
	}
	
	@Override
	public abstract void dispose();
	
	@Override
	public void hide() {
		Gdx.input.setCatchBackKey(false);
	}
	
	@Override
	public abstract void pause();
	
	@Override
	public void render(float delta) {
		updateLogic(delta);
		updateGraphics(delta);
	}
	
	public abstract void updateLogic(float deltaTime);
	public abstract void updateGraphics(float deltaTime);

	@Override
	public abstract void resize(int width, int height);

	@Override
	public void resume() {
		if (Gdx.app.getType().equals(ApplicationType.Android)) {
			//Reload assets.
		}
	}
	
	@Override
	public void show() {
		Gdx.input.setCatchBackKey(true);
	}

	public abstract InputProcessor getInputProcessor();
}
