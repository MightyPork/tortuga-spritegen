package com.mykaruga.models;


import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.spritegen.App;
import net.spritegen.Constants;
import net.spritegen.textures.TextureManager;
import net.spritegen.util.Utils;

import com.mykaruga.models.wavefront.loader.RenderModel;
import com.porcupine.math.Calc;
import com.porcupine.math.Calc.Buffers;


public class Models {

	public static RenderModel turtle;

	
	public static void load() {
		turtle = new RenderModel(App.modelFile.getAbsolutePath());
	}
	
	private static int beginList = -1;
	private static int endList = -1;


	public static void renderBegin() {
		if (beginList == -1) {
			beginList = glGenLists(1);
			glNewList(beginList, GL_COMPILE);
			glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);

			glEnable(GL_TEXTURE_2D);
			glEnable(GL_COLOR_MATERIAL);
			glEnable(GL_CULL_FACE);

			float spec = (float) App.cfg.mat_specular;
			float amb = (float) App.cfg.mat_ambient;
			float diff = (float) App.cfg.mat_diffuse;

			FloatBuffer buff = Calc.Buffers.alloc(4);

			Calc.Buffers.fill(buff, amb, amb, amb, 1.0f);
			glMaterial(GL_FRONT, GL_AMBIENT, Buffers.mkFillBuff(amb, amb, amb, 1f));

			buff.clear();
			Calc.Buffers.fill(buff, spec, spec, spec, 1.0f);
			glLight(GL_LIGHT0, GL_SPECULAR, buff);
			glMaterial(GL_FRONT, GL_SPECULAR, Buffers.mkFillBuff(spec, spec, spec, 1f));

			buff.clear();
			Calc.Buffers.fill(buff, diff, diff, diff, 1.0f);
			glLight(GL_LIGHT0, GL_DIFFUSE, buff);
			glMaterial(GL_FRONT, GL_DIFFUSE, Buffers.mkFillBuff(diff, diff, diff, 1f));
			glMaterialf(GL_FRONT, GL_SHININESS, 8);

			glColorMaterial(GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE);

			glColor4d(1, 1, 1, 1);
			glEndList();
		}
		glCallList(beginList);
	}

	public static void renderEnd() {
		if (endList == -1) {
			endList = glGenLists(1);
			glNewList(endList, GL_COMPILE);
			glDisable(GL_COLOR_MATERIAL);
			glDisable(GL_CULL_FACE);
			TextureManager.unbind();
			glDisable(GL_TEXTURE_2D);
			glEndList();
		}
		glCallList(endList);
	}

}
