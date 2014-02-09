package fr.eyzox.minecraftPattern.gui.selection;

import java.awt.Point;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

public class SelectionModel extends Observable {
	private Set<Point> selection;
	
	public SelectionModel() {
		selection = new HashSet<Point>();
	}

	public Set<Point> getSelection() {
		return selection;
	}

	public boolean add(Point p) {
		return selection.add(p);
	}
	
	public boolean remove(Point p) {
		return selection.remove(p);
	}
	
	public void clear() {
		selection.clear();
	}
	
	public void fireUpdate() {
		setChanged();
		notifyObservers();
	}
}
