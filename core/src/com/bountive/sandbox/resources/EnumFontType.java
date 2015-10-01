package com.bountive.sandbox.resources;

public enum EnumFontType {
	REGULAR("OpenSans-Regular"),
	BOLD("OpenSans-Bold"),
	ITALIC("OpenSans-Italic"),
	BOLD_ITALIC("OpenSans-BoldItalic");
	
	private String fontName;
	private EnumFontType(String name) {
		fontName = name + ".ttf";
	}
	
	public String getFontName() {
		return fontName;
	}
};
