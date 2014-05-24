package net.spritegen.gui;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;

import net.spritegen.App;
import net.spritegen.Constants;
import net.spritegen.util.Log;
import net.spritegen.util.Utils;

import com.mykaruga.models.Models;
import com.porcupine.color.RGB;
import com.porcupine.coord.Coord;
import com.porcupine.math.Calc.Buffers;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;



/**
 * Splash screen
 * 
 * @author MightyPork
 */
public class ScreenSprites extends Screen {

	private static int SHEET_SIZE = App.cfg.img_size;
	public static int FRAME_SIZE = App.cfg.img_size/16;
	private static double WALK_WOBBLE_Y = App.cfg.render_wobble_y;
	private static double WALK_WOBBLE_Z = App.cfg.render_wobble_z;


	private BufferedImage image;

	@Override
	public void initScreen() {
		
		String format = "PNG";
		image = new BufferedImage(SHEET_SIZE, SHEET_SIZE, BufferedImage.TYPE_INT_RGB);

	}

	private void captureSpriteFrame(int xPos, int yPos) {

		glReadBuffer(GL_FRONT);
		int width = Display.getDisplayMode().getWidth();
		int height = Display.getDisplayMode().getHeight();
		int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
		glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		// convert to a buffered image
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int i = (x + (width * y)) * bpp;
				int r = buffer.get(i) & 0xFF;
				int g = buffer.get(i + 1) & 0xFF;
				int b = buffer.get(i + 2) & 0xFF;
				try {
					image.setRGB(x + xPos * FRAME_SIZE, (height - (y + 1)) + yPos * FRAME_SIZE, (0xFF << 24) | (r << 16) | (g << 8) | b);
				}catch(ArrayIndexOutOfBoundsException e) {
					Log.w("X,Y="+x+","+y+";  TILE POS: "+xPos+","+yPos);	
					throw e;
				}
			}
		}

	}

	private void saveSpritesheet() {
		
		File file;
		
		int i = 0;
		
		while(true) {			
			file = new File(App.modelDir, "out/spritesheet"+(i==0?"":"-"+i)+".png");
			if(!file.exists()) break;
			i++;
		}
		
		// save to disk
		try {
			ImageIO.write(image, "PNG", file);
		} catch (IOException e) {
			Log.e("Failed to save screenshot.", e);
		}
	}

	private int timer0 = 0;
	private int timer = 0;

	@Override
	public void render(double delta) {
		timer0++;
		if(timer0<5) return; 
		timer++;
		
		int frame = timer/6;
		boolean capture = (timer+3)%6==0;
		
		int MODE = 1;
		
		if(MODE==0) {
			
			// walking
			if(frame < 8) {	
				renderTurtle(90+Math.sin(((frame-(0*8D))/4D)*3.1415D)*WALK_WOBBLE_Y, Math.sin(((frame)/4D)*3.1415D)*WALK_WOBBLE_Z);
				if(capture) {
					captureSpriteFrame(frame-8*0, 0);				
				}
			}
			
	
			if(frame >= 8 && frame < 8*2) {		
				renderTurtle(180+Math.sin(((frame-(1*8D))/4D)*3.1415D)*WALK_WOBBLE_Y, Math.sin(((frame)/4D)*3.1415D)*WALK_WOBBLE_Z);
				if(capture) {
					captureSpriteFrame(frame-8*1, 1);				
				}
			}
	
			if(frame >= 8*2 && frame < 8*3) {		
				renderTurtle(270+Math.sin(((frame-(2*8D))/4D)*3.1415D)*WALK_WOBBLE_Y, Math.sin(((frame)/4D)*3.1415D)*WALK_WOBBLE_Z);
				if(capture) {
					captureSpriteFrame(frame-8*2, 2);				
				}
			}
	
			if(frame >= 8*3 && frame < 8*4) {		
				renderTurtle(0+Math.sin(((frame-(3*8D))/4D)*3.1415D)*WALK_WOBBLE_Y, Math.sin(((frame)/4D)*3.1415D)*WALK_WOBBLE_Z);
				if(capture) {
					captureSpriteFrame(frame-8*3, 3);				
				}
			}
			
			
			// rotate
			if(frame >= 8*4 && frame < 8*5) {		
				renderTurtle(90+11.25D*(frame-(4*8D)), 0);
				if(capture) {
					captureSpriteFrame(frame-8*4, 4);				
				}
			}
			
			if(frame >= 8*5 && frame < 8*6) {		
				renderTurtle(180+11.25D*(frame-(5*8D)), 0);
				if(capture) {
					captureSpriteFrame(frame-8*5, 5);				
				}
			}
			
			if(frame >= 8*6 && frame < 8*7) {		
				renderTurtle(270+11.25D*(frame-(6*8D)), 0);
				if(capture) {
					captureSpriteFrame(frame-8*6, 6);				
				}
			}
			
			if(frame >= 8*7 && frame < 8*8) {		
				renderTurtle(0+11.25D*(frame-(7*8D)), 0);
				if(capture) {
					captureSpriteFrame(frame-8*7, 7);				
				}
			}
			
			if(frame >= 8*8) {		
				saveSpritesheet();				
				App.inst.exit();
			}
		
		}else if(MODE==1) {
			
			int ROW = 16;
			
			for(int i=0; i<4; i++) {
				if(frame >= ROW*i && frame < ROW*(i+1)) {	
					renderTurtle(90*(i+1)+Math.sin(((frame-(i*ROW))/(ROW/2D))*3.1415D)*WALK_WOBBLE_Y, Math.sin(((frame)/(ROW/2D))*3.1415D)*WALK_WOBBLE_Z);
					if(capture) {
						captureSpriteFrame(frame-ROW*i, i);				
					}
				}
			}
			
			double ANGLE_ROW = 45D;
			double ANGLE_STEP = 45D/ROW;
			
			
			for(int i=0, row = 4; i<360; i+=ANGLE_ROW, row++) {
				int angle = 90 + i;
				if(angle >= 360) angle -= 360;
				
				if(frame >= ROW*row && frame < ROW*(row+1)) {		
					renderTurtle(angle+ANGLE_STEP*(frame-(row*ROW)), 0);
					if(capture) {
						captureSpriteFrame(frame-ROW*row, row);				
					}
				}				
			}
			
			
			if(frame >= 16*12) {
				saveSpritesheet();				
				App.inst.exit();
			}
		}else if(MODE==2) {
			
			int ROW = 16;
						
			double ANGLE_ROW = 90D;
			double ANGLE_STEP = 90D/ROW;
			
			
			for(int i=0, row = 0; i<360; i+=ANGLE_ROW, row++) {
				int angle = 90 + i;
				if(angle >= 360) angle -= 360;
				
				if(frame >= ROW*row && frame < ROW*(row+1)) {		
					renderTurtle(angle+ANGLE_STEP*(frame-(row*ROW)), 0);
					if(capture) {
						captureSpriteFrame(frame-ROW*row, row);				
					}
				}				
			}
			
			
			if(frame >= 16*8) {
				saveSpritesheet();				
				App.inst.exit();
			}
		}
	}

	private void clearScreen() {
		glLoadIdentity();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	private void renderTurtle(double direction, double wobble) {
		glPushMatrix();

		double scale = App.cfg.render_scale;

		glScaled(scale, scale, scale);
		glTranslated(App.cfg.render_move_x, App.cfg.renmder_move_y, 0);

		// perspective
		glRotated(App.cfg.render_rotate_x, 1, 0, 0);

		// direction
		glRotated(direction, 0, 1, 0);

		// wobble
		glRotated(wobble, 0, 0, 1);

		Models.turtle.render();
		glPopMatrix();
	}


	/**
	 * Enable fog with given parameters
	 * 
	 * @param fogStart distance from which the fog grows denser
	 * @param fogEnd distance at which the fog is densest
	 * @param fogDensity relative fog density, 1.0 default, 0.3 recommended
	 * @param fogOpacity fog opacity, 1.0 default
	 * @param fogColor fog color
	 * @param bgColor screen background color
	 * @param fogType fog type: GL_LINEAR, GL_EXP, GL_EXP2
	 */
	protected final void setupFog(double fogStart, double fogEnd, double fogDensity, double fogOpacity, RGB fogColor, RGB bgColor, int fogType) {
		// bg
		glClearColor((float) bgColor.r, (float) bgColor.g, (float) bgColor.b, 1.0F);
		//fog
//		glFogf(GL_FOG_START, (float) fogStart);
//		glFogf(GL_FOG_END, (float) fogEnd);
//		glFogi(GL_FOG_MODE, fogType);
//		glFog(GL_FOG_COLOR, Buffers.mkFillBuff((float) fogColor.r, (float) fogColor.g, (float) fogColor.b, (float) fogOpacity));
//		glFogf(GL_FOG_DENSITY, (float) fogDensity);
//		glHint(GL_FOG_HINT, GL_NICEST);

	}


	/**
	 * Set up camera
	 * 
	 * @param eyePos position of the observing eye
	 * @param centerPos point the eye is looking at
	 * @param viewAngle viewing angle (FOV)
	 * @param zNear nearest visible objects (Z axis)
	 * @param zFar farthest visible objects (Z axis)
	 */
	protected final void setupCamera(Coord eyePos, Coord centerPos, double viewAngle, double zNear, double zFar) {

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		// set up perspective

		Coord s = app.getSize();
		gluPerspective((float) viewAngle, (float) s.x / (float) s.y, (float) zNear, (float) zFar);
		gluLookAt((float) eyePos.x, (float) eyePos.y, (float) eyePos.z, (float) centerPos.x, (float) centerPos.y, (float) centerPos.z, 0F, 1F, 0F);

		glViewport(0, 0, s.xi(), s.yi());

		// back to modelview matrix
		glMatrixMode(GL_MODELVIEW);
	}

	@Override
	public void init() {
		super.init();

		initScreen();

		setupCamera(Constants.CAM_POS, Constants.CAM_LOOKAT, Constants.CAM_ANGLE, Constants.CAM_NEAR, Constants.CAM_FAR);
		setupFog(Constants.FOG_START, Constants.CAM_FAR, 1.0, 1.0, new RGB(0, 0, 0), new RGB(0, 0, 0), GL_LINEAR);


		// SETUP LIGHTS

		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);

		float spec = (float) App.cfg.light_specular;
		float amb = (float) App.cfg.light_ambient;
		float diff = (float) App.cfg.light_diffuse;
		Coord pos = App.cfg.light_pos;

		FloatBuffer buff = Buffers.alloc(4);
		Buffers.fill(buff, amb, amb, amb, 1.0f);
		glLight(GL_LIGHT0, GL_AMBIENT, buff);

		buff.clear();
		Buffers.fill(buff, diff, diff, diff, 1.0f);
		glLight(GL_LIGHT0, GL_DIFFUSE, buff);

		buff.clear();
		Buffers.fill(buff, spec, spec, spec, 1.0f);
		glLight(GL_LIGHT0, GL_SPECULAR, buff);

		buff.clear();
		Buffers.fill(buff, (float) pos.x, (float) pos.y, (float) pos.z, (float)App.cfg.light_attr);
		glLight(GL_LIGHT0, GL_POSITION, buff); //Position The Light


		// OTHER SETTINGS

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glClearDepth(1f);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);

		glEnable(GL_CULL_FACE);
		glEnable(GL_NORMALIZE);

		glShadeModel(GL_SMOOTH);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
		glEnable(GL_TEXTURE_2D);
	}

}
