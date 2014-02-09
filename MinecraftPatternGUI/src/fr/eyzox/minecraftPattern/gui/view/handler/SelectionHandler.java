package fr.eyzox.minecraftPattern.gui.view.handler;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Map;

import fr.eyzox.minecraftPattern.gui.BlockEditionModels;
import fr.eyzox.minecraftPattern.gui.action.Action;
import fr.eyzox.minecraftPattern.gui.bdd.Block;
import fr.eyzox.minecraftPattern.gui.view.View;

public class SelectionHandler extends CanDrawRectangleOverlay {

	protected BlockEditionModels models;

	public SelectionHandler(View view, BlockEditionModels models) {
		super(view);
		this.models = models;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(models.getActionModel().getAction() != Action.SELECT) return;
		super.mouseDragged(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(models.getActionModel().getAction() != Action.SELECT) return;
		super.mousePressed(e);
		if(!e.isControlDown() && MASK == MouseEvent.BUTTON1_DOWN_MASK) models.getSelectionModel().clear();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(models.getActionModel().getAction() != Action.SELECT) return;
		super.mouseReleased(e);
		
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
