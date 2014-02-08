package fr.eyzox.minecraftPattern.gui.menu.jmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import fr.eyzox.minecraftPattern.gui.customframe.AboutFrame;

@SuppressWarnings("serial")
public class AboutMenu extends JMenu {
	private JMenuItem about;
	
	public AboutMenu() {
		super("About");
		about = new JMenuItem("About MinecraftPattern Gui ...");
		
		addMenuItems();
		addListeners();
	}

	private void addMenuItems() {
		add(about);
	}
	
	private void addListeners() {
		about.addActionListener(new AboutHandler());
		
	}
	
	private class AboutHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			new AboutFrame();
		}
	}
}
