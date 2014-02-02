package fr.eyzox.minecraftPattern.gui.testbranch;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class View extends JComponent {
	
	private boolean added = false;
	
	private int tcase;
	private Point wStart;
	
	private Color GRID_COLOR = Color.BLACK;
	private Color AXES_COLOR = Color.BLUE;
	
	public View() {
		tcase = 25;
		wStart = new Point();
	}
	
	public void resetPosition() {
		wStart.setLocation(-getWidth()/2, -getHeight()/2);
		
	}

	public void createFrame() {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(this);
		f.setPreferredSize(new Dimension(400,300));
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(!added) {
			resetPosition();
			added = true;
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		printGrid(g2d);
		printAxes(g2d);
		printBlocks(g2d);
		
	}

	private void printBlocks(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

	private void printAxes(Graphics2D g2d) {
		g2d.setColor(AXES_COLOR);
		if(0 >= wStart.x && 0 < getWidth())
			g2d.drawLine(-wStart.x, 0, -wStart.x, getHeight());
		if(0 >= wStart.y && 0 < getHeight())
			g2d.drawLine(0, -wStart.y, getWidth(), -wStart.y);
		
	}

	private void printGrid(Graphics2D g2d) {
		g2d.setColor(GRID_COLOR);
		
		//draw vertical lines at left
		int size = Math.abs(wStart.x);
		int nbCase = size/tcase;
		int offset = size - nbCase*tcase;
		
		for(int i = 0; i<nbCase; i++) {
			int pos = offset+i*tcase;
			g2d.drawLine(pos, 0, pos, getHeight());
		}
		
		//draw vertical line at right
		size = getWidth()-size;
		nbCase = size/tcase;
		offset = size - nbCase*tcase;
		
		for(int i = 0; i<nbCase; i++) {
			int pos = getWidth()-(offset+i*tcase);
			g2d.drawLine(pos, 0, pos, getHeight());
		}
		
		//draw horizontal line at top
		size = Math.abs(wStart.y);
		nbCase = size/tcase;
		offset = size - nbCase*tcase;
		
		for(int i = 0; i<nbCase; i++) {
			int pos = offset+i*tcase;
			g2d.drawLine(0, pos, getWidth(), pos);
		}
		
		//draw horizontal line at bottom
		size = getHeight()-size;
		nbCase = size/tcase;
		offset = size - nbCase*tcase;
		
		for(int i = 0; i<nbCase; i++) {
			int pos = getHeight()-(offset+i*tcase);
			g2d.drawLine(0, pos, getWidth(), pos);
		}
		
	}
	
	public boolean isVisible(Point p) {
		return p.x >= wStart.x && p.x < getWidth() && p.y >= wStart.y && p.y < getHeight();
	}
	
	public static void main(String[] args) {
		new View().createFrame();
	}
}
