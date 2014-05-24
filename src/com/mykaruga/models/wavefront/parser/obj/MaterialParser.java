package com.mykaruga.models.wavefront.parser.obj;


import com.mykaruga.models.wavefront.loader.Material;
import com.mykaruga.models.wavefront.loader.RenderModel;
import com.mykaruga.models.wavefront.parser.LineParser;



public class MaterialParser extends LineParser {
	String materialName = "";

	@Override
	public void parse() {
		materialName = words[1];
	}

	@Override
	public void incoporateResults(RenderModel wavefrontObject) {
		Material newMaterial = wavefrontObject.getMaterials().get(materialName);
		wavefrontObject.setCurrentMaterial(newMaterial);

	}



}
