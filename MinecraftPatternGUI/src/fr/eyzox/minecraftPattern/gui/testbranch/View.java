package fr.eyzox.minecraftPattern.gui.testbranch;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;

import fr.eyzox.minecraftPattern.gui.BlockInfos;
import fr.eyzox.minecraftPattern.gui.MCPatternModel;
import fr.eyzox.minecraftPattern.gui.action.ActionModel;
import fr.eyzox.minecraftPattern.gui.selection.SelectionModel;

@SuppressWarnings("serial")
public class View extends JComponent implements Observer{
	private boolean added = false;

	private int cellSize;
	private Point wStart;
	
	private SelectionModel selectionModel;
	private ActionModel actionModel;

	private Color GRID_COLOR = Color.BLACK;
	private Color AXES_COLOR = Color.BLUE;
	private boolean SHOW_GRID;
	private boolean SHOW_AXES;

	private MCPatternModel model;

	public View(MCPatternModel model) {
		this.model = model;
		selectionModel = new SelectionModel();
		actionModel = new ActionModel();
		cellSize = 35;
		wStart = new Point();
		MoveHandler mh = new MoveHandler(this);
		addMouseListener(mh); addMouseMotionListener(mh);
		addMouseListener(new BlockHandler(this));
		addMouseWheelListener(new ZoomHandler(this));
		
		model.getPattern().addObserver(this);
		selectionModel.addObserver(this);
		model.getLevel().addObserver(this);
		
	}

	public void resetPosition() {
		wStart.setLocation(-getWidth()/2, getHeight()/2);

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
		int xStart = wStart.x/cellSize - (wStart.x>0?0:1);
		int zStart = wStart.y/cellSize - (wStart.y>0?0:1);

		int nbLin = getHeight()/cellSize+1;
		int nbCol = getWidth()/cellSize+1;
		
		Map<Integer,Map<Integer,Block>> level = model.getPattern().getZXMap(model.getLevel().getLevel());
		if(level == null) return;
		for(int line = 0; line<nbLin; line++) {
			Map<Integer,Block> blockLine = level.get(zStart-line);
			if(blockLine != null) {
				for(int col = 0; col<nbCol; col++) {
					Block block = blockLine.get(xStart+col);
					if(block != null) {
						g2d.drawImage(
							BlockInfos.getImage(block.getId()),
							//pos X
							-wStart.x + (xStart+col)*cellSize,
							//pos Z
							wStart.y - (zStart-line+1)*cellSize,
							cellSize, cellSize, null);
					}
				}
			}
		}

	}

	private void printAxes(Graphics2D g2d) {
		g2d.setColor(AXES_COLOR);
		if(-wStart.x >= 0 && -wStart.x < getWidth())
			g2d.drawLine(-wStart.x, 0, -wStart.x, getHeight());
		if(wStart.y >= 0 && wStart.y < getHeight())
			g2d.drawLine(0, wStart.y, getWidth(), wStart.y);
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
		size = wStart.y;
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
		size = getHeight() - (wStart.y<0?wStart.y%cellSize:wStart.y);
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

	public boolean setCellSize(int cellSize) {
		if(cellSize<=0) return false;
		this.cellSize = cellSize;
		return true;
	}
	
	public MCPatternModel getModel() {
		return model;
	}
	
	public SelectionModel getSelectionModel() {
		return selectionModel;
	}

	public ActionModel getActionModel() {
		return actionModel;
	}

	public void setModel(MCPatternModel model) {
		this.model = model;
	}

	public Color getGRID_COLOR() {
		return GRID_COLOR;
	}

	public void setGRID_COLOR(Color gRID_COLOR) {
		GRID_COLOR = gRID_COLOR;
	}

	public Color getAXES_COLOR() {
		return AXES_COLOR;
	}

	public void setAXES_COLOR(Color aXES_COLOR) {
		AXES_COLOR = aXES_COLOR;
	}

	public boolean isSHOW_GRID() {
		return SHOW_GRID;
	}

	public void setSHOW_GRID(boolean sHOW_GRID) {
		SHOW_GRID = sHOW_GRID;
	}

	public boolean isSHOW_AXES() {
		return SHOW_AXES;
	}

	public void setSHOW_AXES(boolean sHOW_AXES) {
		SHOW_AXES = sHOW_AXES;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
		
	}

}
