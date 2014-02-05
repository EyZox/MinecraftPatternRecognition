package fr.eyzox.minecraftPattern.gui.testbranch;

import java.util.Observable;

public class Level extends Observable {
	private int level = 0;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
		setChanged();
		notifyObservers();
	}
	
	public void next() {
		level++;
		setChanged();
		notifyObservers();
	}
	
	public void previous() {
		level--;
		setChanged();
		notifyObservers();
	}
}
