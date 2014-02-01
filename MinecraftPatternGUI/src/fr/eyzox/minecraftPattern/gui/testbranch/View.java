package fr.eyzox.minecraftPattern.gui.testbranch;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JComponent;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class View extends JComponent {
	
	/*private int tcase;
	private Point wStart;
	private Point wStop;
	
	private Color GRID_COLOR = Color.BLACK;
	private Color AXES_COLOR = Color.BLUE;
	
	public View() {
		tcase = 25;
		wStart = new Point();
		wStop = new Point();
		
		addComponentListener(new ComponentAdapter() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				resetPosition();
				
			}
		});
	}
	
	private void resetPosition() {
		wStop.setLocation(getWidth()/2,getHeight()/2);
		wStart.setLocation(-wStop.x, -wStop.y);
		
	}

	public void createFrame() {
		JFrame f = new JFrame();
		f.getContentPane().add(this);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		printGrid(g2d);
		printAxes(g2d);
		printBlocks(g2d);
		
	}

	private void printBlocks(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

	private void printAxes(Graphics2D g2d) {
		g2d.setColor(AXES_COLOR);
		g2d.drawLine(origin.x, 0, origin.x, getHeight());
		g2d.drawLine(0, origin.y, getWidth(), origin.y);
		
	}

	private void printGrid(Graphics2D g2d) {
		g2d.setColor(GRID_COLOR);
		int depart = origin.y % tcase;
		while(depart < getHeight()) {
			g2d.drawLine(0, depart, getWidth(), depart);
			depart += tcase;
		}
		
		depart = origin.x % tcase;
		while(depart < getWidth()) {
			g2d.drawLine(depart, 0, depart, getHeight());
			depart += tcase;
		}
		
	}*/
	
	
}
