package com.bountive.sandbox.resources;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Disposable;

public class ImageLoader implements Disposable {
	
	public static ImageLoader instance = null;
	
	private boolean areTexturesLoaded = false;
	
	public static Skin GUISKIN;
	
	public static ImageLoader preInit() {
		if (instance == null) {
			instance = new ImageLoader();
		}
		return instance;
	}
	
	public static ImageLoader getInstance() {
		return preInit();
	}
	
	//TODO:Add images to load in this method!
	protected void loadImages() {
		AssetHandler.getInstance().getManager().load(ResourcePaths.GUI_UI_ATLAS, TextureAtlas.class);
	}
	
	public void bindImages() {
		if (areTexturesLoaded) return;
		System.out.println("Binding images!");
		
		
		//TODO:MOVE SKIN CREATION SOMEWHERE ELSE
		GUISKIN = new Skin(AssetHandler.getInstance().getManager().get(ResourcePaths.GUI_UI_ATLAS, TextureAtlas.class));
		
		TextButtonStyle style = new TextButtonStyle();
		style.up = GUISKIN.getDrawable("button_normal");
		style.over = GUISKIN.getDrawable("button_hover");
		style.down = GUISKIN.getDrawable("button_pressed");
		style.font = FontHandler.regular24;
		style.fontColor = Color.WHITE;
		style.overFontColor = new Color(1f, 1f, 0x9F / 255f, 1f);
		GUISKIN.add("button_style", style);
		///////////////////////////////////////////////////
		
		filterImage(GUISKIN.getAtlas(), TextureFilter.Linear, TextureFilter.Linear);
		
		areTexturesLoaded = true;
	}
	
	public static void filterImage(TextureAtlas a, TextureFilter minFilter, TextureFilter magFilter) {
		for (Texture t : a.getTextures()) {
			t.setFilter(minFilter, magFilter);
		}
	}

	@Override
	public void dispose() {
		GUISKIN.dispose();
	}
}
