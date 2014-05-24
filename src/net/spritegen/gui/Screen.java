package net.spritegen.gui;


import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

import net.spritegen.App;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.porcupine.coord.Coord;
import com.porcupine.coord.Vec;


/**
 * Screen class.<br>
 * Screen animates 3D world, while contained panels render 2D overlays, process
 * inputs and run the game logic.
 * 
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class Screen{

	/** application instance, for easier calls */
	public App app = App.inst;

	/** RNG */
	public Random rand = new Random();


	/**
	 * handle window resize.
	 */
	public void onWindowResize() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		Coord s = app.getSize();

		glViewport(0, 0, s.xi(), s.yi());

		glOrtho(0, s.x, 0, s.y, -1000, 1000);

		glMatrixMode(GL_MODELVIEW);

		glLoadIdentity();

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_TEXTURE_2D);
	}

	/**
	 * Initialize screen
	 */
	public void init() {
		onWindowResize();

		initScreen();

		// SETUP LIGHTS

		glDisable(GL_LIGHTING);

		// OTHER SETTINGS

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glClearDepth(1f);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);

		glEnable(GL_NORMALIZE);

		glShadeModel(GL_SMOOTH);
		glDisable(GL_TEXTURE_2D);

	}

	/**
	 * Here you can initialize the screen.
	 */
	public abstract void initScreen();

	/**
	 * Update tick
	 */
	public void update() {}

	/**
	 * Render screen
	 * 
	 * @param delta delta time (position between two update ticks, to allow
	 *            super-smooth animations)
	 */
	public void render(double delta) {}

}
