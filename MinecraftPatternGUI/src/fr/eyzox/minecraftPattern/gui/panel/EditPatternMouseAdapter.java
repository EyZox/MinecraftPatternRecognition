package fr.eyzox.minecraftPattern.gui.panel;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import fr.eyzox.minecraftPattern.gui.Core;
import fr.eyzox.minecraftPattern.gui.MCPatternModel;
import fr.eyzox.minecraftPattern.pattern.MCPatternBlock2D;

public class EditPatternMouseAdapter extends MouseAdapter {
	
	private MCPattern2DPanel view;
	private Point depart = new Point();
	private int MASK;
	public EditPatternMouseAdapter(MCPattern2DPanel view) {
		this.view = view;
	}
	
	@Override
	public void mousePressed(MouseEvent event) {
		if(event.getButton() == MouseEvent.BUTTON1) {
			addBlock(event.getPoint());
			
			setDepart(event.getPoint());
			MASK = MouseEvent.BUTTON1_DOWN_MASK;
		
		}else if(event.getButton() == MouseEvent.BUTTON3) {
			removeBlock(event.getPoint());
			
			setDepart(event.getPoint());
			MASK = MouseEvent.BUTTON3_DOWN_MASK;
		}
	}
	
	private void setDepart(Point p) {
		depart.x = p.x - (p.x % view.getLayout().getTcase());
		depart.y = p.y - (p.y % view.getLayout().getTcase());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if((e.getModifiersEx() & MASK) == MASK && isOnAnOtherCase(e.getPoint())){
			if(MASK == MouseEvent.BUTTON1_DOWN_MASK) {
				addBlock(e.getPoint());
			}else if(MASK == MouseEvent.BUTTON3_DOWN_MASK) {
				removeBlock(e.getPoint());
			}
			setDepart(e.getPoint());
		}
	}
	
	private void addBlock(Point c) {
		Point p = view.getCoord(c);
		if(Core.getToolbox().getSelectedBlock() != -1) {
			Core.getModel().add(view.getLevel(), new MCPatternBlock2D(Core.getToolbox().getSelectedBlock(),Core.getOptionPanel().getBlockInfoPanel().getValue(), p.x, p.y));
		}else {
			Map<MCPatternModel.coord, MCPatternBlock2D> level = Core.getModel().getPattern().get(view.getLevel());
			if(level != null)
				Core.getOptionPanel().getBlockInfoPanel().update(level.get(Core.getModel().getCoord(p.x,p.y)), false);
		}
	}
	
	private void removeBlock(Point c) {
		Point p = view.getCoord(c);
		Core.getModel().remove(p.x,view.getLevel(), p.y);
	}
	
	private boolean isOnAnOtherCase(Point p) {
		return p.x < depart.x || p.y < depart.y || Math.abs(depart.x-p.x) > view.getLayout().getTcase() || Math.abs(depart.y-p.y) > view.getLayout().getTcase();
	}
	
	
}
