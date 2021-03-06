package fr.eyzox.minecraftPattern.gui.menu.jmenu.file;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import fr.eyzox.minecraftPattern.gui.PatternModel;
import fr.eyzox.minecraftPattern.gui.customframe.PreferencesFrame;

@SuppressWarnings("serial")
public class FileMenu extends JMenu {
	
	private PatternModel patternModel;
	private JMenuItem open, saveAs, save, exit, newPattern, option;
	
	public FileMenu(PatternModel patternModel) {
		super("File");
		open = new JMenuItem("Open ...");
		saveAs = new JMenuItem("Save as ...");
		save = new JMenuItem("Save");
		exit = new JMenuItem("Exit");
		newPattern = new JMenuItem("New");
		option = new JMenuItem("Preference");
		
		this.patternModel = patternModel;
		
		addMenuItems();
		addListeners();
		addAccelerators();
	}
	
	private void addMenuItems() {
		add(newPattern);
		add(open);
		add(saveAs);
		add(save);
		addSeparator();
		add(option);
		addSeparator();
		add(exit);
	}
	
	private void addListeners() {
		open.addActionListener(new OpenHandler());
		saveAs.addActionListener(new SaveAsHandler());
		save.addActionListener(new SaveHandler());
		exit.addActionListener(new ExitHandler());
		newPattern.addActionListener(new NewPatternHandler());
		option.addActionListener(new PreferencesHandler());
	}
	
	private void addAccelerators() {
		newPattern.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK+ActionEvent.SHIFT_MASK));
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
	}
	
	private class OpenHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(patternModel.close()) patternModel.open();
		}
	}
	
	private class SaveAsHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			patternModel.save(true);
		}
	}
	
	private class SaveHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			patternModel.save(false);
		}
	}
	
	private class ExitHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(patternModel.close()) System.exit(0);
		}
	}
	
	private class NewPatternHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(patternModel.close()) patternModel.clear();
		}
	}
	
	private class PreferencesHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(patternModel.close()) new PreferencesFrame();
		}
	}
}
