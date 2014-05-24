package net.spritegen;

import java.io.File;

import net.spritegen.util.Utils;

import com.porcupine.coord.Coord;
import com.porcupine.util.PropertyManager;

public class SetupLoader {

	public PropertyManager cfg;
	
	public int img_size;

	public int samples;
	public double render_wobble_y;
	public double render_wobble_z;
	public double render_rotate_x;
	public double renmder_move_y;
	public double render_move_x;
	public double render_scale;
	
	public Coord light_pos;
	public double light_attr;
	public double light_diffuse;
	public double light_specular;
	public double light_ambient;

	public double mat_diffuse;
	public double mat_specular;
	public double mat_ambient;
	
	public SetupLoader(File dir) {
		dir.mkdirs();
		File file = new File(dir,"config.ini");
		
		cfg = new PropertyManager(file.getAbsolutePath(), "Spritesheet setup file");
		
		cfg.putInteger("output.size", 512, "Output image size");
		cfg.putDouble("render.walk.wobble_x_angle_max", 6, "Maximal wobble angle for walking (rotate around Y)");
		cfg.putDouble("render.walk.wobble_z_angle_max", 0, "Maximal wobble angle for walking (rotate around Z)");
		cfg.putDouble("render.rotate_x", 60, "Rotate around X axis to get 2D or 2.5D perspective");
		cfg.putDouble("render.move_y", 0, "Y offset (for centering)");
		cfg.putDouble("render.move_x", 0, "X move (for centering)");
		cfg.putDouble("render.scale", 1, "Render scale");
		cfg.putInteger("render.multisampling", 4, "Multisampling (smoothing) level, values: 0,1,2,4,8");
		
		cfg.putDouble("render.material.ambient", 1, "Material property: ambient");
		cfg.putDouble("render.material.specular", 1, "Material property: specular");
		cfg.putDouble("render.material.diffuse", 1, "Material property: diffuse");
		
		cfg.putDouble("render.light.ambient", 1, "Light property: ambient");
		cfg.putDouble("render.light.specular", 1, "Light property: specular");
		cfg.putDouble("render.light.diffuse", 1, "Light property: diffuse");
		cfg.putDouble("render.light.attribute", 1, "Light property: attribute");
		
		cfg.putDouble("render.light.x", 0, "Light pos: X");
		cfg.putDouble("render.light.y", 3, "Light pos: Y");
		cfg.putDouble("render.light.z", 3, "Light pos: Z");
		
		cfg.apply();
		
		
		img_size = cfg.getInt("output.size");

		render_wobble_y = cfg.getDouble("render.walk.wobble_x_angle_max");
		render_wobble_z = cfg.getDouble("render.walk.wobble_z_angle_max");
		render_rotate_x = cfg.getDouble("render.rotate_x");
		renmder_move_y = cfg.getDouble("render.move_y");
		render_move_x = cfg.getDouble("render.move_x");
		render_scale = cfg.getDouble("render.scale");
		samples = cfg.getInt("render.multisampling");
		
		mat_ambient = cfg.getDouble("render.material.ambient");
		mat_specular = cfg.getDouble("render.material.specular");
		mat_diffuse = cfg.getDouble("render.material.diffuse");
		
		light_ambient = cfg.getDouble("render.light.ambient");
		light_specular = cfg.getDouble("render.light.specular");
		light_diffuse = cfg.getDouble("render.light.diffuse");
		light_attr = cfg.getDouble("render.light.attribute");
		
		light_pos = new Coord(cfg.getDouble("render.light.x"),
		cfg.getDouble("render.light.y"),
		cfg.getDouble("render.light.z"));
		
	}

}
