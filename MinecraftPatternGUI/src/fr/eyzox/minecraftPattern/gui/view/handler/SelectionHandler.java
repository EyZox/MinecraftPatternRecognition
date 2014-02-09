package fr.eyzox.minecraftPattern.gui.view.handler;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Map;

import fr.eyzox.minecraftPattern.gui.BlockEditionModels;
import fr.eyzox.minecraftPattern.gui.action.Action;
import fr.eyzox.minecraftPattern.gui.bdd.Block;
import fr.eyzox.minecraftPattern.gui.view.View;

public class SelectionHandler extends ViewEventListener {

	protected BlockEditionModels models;

	private int MASK;
	private Rectangle rect;
	private Point start,stop, pressed;

	public SelectionHandler(View view, BlockEditionModels models) {
		super(view);
		this.models = models;
		rect = new Rectangle();
		start = new Point();
		stop = new Point();
		pressed = new Point();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(models.getActionModel().getAction() != Action.SELECT || MASK == MouseEvent.NOBUTTON) return;
		start.setLocation(Math.min(pressed.x, e.getPoint().x), Math.min(pressed.y, e.getPoint().y));
		stop.setLocation(Math.max(pressed.x, e.getPoint().x), Math.max(pressed.y, e.getPoint().y));
		rect.setLocation(start);
		rect.setSize(stop.x-start.x, stop.y-start.y);
		view.setSelection(rect);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(models.getActionModel().getAction() != Action.SELECT) return;
		pressed.setLocation(e.getPoint());
		if(e.getButton() == MouseEvent.BUTTON3){
			MASK = MouseEvent.BUTTON3_DOWN_MASK;
			return;
		}
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(!e.isControlDown()) models.getSelectionModel().clear();
			MASK = MouseEvent.BUTTON1_DOWN_MASK;
		}else {
			MASK = MouseEvent.NOBUTTON;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(models.getActionModel().getAction() != Action.SELECT) return;
		view.setSelection(null);

		Point coord = convert(e.getPoint());
		Point pressedCoord = convert(pressed);
		if(coord.x != pressedCoord.x || coord.y != pressedCoord.y) {
			Point startCoord = convert(rect.getLocation());
			Point endCoord = convert(stop);
			int sizeX = endCoord.x-startCoord.x + 1;
			int sizeY = startCoord.y - endCoord.y + 1;


			if(MASK == MouseEvent.BUTTON1_DOWN_MASK) {
				for(int y = 0; y < sizeY; y++) {
					int coordY = startCoord.y-y;

					Map<Integer, Block> line = models.getBdd().getXMap(models.getLevelModel().getLevel(), coordY);
					if(line != null) {
						for(int x = 0; x < sizeX; x++) {
							int coordX = startCoord.x+x;
							if(line.get(coordX) != null) {
								models.getSelectionModel().add(new Point(coordX,coordY));
							}
						}
					}
				}
			}else if(MASK == MouseEvent.BUTTON3_DOWN_MASK) {
				Point tmp = new Point();
				for(int y = 0; y < sizeY; y++) {
					for(int x = 0; x < sizeX; x++) {
						tmp.setLocation(startCoord.x+x, startCoord.y-y);
						models.getSelectionModel().remove(tmp);
					}
				}

			}
		}else {
			if(MASK == MouseEvent.BUTTON1_DOWN_MASK && models.getBdd().getBlock(coord.x, models.getLevelModel().getLevel(), coord.y) != null) {
				models.getSelectionModel().add(coord);
			}else if(MASK == MouseEvent.BUTTON3_DOWN_MASK) {
				models.getSelectionModel().remove(coord);
			}
		}
		if(models.getSelectionModel().getSelection().size() > 1) {
			models.getBlockInfoModel().setMulti(true);
			models.getBlockInfoModel().setProperties(-2, -1);
		}else {
			models.getBlockInfoModel().setMulti(false);
			if(models.getSelectionModel().getSelection().size() < 1) {
				models.getBlockInfoModel().setProperties(-1, -1);
			}else{
				Point coordBlock = models.getSelectionModel().getSelection().iterator().next();
				Block b = models.getBdd().getBlock(coordBlock.x, models.getLevelModel().getLevel(), coordBlock.y);
				models.getBlockInfoModel().setProperties(b.getId(), b.getMetaData());
			}
		}
		models.getSelectionModel().fireUpdate();
	}

}
