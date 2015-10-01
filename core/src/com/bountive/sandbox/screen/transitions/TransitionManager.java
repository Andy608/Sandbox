package com.bountive.sandbox.screen.transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Disposable;
import com.bountive.sandbox.SandBox;
import com.bountive.sandbox.screen.AbstractScreen;
import com.bountive.sandbox.screen.ScreenManager;

public class TransitionManager implements Disposable {

	private static TransitionManager instance = null;
	
	private FrameBuffer currentFrameBuffer;
	private FrameBuffer nextFrameBuffer;
	private SpriteBatch batch;
	private float currentDuration;
	private ITransition transition;
	private boolean isTransitioning;
	
	private AbstractScreen currentScreen;
	private AbstractScreen nextScreen;
	
	protected TransitionManager() {
		currentFrameBuffer = new FrameBuffer(Format.RGB888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		nextFrameBuffer = new FrameBuffer(Format.RGB888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		batch = new SpriteBatch();
		isTransitioning = false;
	}
	
	public static TransitionManager getInstance() {
		if (instance == null) {
			instance = new TransitionManager();
		}
		return instance;
	}
	
	@Override
	public void dispose() {
		currentFrameBuffer.dispose();
		nextFrameBuffer.dispose();
		batch.dispose();
	}
	
	public void startTransition(AbstractScreen cScreen, AbstractScreen nScreen, ITransition t) {
		if (nScreen != null) {
			isTransitioning = true;
			nextScreen = nScreen;
			nextScreen.show();
			nextScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			nextScreen.render(0);
			
			if (cScreen != null) {
				currentScreen = cScreen;
				Gdx.input.setInputProcessor(null);
				transition = t;
				currentDuration = 0;
			}
		}
	}
	
	public void renderTransition(float deltaTime) {
		float totalDuration = 0;
		if (transition != null) {
			totalDuration = transition.getDuration();
		}
		currentDuration = Math.min(currentDuration + deltaTime, totalDuration);
		
		if (transition == null || currentDuration >= totalDuration) {
			if (currentScreen != null) {
				currentScreen.hide();
			}
			
			nextScreen.resume();
			Gdx.input.setInputProcessor(nextScreen.getInputProcessor());
			endTransition();
		}
		else {
			currentFrameBuffer.begin();
			if (currentScreen != null) {
				currentScreen.render(deltaTime);
			}
			currentFrameBuffer.end();
			nextFrameBuffer.begin();
			nextScreen.render(deltaTime);
			nextFrameBuffer.end();
			
			float alpha = currentDuration / totalDuration;
			transition.render(batch, currentFrameBuffer.getColorBufferTexture(), nextFrameBuffer.getColorBufferTexture(), alpha);
		}
	}
	
	private void endTransition() {
		ScreenManager.getInstance(SandBox.getInstance()).switchScreen(nextScreen);
		isTransitioning = false;
		currentScreen = null;
		nextScreen = null;
		transition = null;
	}

	public boolean isTransitioning() {
		return isTransitioning;
	}
}
