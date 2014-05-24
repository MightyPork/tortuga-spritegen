package com.mykaruga.models.wavefront.parser.mtl;


import com.mykaruga.models.wavefront.loader.Material;
import com.mykaruga.models.wavefront.loader.RenderModel;
import com.mykaruga.models.wavefront.loader.Vertex;
import com.mykaruga.models.wavefront.parser.LineParser;



public class KdParser extends LineParser {

	Vertex kd = null;

	@Override
	public void incoporateResults(RenderModel wavefrontObject) {
		Material currentMaterial = wavefrontObject.getCurrentMaterial();
		currentMaterial.setKa(kd);

	}

	@Override
	public void parse() {
		kd = new Vertex();

		try {
			kd.setX(Float.parseFloat(words[1]));
			kd.setY(Float.parseFloat(words[2]));
			kd.setZ(Float.parseFloat(words[3]));
		} catch (Exception e) {
			throw new RuntimeException("VertexParser Error");
		}
	}

}
