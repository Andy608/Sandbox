package com.bountive.sandbox;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bountive.sandbox.resources.AssetHandler;
import com.bountive.sandbox.resources.FontHandler;
import com.bountive.sandbox.screen.ScreenManager;

public class SandBox extends Game {
	
	private static SandBox gameInstance;
	public static final float MAX_DELTA = 0.15f;
	private SpriteBatch batch;
	
	public SandBox() {
		gameInstance = this;
	}
	
	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		batch = new SpriteBatch();
		InitHandler.preInit(gameInstance);
	}
	
	@Override
	public void dispose() {
		ScreenManager.getInstance(gameInstance).dispose();
		FontHandler.getInstance().dispose();
		AssetHandler.getInstance().dispose();
		batch.dispose();
	}
	
	@Override
	public void pause() {
		ScreenManager.getInstance(gameInstance).pause();
	}
	
	@Override
	public void resume() {
		ScreenManager.getInstance(gameInstance).resume();
	}
	
	@Override
	public void render() {
		ScreenManager.getInstance(gameInstance).render(Math.min(Gdx.graphics.getDeltaTime(), MAX_DELTA));
	}
	
	public SpriteBatch getBatch() {
		return batch;
	}
	
	public static SandBox getInstance() {
		return gameInstance;
	}
}
