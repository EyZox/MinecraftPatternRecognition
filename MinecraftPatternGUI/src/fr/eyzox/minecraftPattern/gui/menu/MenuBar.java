package fr.eyzox.minecraftPattern.gui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import fr.eyzox.minecraftPattern.gui.level.LevelModel;
import fr.eyzox.minecraftPattern.gui.menu.jmenu.AboutMenu;
import fr.eyzox.minecraftPattern.gui.menu.jmenu.FileMenu;
import fr.eyzox.minecraftPattern.gui.menu.jmenu.ViewMenu;
import fr.eyzox.minecraftPattern.gui.view.View;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {
	
	private JMenu file, view, about;

	public MenuBar(View viewModel, LevelModel levelModel) {
		file = new FileMenu();
		view = new ViewMenu(viewModel, levelModel);
		about = new AboutMenu();
		
		addMenus();
	}
	
	private void addMenus() {
		add(file);
		add(view);
		add(about);
	}
}
