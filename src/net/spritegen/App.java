package net.spritegen;


import static org.lwjgl.opengl.GL11.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.*;

import net.spritegen.gui.Screen;
import net.spritegen.gui.ScreenSprites;
import net.spritegen.util.Log;
import net.spritegen.util.Utils;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import com.porcupine.coord.Coord;
import com.porcupine.time.FpsMeter;
import com.porcupine.time.Timer;
import com.porcupine.util.FileUtils;



/**
 * SECTOR main class
 * 
 * @author MightyPork
 */
public class App {

	/** instance */
	public static App inst;

	/** current screen */
	public static Screen screen = null;

	public static String[] args;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		App.args = args;

		inst = new App();
		try {
			inst.start();
		} catch (Throwable t) {
			showCrashReport(t);
		}

	}

	/**
	 * Show crash report dialog with error stack trace.
	 * 
	 * @param error
	 */
	public static void showCrashReport(Throwable error) {


		Log.e(error);

		try {
			inst.deinit();
		} catch (Throwable t) {}

		JFrame f = new JFrame("SpriteGen has crashed!");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.getContentPane().setLayout(new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS));


		StringWriter sw = new StringWriter();
		error.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();

		String errorLogAsString = "Not found.";
		String wholeLogAsString = "Not found.";

		try {
			wholeLogAsString = FileUtils.fileToString(Utils.getGameSubfolder(Constants.FILE_LOG));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			errorLogAsString = FileUtils.fileToString(Utils.getGameSubfolder(Constants.FILE_LOG_E));
		} catch (Exception e) {
			e.printStackTrace();
		}

		String txt = "";
		txt = "";
		txt += "SPRITE-GEN HAS CRASHED!\n";
		txt += "\n";
		txt += "Please report it to MightyPork:\n";
		txt += "\tE-mail: ondra@ondrovo.com\n";
		txt += "\tTwitter: #MightyPork (post log via pastebin.com)\n";
		txt += "\n";
		txt += "\n";
		txt += "\n";
		txt += "### STACK TRACE ###\n";
		txt += "\n";
		txt += exceptionAsString + "\n";
		txt += "\n";
		txt += "\n";
		txt += "### ERROR LOG ###\n";
		txt += "\n";
		txt += errorLogAsString + "\n";
		txt += "\n";
		txt += "\n";
		txt += "### FULL LOG ###\n";
		txt += "\n";
		txt += wholeLogAsString + "\n";


		// Create Scrolling Text Area in Swing
		JTextArea ta = new JTextArea(txt, 20, 70);
		ta.setFont(new Font("Courier", 0, 16));
		ta.setMargin(new Insets(10, 10, 10, 10));
		ta.setEditable(false);
		ta.setLineWrap(false);
		JScrollPane sbrText = new JScrollPane(ta);
		sbrText.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createEtchedBorder()));
		sbrText.setWheelScrollingEnabled(true);
		sbrText.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sbrText.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);


		// Create Quit Button
		JButton btnQuit = new JButton("Quit");
		btnQuit.setAlignmentX(Component.CENTER_ALIGNMENT);

		btnQuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(btnQuit);


		f.getContentPane().add(sbrText);
		f.getContentPane().add(buttonPane);

		// Close when the close button is clicked
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



		//Display Frame
		f.pack(); // Adjusts frame to size of components

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		f.setLocation((dim.width - f.getWidth()) / 2, (dim.height - f.getHeight()) / 2);

		f.setVisible(true);

		while (true) {}
	}

	public static File modelFile;

	public static File modelDir;

	public static SetupLoader cfg;



	private void deinit() {
		Display.destroy();
		Mouse.destroy();
		Keyboard.destroy();
		AL.destroy();
	}

	/**
	 * Quit to OS
	 */
	public void exit() {
		deinit();
		System.exit(0);
	}

	/**
	 * Get current screen
	 * 
	 * @return screen
	 */
	public Screen getScreen() {
		return screen;
	}

	/**
	 * Get screen size
	 * 
	 * @return size
	 */
	public Coord getSize() {
		return new Coord(Display.getWidth(), Display.getHeight());
	}

