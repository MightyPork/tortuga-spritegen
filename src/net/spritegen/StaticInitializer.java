package net.spritegen;


import com.mykaruga.models.Models;


/**
 * Initialization utility, initializing all the static stuff that is needed
 * before starting main loop.
 * 
 * @author MightyPork
 */
public class StaticInitializer {

	/**
	 * Init static things and start threads.<br>
	 * This is called on startup, even before the splash screen.
	 */
	public static void initOnStartup() {
		Models.load();
	}

	/**
	 * Initialize all.
	 */
	public static void initPostLoad() {}

}
