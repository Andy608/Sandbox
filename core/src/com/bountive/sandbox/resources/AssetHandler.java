package com.bountive.sandbox.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Disposable;

public class AssetHandler implements Disposable, AssetErrorListener {

	private static AssetHandler instance = null;
	
	private AssetManager manager;
	
	private AssetHandler() {
		manager = new AssetManager();
		manager.setErrorListener(this);
	}
	
	public static AssetHandler preInit() {
		if (instance == null) {
			instance = new AssetHandler();
		}
		return instance;
	}
	
	public static AssetHandler getInstance() {
		return preInit();
	}
	
	public void loadAllAssets() {
		FontHandler.getInstance().loadFonts();
		ImageLoader.getInstance().loadImages();
	}
	
	public AssetManager getManager() {
		return manager;
	}
	
	@Override
	public void error(@SuppressWarnings("rawtypes")AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error("AssetHandler", "Couldn't load asset: " + asset.fileName + "'", (Exception)throwable);
	}

	@Override
	public void dispose() {
		if (manager != null) manager.dispose();
	}
}
