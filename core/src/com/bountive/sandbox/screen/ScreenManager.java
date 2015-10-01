package com.bountive.sandbox.screen;

import com.badlogic.gdx.Gdx;
import com.bountive.sandbox.SandBox;
import com.bountive.sandbox.screen.transitions.ITransition;
import com.bountive.sandbox.screen.transitions.TransitionManager;

public class ScreenManager {

	private static ScreenManager instance = null;
	private AbstractScreen currentScreen;
	
	protected ScreenManager(AbstractScreen startingScreen) {
		setCurrentScreen(startingScreen);
	}
	
	public static void init(AbstractScreen startingScreen) {
		instance = new ScreenManager(startingScreen);
	}
	
	/**
	 * @return: The instance of ScreenManager, or creates a new instance if null.
	 */
	public static ScreenManager getInstance(SandBox gameInstance) {
		if (instance == null) {
			init(new ScreenLoading(gameInstance));
		}
		return instance;
	}
	
	public void dispose() {
		if (currentScreen != null) currentScreen.dispose();
		TransitionManager.getInstance().dispose();
	}
	
	public void pause() {
		if (currentScreen != null) currentScreen.pause();
	}
	
	public void render(float deltaTime) {
		if (currentScreen != null) {
			if (!TransitionManager.getInstance().isTransitioning()) {
				currentScreen.render(deltaTime);
			}
			else {
				TransitionManager.getInstance().renderTransition(deltaTime);
			}
		}
	}
	
	/**
	 * Resizes the screen to the correct dimensions.
	 * @param width: The width.
	 * @param height: The height.
	 */
	public void resizeScreen(int width, int height) {
		if (currentScreen != null) currentScreen.resize(width, height);
	}
	
	public void resume() {
		if (currentScreen != null) currentScreen.resume();
	}
	
	private void setCurrentScreen(AbstractScreen newScreen) {
		if (currentScreen != null) {
			currentScreen.dispose();
		}
		currentScreen = newScreen;
		SandBox.getInstance().setScreen(currentScreen);
	}
	
	/**
	 * Switches to the new screen and disposes of the old one.
	 * @param newScreen: the new screen.
	 */
	public void switchScreen(AbstractScreen newScreen) {
		if (newScreen == null) {
			Gdx.app.log("[ScreenManager.switchScreen()]", "Could not change Screens!");
			return;
		}
		
		setCurrentScreen(newScreen);
	}
	
	/**
	 * Switches to the new screen with an added transition effect and disposes of the old screen.
	 * @param newScreen: The new screen.
	 * @param transition: The transition to be done.
	 */
	public void switchScreenWithTransition(AbstractScreen newScreen, ITransition transition) {
		if (newScreen == null) {
			Gdx.app.log("[ScreenManager.switchScreenWithTransition()]", "Could not change Screens!");
			return;
		}
		
		if (TransitionManager.getInstance().isTransitioning()) {
			switchScreen(newScreen);
		}
		else {
			TransitionManager.getInstance().startTransition(currentScreen, newScreen, transition);
		}
	}
	
	/**
	 * @return: The current screen.
	 */
	public AbstractScreen getCurrentScreen() {
		return currentScreen;
	}
}
