package com.bountive.sandbox;

import com.bountive.sandbox.resources.AssetHandler;
import com.bountive.sandbox.resources.FontHandler;
import com.bountive.sandbox.resources.ImageLoader;
import com.bountive.sandbox.screen.ScreenLoading;
import com.bountive.sandbox.screen.ScreenManager;

public abstract class InitHandler {

	protected static void preInit(SandBox gameInstance) {
		FontHandler.preInit();
		AssetHandler.preInit();
		ImageLoader.preInit();
		ScreenManager.preInit(new ScreenLoading(gameInstance));
	}
	
	public static void init(SandBox gameInstance) {
		ImageLoader.getInstance().bindImages();
	}
}
