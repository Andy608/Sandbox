package com.bountive.sandbox.screen;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bountive.sandbox.InitHandler;
import com.bountive.sandbox.SandBox;
import com.bountive.sandbox.resources.AssetHandler;
import com.bountive.sandbox.resources.FontHandler;
import com.bountive.sandbox.resources.ImageLoader;
import com.bountive.sandbox.resources.ResourcePaths;

public class ScreenLoading extends AbstractScreen {

	private static final String texturePath = ResourcePaths.LOADSCREEN_ATLAS;
	
	private Skin loadingSkin;
	private Stage stage;
	private Stack screenStack;
	
	private Container<Actor> outerCircleWrapper;
	private Container<Actor> innerCircleWrapper;
	private Container<Actor> loadingBarWrapper;
	
	private Label percentGauge;
	private Label loadingInfo;
	
	private float percent;
	private Viewport view;
	private Color backgroundColor;
	
	public ScreenLoading(SandBox instance) {
		super(instance);
	}
	
	@Override
	public void show() {
		super.show();
		
		if (!FontHandler.getInstance().arePreFontsLoaded()) {
			FontHandler.getInstance().loadPreFonts();
		}
		
		AssetHandler.getInstance().getManager().load(texturePath, TextureAtlas.class);
		
		AssetHandler.getInstance().getManager().finishLoading();
		FontHandler.getInstance().initPreFonts();
		
		loadingSkin = new Skin(AssetHandler.getInstance().getManager().get(texturePath, TextureAtlas.class));
		ImageLoader.filterImage(loadingSkin.getAtlas(), TextureFilter.Linear, TextureFilter.Linear);
		
		view = new ScalingViewport(Scaling.fill, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage = new Stage(view);
		buildStage();
		
		backgroundColor = new Color();
		AssetHandler.getInstance().loadAllAssets();
	}
	
	@Override
	public void dispose() {
		stage.dispose();
		loadingSkin.dispose();
	}

	@Override
	public void pause() {}
	
	@Override
	public void resume() {
		if (Gdx.app.getType().equals(ApplicationType.Android)) {
			ScreenManager.getInstance(instance).switchScreen(new ScreenLoading(instance));
		}
	}

	@Override
	public void updateLogic(float deltaTime) {
		percent = Interpolation.linear.apply(percent, AssetHandler.getInstance().getManager().getProgress(), 0.1f);
		loadingBarWrapper.getActor().setWidth(Gdx.graphics.getWidth() * percent);
		
//		percentGauge.setText((int)(AssetHandler.getInstance().getManager().getProgress() * 100) + "%");
		percentGauge.setText((int)(Math.ceil(percent * 100)) + "%");
		
		outerCircleWrapper.rotateBy(45 * deltaTime);
		innerCircleWrapper.rotateBy(-90 * deltaTime);
		
		//In reality once this happens becomes true, it will move to the next screen instantly and not get
		//called again.
//		if (AssetHandler.getInstance().getManager().update()) {
		if (AssetHandler.getInstance().getManager().update() && Math.ceil(percent * 100) == 100) {
			InitHandler.init(instance);
			ScreenManager.getInstance(instance).switchScreen(new ScreenMainMenu(instance));
			
//			if (Gdx.input.justTouched()) {
//				FontHandler.getInstance().initFonts();
//				ScreenManager.getInstance(instance).switchScreen(new ScreenMainMenu(instance));
//			}
//			else if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
//				ScreenManager.getInstance(instance).switchScreen(new ScreenLoading(instance));
//			}
		}
	}

	@Override
	public void updateGraphics(float deltaTime) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		setBackgroundColor(percent);
		Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, 1.0f);
		
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
	
	@Override
	public void hide() {
		super.hide();
		AssetHandler.getInstance().getManager().unload(texturePath);
	}
	
	private void buildStage() {
		stage.setDebugAll(false);
		stage.clear();
		screenStack = new Stack();
		stage.addActor(screenStack);
		screenStack.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		screenStack.add(buildOuterCircle());
		screenStack.add(buildInnerCircle());
		screenStack.add(buildPercentGauge());
		screenStack.add(buildLoadingBar());
		screenStack.add(buildLoadingInfo());
	}
	
