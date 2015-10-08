package com.bountive.sandbox.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
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
	
	private Container<Actor> singlePlayerButton;
	private Container<Actor> multiplayerButton;
	private Container<Actor> optionsButton;
	private Container<Actor> exitButton;
	
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
		stage.setDebugAll(true);
		stage.clear();
		screenStack = new Stack();
		stage.addActor(screenStack);
		screenStack.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		screenStack.add(buildButtons());
	}
	
	private Table buildButtons() {
		Table layer = new Table();
		
		singlePlayerButton = createMenuButton("Singleplayer", 0f, 1f);
		multiplayerButton = createMenuButton("Multiplayer", 0f, 1f).padTop(10);
		optionsButton = createMenuButton("Options", 0f, 1f).padTop(10);
		exitButton = createMenuButton("Exit", 0f, 1f).padTop(10);
		
		layer.left();
		layer.add(singlePlayerButton).row();
		layer.add(multiplayerButton).row();
		layer.add(optionsButton).row();
		layer.add(exitButton).row();
		
		System.out.println(singlePlayerButton.getWidth());
		
		layer.padTop(Gdx.graphics.getHeight() / 4f);
		
//		layer.add(ScreenManager.createContainer(singlePlayerButton, singlePlayerButton.getPrefWidth() / 2.0f, singlePlayerButton.getPrefHeight() / 2.0f, 0.0f, 1.0f).width(301));
		return layer;
	}
	
	private Container<Actor> createMenuButton(String text, float rotation, float scale) {
		TextButton b = new TextButton(text, ImageLoader.GUISKIN, "button_style");
		return ScreenManager.createContainer(b, b.getPrefWidth() / 2.0f, b.getPrefHeight() / 2.0f, rotation, scale).width(300);
	}
	
	@Override
	public InputProcessor getInputProcessor() {
		return stage;
	}
}
