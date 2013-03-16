package org.hacksore.jarm8;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Gui {
	public JarM8 j;
	public JPanel panel;
	public JList jarlist;
	public Gui(JarM8 m){
		this.j = m;		
		init();
	}
	
	
	public void init(){
		j.getContentPane().setLayout(new GridLayout());
		panel = new JPanel();
		j.getContentPane().add(panel);
		
		Map<String, String> data = j.conf.jars;
		jarlist = new JList(getMapKeys(data));    
       
		JScrollPane jarScroll = new JScrollPane(jarlist, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jarScroll.setPreferredSize(new Dimension(200,100));

		panel.add(jarScroll);
	
       
		JButton start = new JButton("Launch");
		start.setPreferredSize(new Dimension(300, 100));
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				j.launchGame("");
			}
		});
		
		JButton mc = new JButton("Locate Minecraft");
		mc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
    		   
			}
		});
		
		JButton add = new JButton("Add Jar");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Object[] o = displayFileChooser();
				if(o[1] == null){
					return;
				}
			    String name = JOptionPane.showInputDialog(panel, "Enter jar name:");
			    
			    j.conf.jars.put(name, (String) o[1]);
			    j.conf.saveConfig();
			    refreshJars();
			    
			}
		});
		
		JButton rm = new JButton("Remove Jar");
		rm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println(jarlist.getSelectedIndex() + "");			
			
				j.conf.jars.remove(jarlist.getSelectedValue());
				j.conf.saveConfig();
				refreshJars();
			}
		});
       
		panel.add(start);
		panel.add(mc);
		panel.add(add);
		panel.add(rm);
	}
		

	public void refreshJars(){
		jarlist.setListData(getMapKeys(j.conf.jars));
	}


	private String[] getMapKeys(Map<String, String> jars) {

		String[] keys = new String[jars.size()];
		Object[] values = new Object[jars.size()];
		int index = 0;
		for (Entry<String, String> mapEntry : jars.entrySet()) {
		    keys[index] = mapEntry.getKey();
		    values[index] = mapEntry.getValue();
		    index++;
		}
		return keys;
	}


	public Object[] displayFileChooser() {
		Object[] s = new Object[2];
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getenv("APPDATA") + File.separator + ".jarm8" + File.separator + "jars"));
		int returnVal = fc.showDialog(this.panel, "Attach");	
		s[0] = returnVal;
		if(fc.getSelectedFile() != null){
			s[1] = fc.getSelectedFile().getAbsolutePath();
		}else{
			s[1] = "";
		}
		return s;
		
	}


	public void alert(String string) {
		JOptionPane.showMessageDialog(this.panel, string);
		
	}

	public String activeJar() {
		return (String) jarlist.getSelectedValue();
	}

}
