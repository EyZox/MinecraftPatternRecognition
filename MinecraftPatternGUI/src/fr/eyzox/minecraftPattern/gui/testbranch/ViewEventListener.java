package fr.eyzox.minecraftPattern.gui.testbranch;

import java.awt.Point;
import java.awt.event.MouseAdapter;

public class ViewEventListener extends MouseAdapter {
	
	protected View view;
	
	public ViewEventListener(View view) {
		this.view = view;
	}
	
	public Point convert(Point p) {
		Point res = new Point((p.x+view.getWindowStart().x)/view.getCellSize(),(view.getWindowStart().y-p.y)/view.getCellSize());
		if(-view.getWindowStart().x > view.getWidth() || (view.getWindowStart().x < 0 && p.x < -view.getWindowStart().x)) {
			res.x--;
		}
		if(view.getWindowStart().y < 0 || view.getWindowStart().y < view.getHeight() && p.y > view.getWindowStart().y) {
			res.y--;
		}
		return res;
	}

}
