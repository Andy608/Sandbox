package com.bountive.sandbox.resources;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.Disposable;

public class FontHandler implements Disposable {

	public static FontHandler instance;
	
	private FreeTypeFontGenerator regularGenerator;
	private FreeTypeFontGenerator boldGenerator;
	private FreeTypeFontGenerator italicGenerator;
	private FreeTypeFontGenerator boldItalicGenerator;
	
	private FreeTypeFontParameter params;
	
	public static BitmapFont regular12;
	public static BitmapFont regular24;
	public static BitmapFont regular72;
	
	private static GlyphLayout layout;
	
	private boolean arePreFontsLoaded;
	private boolean areFontsLoaded;
	
	private FontHandler() {
		params = new FreeTypeFontParameter();
		layout = new GlyphLayout();
	}
	
	public static FontHandler preInit() {
		if (instance == null) {
			instance = new FontHandler();
		}
		return instance;
	}
	
	public static FontHandler getInstance() {
		return preInit();
	}
	
	public void loadPreFonts() {
		if (arePreFontsLoaded) return;
		
		FileHandleResolver resolver = new InternalFileHandleResolver();
		AssetHandler.getInstance().getManager().setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		AssetHandler.getInstance().getManager().setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
		
		AssetHandler.getInstance().getManager().load("fonts/" + EnumFontType.REGULAR.getFontName(), FreeTypeFontGenerator.class);
		arePreFontsLoaded = true;
	}
	
	public void initPreFonts() {
		if (!arePreFontsLoaded) return;
		
		regularGenerator = AssetHandler.getInstance().getManager().get("fonts/" + EnumFontType.REGULAR.getFontName(), FreeTypeFontGenerator.class);
		
		regular12 = generateFont(EnumFontType.REGULAR, 12);
		regular24 = generateFont(EnumFontType.REGULAR, 24);
	}
	
	public void loadFonts() {
		if (areFontsLoaded) return;
		
		AssetHandler.getInstance().getManager().load("fonts/" + EnumFontType.BOLD.getFontName(), FreeTypeFontGenerator.class);
		AssetHandler.getInstance().getManager().load("fonts/" + EnumFontType.ITALIC.getFontName(), FreeTypeFontGenerator.class);
		AssetHandler.getInstance().getManager().load("fonts/" + EnumFontType.BOLD_ITALIC.getFontName(), FreeTypeFontGenerator.class);
		areFontsLoaded = true;
	}
	
	public void initFonts() {
		if (!areFontsLoaded) return;
		
		boldGenerator = AssetHandler.getInstance().getManager().get("fonts/" + EnumFontType.BOLD.getFontName(), FreeTypeFontGenerator.class);
		italicGenerator = AssetHandler.getInstance().getManager().get("fonts/" + EnumFontType.ITALIC.getFontName(), FreeTypeFontGenerator.class);
		boldItalicGenerator = AssetHandler.getInstance().getManager().get("fonts/" + EnumFontType.BOLD_ITALIC.getFontName(), FreeTypeFontGenerator.class);
		
		regular72 = generateFont(EnumFontType.REGULAR, 72);
	}
	
	public boolean areFontsLoaded() {
		return areFontsLoaded;
	}
	
	public boolean arePreFontsLoaded() {
		return arePreFontsLoaded;
	}
	
	private BitmapFont generateFont(EnumFontType font, int fontSize) {
		params.size = fontSize;
		
		switch(font) {
		case BOLD: return boldGenerator.generateFont(params);
		case ITALIC: return italicGenerator.generateFont(params);
		case BOLD_ITALIC: return boldItalicGenerator.generateFont(params);
		case REGULAR: default: return regularGenerator.generateFont(params);
		}
	}
	
	public GlyphLayout bindText(BitmapFont font, String text) {
		layout.setText(font, text);
		return layout;
	}

	@Override
	public void dispose() {
		//Don't need to dispose the generators because asset manager is keeping track of them.
		if (regular12 != null) regular12.dispose();
		if (regular24 != null) regular24.dispose();
		if (regular72 != null) regular72.dispose();
	}
}
