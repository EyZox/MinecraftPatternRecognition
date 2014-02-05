package fr.eyzox.minecraftPattern.gui.testbranch;

import java.awt.event.MouseWheelEvent;

public class ZoomHandler extends ViewEventListener {

	public ZoomHandler(View view) {
		super(view);
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(view.setCellSize(view.getCellSize()+e.getWheelRotation()))view.repaint();
	}

}
