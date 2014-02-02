package fr.eyzox.minecraftPattern.gui.testbranch;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BlockHandler extends MouseAdapter {

	private View view;
	
	public BlockHandler(View view) {
		this.view = view;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			System.out.println((e.getPoint().x+view.getWindowStart().x)/view.getCellSize());
			if(view.getWindowStart().x > 0) {
				System.out.println("Droite");
			}else System.out.println("Gauche");
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
