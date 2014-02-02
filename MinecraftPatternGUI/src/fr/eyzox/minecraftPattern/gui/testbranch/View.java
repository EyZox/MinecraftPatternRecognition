package fr.eyzox.minecraftPattern.gui.testbranch;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;

import fr.eyzox.minecraftPattern.gui.Core;
import fr.eyzox.minecraftPattern.gui.MCPatternModel;
import fr.eyzox.minecraftPattern.pattern.MCPatternBlock2D;

@SuppressWarnings("serial")
public class View extends JComponent {
	private boolean added = false;

	private int cellSize;
	private Point wStart;

	private Color GRID_COLOR = Color.BLACK;
	private Color AXES_COLOR = Color.BLUE;

	public View() {
		cellSize = 25;
		wStart = new Point();
		MoveHandler mh = new MoveHandler(this);
		addMouseListener(mh); addMouseMotionListener(mh);
		//addMouseListener(new BlockHandler(this));
	}

	public void resetPosition() {
		wStart.setLocation(-getWidth()/2, -getHeight()/2);

	}

	public void createFrame() {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(this);
		this.setPreferredSize(new Dimension(400,300));
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
		//printBlocks(g2d);

	}

	private void printBlocks(Graphics2D g2d) {
		Map<MCPatternModel.coord, MCPatternBlock2D> m = Core.getModel().getPattern().get(Core.getOptionPanel().getSelectLevel().getLevel());
		if(m == null) return;

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

		int size,nbCase,offset;
		//draw vertical lines at left
		size = -wStart.x;
		if(size > 0) {
			if(size > getWidth()) size = getWidth() + size%cellSize;
			nbCase = size/cellSize;
			offset = size - nbCase*cellSize;
			for(int i = 0; i<nbCase; i++) {
				int pos = offset+i*cellSize;
				g2d.drawLine(pos, 0, pos, getHeight());
			}
		}

		//draw vertical line at right
		size = getWidth() + (wStart.x>0?wStart.x%cellSize:wStart.x);
		if(size > 0) {
			nbCase = size/cellSize;
			offset = size - nbCase*cellSize;
			for(int i = 0; i<nbCase; i++) {
				int pos = getWidth()-(offset+i*cellSize);
				g2d.drawLine(pos, 0, pos, getHeight());
			}
		}
		//draw horizontal line at top
		size = -wStart.y;
		if(size > 0) {
			if(size > getHeight()) size = getHeight() + size%cellSize;
			nbCase = size/cellSize;
			offset = size - nbCase*cellSize;

			for(int i = 0; i<nbCase; i++) {
				int pos = offset+i*cellSize;
				g2d.drawLine(0, pos, getWidth(), pos);
			}
		}
		//draw horizontal line at bottom
		size = getHeight()+ (wStart.y>0?wStart.y%cellSize:wStart.y);
		if(size > 0) {
			nbCase = size/cellSize;
			offset = size - nbCase*cellSize;

			for(int i = 0; i<nbCase; i++) {
				int pos = getHeight()-(offset+i*cellSize);
				g2d.drawLine(0, pos, getWidth(), pos);
			}
		}

	}

	public boolean isVisible(Point p) {
		return p.x >= wStart.x && p.x < getWidth() && p.y >= wStart.y && p.y < getHeight();
	}


	public Point getWindowStart() {
		return wStart;
	}

	public void setWindowStart(Point wStart) {
		this.wStart = wStart;
	}

	public int getCellSize() {
		return cellSize;
	}

	public void setCellSize(int cellSize) {
		if(cellSize<=0) return;
		this.cellSize = cellSize;
	}

	public static void main(String[] args) {
		new View().createFrame();
	}
}
