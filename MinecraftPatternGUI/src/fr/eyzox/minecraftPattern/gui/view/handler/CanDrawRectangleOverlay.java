package fr.eyzox.minecraftPattern.gui.view.handler;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import fr.eyzox.minecraftPattern.gui.view.View;

public class CanDrawRectangleOverlay extends ViewEventListener {

	protected int MASK;
	protected Rectangle rect;
	protected Point start,stop, pressed;
	
	public CanDrawRectangleOverlay(View view) {
		super(view);
		rect = new Rectangle();
		start = new Point();
		stop = new Point();
		pressed = new Point();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(MASK == MouseEvent.NOBUTTON) return;
		start.setLocation(Math.min(pressed.x, e.getPoint().x), Math.min(pressed.y, e.getPoint().y));
		stop.setLocation(Math.max(pressed.x, e.getPoint().x), Math.max(pressed.y, e.getPoint().y));
		rect.setLocation(start);
		rect.setSize(stop.x-start.x, stop.y-start.y);
		view.setSelection(rect);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON3){
			MASK = MouseEvent.BUTTON3_DOWN_MASK;
		}else if(e.getButton() == MouseEvent.BUTTON1) {
			MASK = MouseEvent.BUTTON1_DOWN_MASK;
		}else {
			MASK = MouseEvent.NOBUTTON;
			return;
		}
		pressed.setLocation(e.getPoint());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(MASK == MouseEvent.NOBUTTON) return;
		view.setSelection(null);
	}
	
	

}
