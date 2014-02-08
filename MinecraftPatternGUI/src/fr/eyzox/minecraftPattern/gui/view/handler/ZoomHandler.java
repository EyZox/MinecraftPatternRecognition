package fr.eyzox.minecraftPattern.gui.view.handler;

import java.awt.event.MouseWheelEvent;

import fr.eyzox.minecraftPattern.gui.view.View;

public class ZoomHandler extends ViewEventListener {

	public ZoomHandler(View view) {
		super(view);
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(view.setCellSize(view.getCellSize()+e.getWheelRotation()))view.repaint();
	}

}
