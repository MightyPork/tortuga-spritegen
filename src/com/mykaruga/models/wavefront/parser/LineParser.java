package com.mykaruga.models.wavefront.parser;


import com.mykaruga.models.wavefront.loader.RenderModel;


public abstract class LineParser {

	protected String[] words = null;

	public void setWords(String[] words) {
		this.words = words;
	}

	public abstract void parse();

	public abstract void incoporateResults(RenderModel wavefrontObject);

}
