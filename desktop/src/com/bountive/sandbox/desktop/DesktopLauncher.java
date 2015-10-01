package com.bountive.sandbox.desktop;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.bountive.sandbox.SandBox;
import com.bountive.sandbox.util.Info;

public class DesktopLauncher {
	
	private static boolean rebuildAtlas = true;
	
	public static void main (String[] arg) {
		if (rebuildAtlas) {
			Settings settings = new Settings();
			settings.maxWidth = 1024;
			settings.maxHeight = 1024;
			settings.duplicatePadding = false;
			settings.debug = false;
			
			TexturePacker.process(settings, "assets/images/loading", "../android/assets/images/startup", "loading_ui");
		}
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Info.TITLE + " | " + Info.VERSION + " | " + Info.AUTHOR;
		DisplayMode dm = LwjglApplicationConfiguration.getDesktopDisplayMode();
		
		//Get options file and see what width and height were.
		config.width = dm.width / 2;
		config.height = dm.height / 2;
		new LwjglApplication(new SandBox(), config);
	}
}
