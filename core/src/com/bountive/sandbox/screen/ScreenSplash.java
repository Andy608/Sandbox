package com.bountive.sandbox.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bountive.sandbox.SandBox;

public class ScreenSplash extends AbstractScreen {

	private Stage stage;
	private Viewport view;
	
	public ScreenSplash(SandBox gameInstance) {
		super(gameInstance);
	}
	
	@Override
	public void show() {
		super.show();
		
		view = new ScalingViewport(Scaling.fill, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage = new Stage(view);
		
		stage.setDebugAll(true);
		buildStage();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void updateLogic(float deltaTime) {
		
	}

	@Override
	public void updateGraphics(float deltaTime) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		stage.act(deltaTime);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		
	}
	
	private void buildStage() {
		
	}

	@Override
	public InputProcessor getInputProcessor() {
		return stage;
	}
}
