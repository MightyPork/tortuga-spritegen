package com.mykaruga.models.wavefront.parser.mtl;


import com.mykaruga.models.wavefront.loader.LineParserFactory;
import com.mykaruga.models.wavefront.loader.RenderModel;
import com.mykaruga.models.wavefront.parser.CommentParser;



public class MtlLineParserFactory extends LineParserFactory {



	public MtlLineParserFactory(RenderModel object) {
		this.object = object;
		parsers.put("newmtl", new MaterialParser());
		parsers.put("Kd", new KdParser());
		parsers.put("map_Kd", new KdMapParser(object));
		parsers.put("#", new CommentParser());
	}



}
