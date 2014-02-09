package fr.eyzox.minecraftPattern.gui.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;

import fr.eyzox.minecraftPattern.gui.BlockEditionModels;
import fr.eyzox.minecraftPattern.gui.bdd.Block;
import fr.eyzox.minecraftPattern.gui.bdd.BlockBDD;
import fr.eyzox.minecraftPattern.gui.config.BlockInfos;
import fr.eyzox.minecraftPattern.gui.level.LevelModel;
import fr.eyzox.minecraftPattern.gui.selection.SelectionModel;
import fr.eyzox.minecraftPattern.gui.view.handler.SelectionHandler;
import fr.eyzox.minecraftPattern.gui.view.handler.BlockHandler;
import fr.eyzox.minecraftPattern.gui.view.handler.MoveHandler;
import fr.eyzox.minecraftPattern.gui.view.handler.ZoomHandler;

@SuppressWarnings("serial")
public class View extends JComponent implements Observer{
	private boolean added = false;

	private int cellSize;
	private Point wStart;
	
	private BlockBDD bdd;
	private LevelModel levelModel;
	private SelectionModel selectionModel;
	
	private Rectangle selection;

	private Color GRID_COLOR = Color.BLACK;
	private Color AXES_COLOR = Color.BLUE;
	private Color SELECTION_COLOR;
	private boolean SHOW_GRID = true;
	private boolean SHOW_AXES = true;

	public View(BlockEditionModels models) {
		this.bdd = models.getBdd();
		this.levelModel = models.getLevelModel();
		this.selectionModel = models.getSelectionModel();
		cellSize = 35;
		wStart = new Point();
		MoveHandler mh = new MoveHandler(this);
		addMouseListener(mh); addMouseMotionListener(mh);
		addMouseWheelListener(new ZoomHandler(this));
		
		SelectionHandler sh = new SelectionHandler(this, models);
		addMouseListener(sh); addMouseMotionListener(sh);
		
		BlockHandler bh = new BlockHandler(this, models);
		addMouseListener(bh); addMouseMotionListener(bh);
		
		setSELECTION_COLOR(Color.BLUE);
		
		bdd.addObserver(this);
		selectionModel.addObserver(this);
		levelModel.addObserver(this);
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
		printBlocks(g2d);
		if(SHOW_GRID) printGrid(g2d);
		if(SHOW_AXES) printAxes(g2d);
		printSelection(g2d);
		printPreSelection(g2d);

	}

	private void printPreSelection(Graphics2D g2d) {
		if(selection != null) {
			g2d.fill(selection);
		}
		
	}

	private void printSelection(Graphics2D g2d) {
		g2d.setColor(SELECTION_COLOR);
		for(Point selectedBlock : selectionModel.getSelection()) {
			g2d.fillRect(-wStart.x+(selectedBlock.x*cellSize), wStart.y-((selectedBlock.y+1)*cellSize), cellSize, cellSize);
		}
	}

	private void printBlocks(Graphics2D g2d) {
		int xStart = wStart.x/cellSize - (wStart.x>0?0:1);
		int zStart = wStart.y/cellSize - (wStart.y>0?0:1);

		int nbLin = getHeight()/cellSize+1;
		int nbCol = getWidth()/cellSize+1;
		
		Map<Integer,Map<Integer,Block>> level = bdd.getZXMap(levelModel.getLevel());
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
			if(size > getWidth()) size = getWidth() + (size-getWidth())%cellSize;
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
			if(size > getHeight()) size = getHeight() + (size-getHeight())%cellSize;
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

	public Rectangle getSelection() {
		return selection;
	}

	public void setSelection(Rectangle selection) {
		this.selection = selection;
		repaint();
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

	public Color getSELECTION_COLOR() {
		return SELECTION_COLOR;
	}

	public void setSELECTION_COLOR(Color c) {
		SELECTION_COLOR = new Color(c.getRed(), c.getGreen(), c.getBlue(), 70);
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
	public void update(Observable obs, Object arg1) {
		repaint();
	}

}
