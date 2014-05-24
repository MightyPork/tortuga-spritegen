package net.spritegen;


import net.spritegen.gui.ScreenSprites;

import com.porcupine.coord.Coord;


/**
 * Sector constants
 * 
 * @author Ondřej Hruška (MightyPork)
 */
@SuppressWarnings("javadoc")
public class Constants {

	// STRINGS
	public static final String TITLEBAR = "SpriteGen";

	// FILES+DIRS
	public static final String APP_DIR = "spritegen";

	public static final String FILE_LOG = "Runtime.log";
	public static final String FILE_LOG_E = "Error.log";

	// TIMING
	public static final int FPS_UPDATE = 55;
	public static final double SPEED_MUL = 100D / FPS_UPDATE;

	public static final int FPS_RENDER = 200; // max


//	// INITIAL WINDOW SIZE (later loaded from config file)
//	public static final int WINDOW_SIZE_X = ScreenSprites.FRAME_SIZE; //1024; FIXME
//	public static final int WINDOW_SIZE_Y = ScreenSprites.FRAME_SIZE; //768; FIXME

	
//
//	// LIGHT
//	public static final float LIGHT_AMBIENT = 1F;
//	public static final float LIGHT_SPECULAR = 0.5F;
//	public static final float LIGHT_DIFFUSE = 1F;
//	public static final Coord LIGHT_POS = new Coord(0, 3, 3);
//	public static final float LIGHT_ATTR = 1;
//
//	public static final float SCENE_MAT_AMBIENT = 1F;
//	public static final float SCENE_MAT_SPECULAR = 1F;
//	public static final float SCENE_MAT_DIFFUSE = 1F;

	// CAMERA & SCENE
	public static final Coord CAM_POS = new Coord(0, 0, 5);
	public static final Coord CAM_LOOKAT = new Coord(0, 0, 0);
	public static final float CAM_ANGLE = 45;
	public static final double CAM_NEAR = 0.1;
	public static final double FOG_START = 80;
	public static final double CAM_FAR = 100;
}
