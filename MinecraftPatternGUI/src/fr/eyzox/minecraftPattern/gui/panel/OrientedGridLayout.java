package fr.eyzox.minecraftPattern.gui.panel;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;

public class OrientedGridLayout implements LayoutManager {

	private int tcase = 50;
	private Point origin;
	
	public OrientedGridLayout() {
		this(50);
	}
	
	public OrientedGridLayout(int tcase) {
		if(tcase <= 0) tcase = 1;
		this.tcase = tcase;
	}
	
	@Override
	public void addLayoutComponent(String arg0, Component arg1) {
		// NOTHING
		
	}

	@Override
	public void layoutContainer(Container parent) {
		if(origin == null) {
			origin = new Point(parent.getWidth()/2, parent.getHeight()/2);
		}
		for(int i = 0; i<parent.getComponentCount();i++) {
			try {
			OrientedGridItem item = (OrientedGridItem) parent.getComponent(i);
			parent.getComponent(i).setBounds(origin.x + item.getCoordX() * tcase, origin.y - (item.getCoordY()+1) * tcase, tcase, tcase);
			}catch(ClassCastException e) {
				parent.remove(i);
			}
		}
		
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(0,0);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return parent.getSize();
	}

	@Override
	public void removeLayoutComponent(Component arg0) {
		// NOTHING
		
	}
	
	public Point getOrigin() { return origin;}

	public int getTcase() {
		return tcase;
	}

	public boolean setTcase(int tcase) {
		if(tcase <= 0) return false;
		this.tcase = tcase;
		return true;
	}

}
