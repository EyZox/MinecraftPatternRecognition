package fr.eyzox.minecraftPattern.gui.panel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import fr.eyzox.minecraftPattern.gui.Core;
import fr.eyzox.minecraftPattern.gui.MCPatternModel.coord;
import fr.eyzox.minecraftPattern.pattern.MCPatternBlock2D;

@SuppressWarnings("serial")
public class MCPattern2DPanel extends JPanel implements Observer{
	
	private class DragScrollListener extends MouseAdapter {
		private final Cursor defCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
        private final Cursor hndCursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
		Point depart = new Point();
		
		@Override public void mousePressed(MouseEvent e) {
			if(isButton2Pressed(e)) {
            ((JComponent) e.getSource()).setCursor(hndCursor);
            depart.setLocation(e.getPoint());
			}
        }
        @Override public void mouseReleased(MouseEvent e) {
            ((JComponent)e.getSource()).setCursor(defCursor);
        }
		
		@Override
		public void mouseDragged(MouseEvent event) {
			if(isButton2Pressed(event)) {
				layout.getOrigin().translate(event.getPoint().x - depart.x,event.getPoint().y - depart.y);
				depart.setLocation(event.getPoint());
				repaint();
			}
		}
		
		public boolean isButton2Pressed(MouseEvent e) {
			return (e.getModifiersEx() & MouseEvent.BUTTON2_DOWN_MASK) == MouseEvent.BUTTON2_DOWN_MASK;
		}
	}

	private OrientedGridLayout layout;

	private int level = 0;
	private boolean SHOW_GRID;
	private boolean SHOW_AXES;
	private Color GRID_COLOR = Color.BLACK;
	private Color AXES_COLOR = Color.BLUE;

	public MCPattern2DPanel() {
		this(50,true,true);
	}

	public MCPattern2DPanel(int tcase) {
		this(tcase,true,true);
	}

	public MCPattern2DPanel(int tcase, boolean showGrid, boolean showAxes) {
		this.SHOW_AXES = showAxes;
		this.SHOW_GRID = showGrid;
		Core.getModel().addObserver(this);
		layout = new OrientedGridLayout(tcase);
		setLayout(layout);
		setPreferredSize(new Dimension(400,400));
		this.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				int newTCase = layout.getTcase()+e.getWheelRotation();
				if(newTCase > 0) {
					layout.setTcase(newTCase);
					repaint();
				}
			}
		});
		
		EditPatternMouseAdapter ep = new EditPatternMouseAdapter(this);
		this.addMouseListener(ep);
		this.addMouseMotionListener(ep);
		DragScrollListener scrollListener = new DragScrollListener();
		addMouseMotionListener(scrollListener);
		addMouseListener(scrollListener);

	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		layout.layoutContainer(this);
		int x = layout.getOrigin().x;
		int y = layout.getOrigin().y;
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(getBackground());
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		if(SHOW_GRID) {
			g2d.setColor(GRID_COLOR);
			int depart = layout.getOrigin().y % layout.getTcase();
			while(depart < getHeight()) {
				g2d.drawLine(0, depart, getWidth(), depart);
				depart += layout.getTcase();
			}
			
			depart = layout.getOrigin().x % layout.getTcase();
			while(depart < getWidth()) {
				g2d.drawLine(depart, 0, depart, getHeight());
				depart += layout.getTcase();
			}
		}
		if(SHOW_AXES) {
			g2d.setColor(AXES_COLOR);
			g2d.drawLine(x, 0, x, getHeight());
			g2d.drawLine(0, y, getWidth(), y);
			if(level == 0) {
				g2d.setColor(new Color(AXES_COLOR.getRed(), AXES_COLOR.getGreen(), AXES_COLOR.getBlue(), 20));
				g2d.fillRect(x, y, layout.getTcase(), -layout.getTcase());
			}
		}

	}

	@Override
	public void update(Observable arg0, Object obj) {
		updateScreen();
	}
	
	private void updateScreen() {
		removeAll();
		Map<coord, MCPatternBlock2D> map = Core.getModel().getPattern().get(level);
		if(map != null) {
			for(coord c : map.keySet()) {
				add(new MCPatternBlock2DUI(map.get(c)));
			}
		}
		repaint();
		
	}
	
	/*public void move(int x, int y) {
		layout.getOrigin().translate(x, y);
		repaint();
	}*/
	
	public boolean setZoom(int size) {
		boolean b = layout.setTcase(size);
		if(b)repaint();
		return b;
	}
	
	public int getZoom() {
		return layout.getTcase();
	}

	public int getLevel() {
		return level;
	}

	public boolean setLevel(int level) {
		if(level <= -256 || level >= 256 ) return false;
		this.level = level;
		updateScreen();
		return true;
	}

	public boolean isSHOW_GRID() {
		return SHOW_GRID;
	}

	public void setSHOW_GRID(boolean sHOW_GRID) {
		SHOW_GRID = sHOW_GRID;
		repaint();
	}

	public boolean isSHOW_AXES() {
		return SHOW_AXES;
	}

	public void setSHOW_AXES(boolean sHOW_AXES) {
		SHOW_AXES = sHOW_AXES;
		repaint();
	}

	public Color getGRID_COLOR() {
		return GRID_COLOR;
	}

	public void setGRID_COLOR(Color gRID_COLOR) {
		GRID_COLOR = gRID_COLOR;
		repaint();
	}

	public Color getAXES_COLOR() {
		return AXES_COLOR;
	}

	public void setAXES_COLOR(Color aXES_COLOR) {
		AXES_COLOR = aXES_COLOR;
		repaint();
	}
	
	public Point getCoord(Point p) {
		int coordX, coordY;
		coordX = Math.abs(layout.getOrigin().x - p.x)/layout.getTcase();
		if(p.getX() < layout.getOrigin().x) {
			coordX = -coordX -1;
		}
		
		
		coordY = Math.abs(layout.getOrigin().y - p.y)/layout.getTcase();
		if(p.getY() > layout.getOrigin().y) {
			coordY = -coordY-1;
		}
		
		return new Point(coordX,coordY);
	}
	
	public OrientedGridLayout getLayout() {
		return layout;
	}
	
}
