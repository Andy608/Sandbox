package com.bountive.sandbox.resources;

public abstract class ResourcePaths {

	private static final String ATLAS = ".atlas";
	
	private static final String IMAGES_FOLDER = "images/";
	private static final String STARTUP_FOLDER = IMAGES_FOLDER + "startup/";
	private static final String GUI_FOLDER = IMAGES_FOLDER + "gui/";
	
	public static final String LOADSCREEN_ATLAS = STARTUP_FOLDER + "loading_ui" + ATLAS;
	
	public static final String GUI_UI_ATLAS = GUI_FOLDER + "gui_ui" + ATLAS;
}
