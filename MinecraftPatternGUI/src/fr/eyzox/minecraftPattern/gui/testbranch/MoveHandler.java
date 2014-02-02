package fr.eyzox.minecraftPattern.gui.testbranch;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MoveHandler extends MouseAdapter {
	private final Cursor defCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    private final Cursor hndCursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
    
	private View view;
	private Point start = new Point();
	
	public MoveHandler(View view) {
		this.view = view;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if((e.getModifiersEx() & MouseEvent.BUTTON2_DOWN_MASK) == MouseEvent.BUTTON2_DOWN_MASK) {
			view.getWindowStart().translate(start.x-e.getPoint().x, start.y-e.getPoint().y);
			start.setLocation(e.getPoint());
			view.repaint();
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if((e.getModifiersEx() & MouseEvent.BUTTON2_DOWN_MASK) == MouseEvent.BUTTON2_DOWN_MASK) {
			start.setLocation(e.getPoint());
			view.setCursor(hndCursor);
		}

	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		view.setCursor(defCursor);
	}

}