// INIT

	private void init() throws LWJGLException {

		Log.setPrintToStdout(true);

		if (args.length == 0) {
			System.out.println("Args: folder/model.obj\n" +
					"SpriteGen operates in .spritegen folder.\n" +
					"There is a folder \".spritegen/models\", which contains subfolders with your models.\n" +
					"\n" +
					"Spritesheet settings are found in \"models/modelDir/config.ini\". This file will\n" +
					"be generated while creating first spritesheet from that folder.\n" +
					"\n" +
					"Output PNG image will be saved in \"models/modelDir/out\".\n" +
					"\n" +
					"Example usage: java -jar spritegen.jar turtle/turtle.obj");
		}

		File dir = Utils.getGameSubfolder("models");
		dir.mkdirs();

		if (args.length == 0) {
			exit();
			return;
		}

		modelFile = Utils.getGameSubfolder("models/" + args[0]);

		modelDir = modelFile.getParentFile();
		File out = new File(modelDir,"out");
		out.mkdirs();

		cfg = new SetupLoader(modelDir);
		Log.i("Output image size: " + cfg.img_size + "x" + cfg.img_size);

		// init display
		Display.setDisplayMode(new DisplayMode(ScreenSprites.FRAME_SIZE, ScreenSprites.FRAME_SIZE));
		Display.setResizable(false);
		Display.setVSyncEnabled(true);
		Display.setTitle(Constants.TITLEBAR);

		int samples = cfg.samples;
		while (true) {
			try {
				Display.create(new PixelFormat().withSamples(samples).withAlphaBits(4));
				Log.i("Created display with " + samples + "x multisampling.");
				break;
			} catch (LWJGLException e) {
				Log.w("Failed to create display with " + samples + "x multisampling, trying " + samples / 2 + "x.");
				if (samples >= 2) {
					samples /= 2;
				} else if (samples == 1) {
					samples = 0;
				} else if (samples == 0) {
					Log.e("Could not create display.", e);
					exit();
				}
			}
		}

		Mouse.create();
		Keyboard.create();
		Keyboard.enableRepeatEvents(false);

		StaticInitializer.initOnStartup();
	}

	private void start() throws LWJGLException {

		Log.enable(true);
		Log.setPrintToStdout(true);

		init();
		mainLoop();
		deinit();
	}

// INIT END


// UPDATE LOOP

	/** fps meter */
	public FpsMeter fpsMeter;

	/** timer */
	public Timer timer;

	private int timerAfterResize = 0;

	private void mainLoop() {
		screen = new ScreenSprites();

		screen.init();

		timer = new Timer(Constants.FPS_UPDATE);
		fpsMeter = new FpsMeter();

		while (!Display.isCloseRequested()) {
			glLoadIdentity();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			int j = timer.ticksMissed;

			if (j > 1) fpsMeter.drop(j - 1);
			timer.update();

			for (int i = 0; i < j; i++) {
				screen.update();
			}

			fpsMeter.frame();
			float delta = timer.renderDeltaTime;
			screen.render(delta);
			Display.update();

			try {
				Display.sync(Constants.FPS_RENDER);
			} catch (Throwable t) {
				Log.e("Your graphics card driver does not support fullscreen properly.", t);
			}
		}
	}

// UPDATE LOOP END

	/**
	 * Replace screen
	 * 
	 * @param newScreen new screen
	 */
	public void replaceScreen(Screen newScreen) {
		screen = newScreen;
		screen.init();
	}

	/**
	 * Replace screen, don't init it
	 * 
	 * @param newScreen new screen
	 */
	public void replaceScreenNoInit(Screen newScreen) {
		screen = newScreen;
	}
}
