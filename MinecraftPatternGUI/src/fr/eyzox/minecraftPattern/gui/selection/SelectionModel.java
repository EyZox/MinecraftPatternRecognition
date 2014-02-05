package fr.eyzox.minecraftPattern.gui.selection;

import java.awt.Point;
import java.util.Observable;

public class SelectionModel extends Observable {
	private Point selection;

	public Point getSelection() {
		return selection;
	}

	public void setSelection(Point selection) {
		this.selection = selection;
		setChanged();
		notifyObservers();
	}
	
	
}
