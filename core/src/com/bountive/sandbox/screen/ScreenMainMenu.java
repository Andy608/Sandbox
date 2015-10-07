package com.bountive.sandbox.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bountive.sandbox.SandBox;
import com.bountive.sandbox.resources.ImageLoader;

public class ScreenMainMenu extends AbstractScreen {
	
	private Stage stage;
	private Stack screenStack;
	
	private Viewport view;
	
	private TextButton testButton;
	
	public ScreenMainMenu(SandBox instance) {
		super(instance);
	}
	
	@Override
	public void show() {
		super.show();
		
		view = new ScalingViewport(Scaling.fill, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage = new Stage(view);
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
	public void resume() {
		super.resume();
	}
	
	@Override
	public void updateLogic(float deltaTime) {
		
	}

	@Override
	public void updateGraphics(float deltaTime) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(1, 0, 1, 1);
		
		stage.act(deltaTime);
		stage.draw();
	}
	
	@Override
	public void resize(int width, int height) {
		view.setWorldWidth(width);
		view.setWorldHeight(height);
		
		stage.getViewport().update(width, height, true);
		
		screenStack.setSize(stage.getWidth(), stage.getHeight());
		screenStack.invalidate();
	}
	
	private void buildStage() {
		Gdx.input.setInputProcessor(stage);
		stage.setDebugAll(false);
		stage.clear();
		screenStack = new Stack();
		stage.addActor(screenStack);
		screenStack.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		screenStack.add(buildTestButton());
	}
	
	private Table buildTestButton() {
		Table layer = new Table();
		
		testButton = new TextButton("This is a test! Derp! :3", ImageLoader.GUISKIN, "button_style");
		layer.add(ScreenManager.createContainer(testButton, testButton.getPrefWidth() / 2.0f, testButton.getPrefHeight() / 2.0f, 0.0f, 1.0f));
		return layer;
	}
	
	@Override
	public InputProcessor getInputProcessor() {
		return stage;
	}
}