	private Table buildOuterCircle() {
		Table layer = new Table();
		Image outerCircle = new Image(loadingSkin, "outer_circle");
		outerCircleWrapper = ScreenManager.createContainer(outerCircle, outerCircle.getPrefWidth() / 2.0f, outerCircle.getPrefHeight() / 2.0f, 0.0f, 1.0f);
		layer.add(outerCircleWrapper);
		return layer;
	}
	
	private Table buildLoadingBar() {
		Table layer = new Table();
		Image loadingBar = new Image(loadingSkin, "loading_bar");
		loadingBarWrapper = ScreenManager.createContainer(loadingBar, loadingBar.getPrefWidth() / 2.0f, loadingBar.getPrefHeight() / 2.0f, 0.0f, 1.0f).padTop(Gdx.graphics.getHeight() / 2.5f);
		layer.left();
		layer.add(loadingBarWrapper);
		return layer;
	}
	
	private Table buildLoadingInfo() {
		Table layer = new Table();
		LabelStyle style = new LabelStyle();
		style.font = FontHandler.regular12;
		loadingInfo = new Label("Loading...", style);
		layer.left().padTop(Gdx.graphics.getHeight() / 2.2f).padLeft(Gdx.graphics.getWidth() / 32);
		layer.add(loadingInfo);
		return layer;
	}
	
	private Table buildInnerCircle() {
		Table layer = new Table();
		Image innerCircle = new Image(loadingSkin, "inner_circle");
		innerCircleWrapper = ScreenManager.createContainer(innerCircle, innerCircle.getPrefWidth() / 2.0f, innerCircle.getPrefHeight() / 2.0f, 0.0f, 1.0f);
		layer.add(innerCircleWrapper);
		return layer;
	}
	
	private Table buildPercentGauge() {
		Table layer = new Table();
		LabelStyle style = new LabelStyle();
		style.font = FontHandler.regular24;
		percentGauge = new Label(percent + "%", style);
		layer.add(percentGauge);
		return layer;
	}
	
//	private Container<Actor> createContainer(Actor actor, float originX, float originY, float rotation, float scaleXY) {
//		Container<Actor> wrapper = new Container<Actor>(actor);
//		
//		wrapper.setTransform(true);
//		wrapper.setOrigin(originX, originY);
//		wrapper.setRotation(rotation);
//		wrapper.setScale(scaleXY);
//		
//		return wrapper;
//	}
	
	private static final Color[] transitionColors = new Color[] {
			new Color(0x54 / 255f, 0x09 / 255f, 0x2A / 255f, 1.0f),
			new Color(0x7D / 255f, 0x06 / 255f, 0x2C / 255f, 1.0f),
			new Color(0xCA / 255f, 0x05 / 255f, 0x33 / 255f, 1.0f),
			new Color(0xFC / 255f, 0x52 / 255f, 0x3B / 255f, 1.0f),
			new Color(0xFF / 255f, 0x62 / 255f, 0x37 / 255f, 1.0f),
			new Color(0xFF / 255f, 0x94 / 255f, 0x22 / 255f, 1.0f),
			new Color(0xFF / 255f, 0xC0 / 255f, 0x01 / 255f, 1.0f),
			new Color(0xF9 / 255f, 0xE9 / 255f, 0x3E / 255f, 1.0f)
	};
	
	private void setBackgroundColor(float alpha) {
		float segmentAlpha = 1.0f / (float)transitionColors.length;
		
		int index = (int)(alpha / segmentAlpha);
		
		if (index >= transitionColors.length - 1) {
			backgroundColor.set(transitionColors[transitionColors.length - 1]);
		}
		else {
			float percentThroughIndex = ((alpha) - ((index) * segmentAlpha)) / segmentAlpha;
			float weight1 = 1 - percentThroughIndex;
			
			backgroundColor.set((transitionColors[index].r * weight1) + (transitionColors[index + 1].r * (1 - weight1)),
								(transitionColors[index].g * weight1) + (transitionColors[index + 1].g * (1 - weight1)),
								(transitionColors[index].b * weight1) + (transitionColors[index + 1].b * (1 - weight1)),
								(transitionColors[index].a * weight1) + (transitionColors[index + 1].a * (1 - weight1)));
		}
	}
	
	@Override
	public InputProcessor getInputProcessor() {
		return stage;
	}
}
