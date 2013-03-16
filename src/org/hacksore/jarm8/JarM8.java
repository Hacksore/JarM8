package org.hacksore.jarm8;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;


public class JarM8 extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Config conf;
	public Gui gui;

	public JarM8(){
		setTitle("JarM8");
		setSize(580, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		
		conf = new Config(this);
		gui = new Gui(this);
		
		
		if(conf.minecraft.equals("")){
			gui.alert("Please select your minecraft.exe");
			Object[] o = gui.displayFileChooser();
			String path = (String) o[1];
			
			conf.minecraft = path;
			conf.saveConfig();
			
		}
	}
	
	public void launchGame(String args){

		try {
			Util.copyFile(new File(conf.jars.get(gui.activeJar())), new File(conf.mcjar));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Runtime rt = Runtime.getRuntime();
		try {
			rt.exec(conf.minecraft);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
		
	public static void main(String[] args){
		JarM8 ex = new JarM8();
        ex.setVisible(true);
        ex.setResizable(false);
	}
}
