package fr.eyzox.minecraftPattern.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.eyzox.minecraftPattern.gui.Core;

public class NewPatternHandler implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(!Core.getModel().isSaved() && !Core.getMenu().askForSave()) return;
		Core.getModel().clear();
		Core.getModel().setFile(null);
	}

}
