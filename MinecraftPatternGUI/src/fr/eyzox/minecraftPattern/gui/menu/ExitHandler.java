package fr.eyzox.minecraftPattern.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.eyzox.minecraftPattern.gui.Core;

public class ExitHandler implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!Core.getModel().isSaved() && !Core.getMenu().askForSave()) return;
		System.exit(0);
	}

}
