package fr.eyzox.minecraftPattern.gui.view.handler;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.TreeMap;

import fr.eyzox.minecraftPattern.gui.BlockEditionModels;
import fr.eyzox.minecraftPattern.gui.action.Action;
import fr.eyzox.minecraftPattern.gui.bdd.Block;
import fr.eyzox.minecraftPattern.gui.view.View;

public class BlockHandler extends CanDrawRectangleOverlay {

	protected BlockEditionModels models;
	protected Point pressedCoord;
	private boolean firstPressed;

	public BlockHandler(View view, BlockEditionModels models) {
		super(view);
		this.models = models;
	}



	@Override
	public void mousePressed(MouseEvent e) {
		if(models.getActionModel().getAction() != Action.ADDorREMOVE) return;
		pressedCoord = convert(e.getPoint());
		super.mousePressed(e);
		firstPressed = true;
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		if(models.getActionModel().getAction() != Action.ADDorREMOVE) return;
		super.mouseReleased(e);
		final Point coord = convert(e.getPoint());
		final Point pressedC = convert(pressed);
		if(coord.x != pressedC.x || coord.y != pressedC.y) {
			if(e.isControlDown()) {
				Point startCoord = convert(rect.getLocation());
				Point endCoord = convert(stop);
				int sizeX = endCoord.x-startCoord.x + 1;
				int sizeY = startCoord.y - endCoord.y + 1;
				Map<Integer,Map<Integer,Block>> levelY = models.getBdd().getZXMap(models.getLevelModel().getLevel());
				if(levelY == null) {
					levelY = new TreeMap<Integer, Map<Integer,Block>>();
					models.getBdd().getBlockMap().put(models.getLevelModel().getLevel(), levelY);
				}
				if(MASK == MouseEvent.BUTTON1_DOWN_MASK) {
					for(int y = 0; y < sizeY; y++) {
						Map<Integer, Block> line = levelY.get(startCoord.y-y);
						if(line == null) {
							line = new TreeMap<Integer,Block>();
							levelY.put(startCoord.y-y, line);
						}
						for(int x = 0; x < sizeX; x++) {
							line.put(startCoord.x+x, new Block(models.getBlockInfoModel().getId(), models.getBlockInfoModel().getMetadata()));
						}
					}
				}else if(MASK == MouseEvent.BUTTON3_DOWN_MASK) {
					for(int y = 0; y < sizeY; y++) {
						Map<Integer, Block> line = levelY.get(startCoord.y-y);
						if(line == null) {
							line = new TreeMap<Integer,Block>();
							levelY.put(startCoord.y-y, line);
						}
						for(int x = 0; x < sizeX; x++) {
							line.remove(startCoord.x+x);
						}
						if(line.isEmpty()) {
							levelY.remove(startCoord.y-y);
						}
					}
				}else {
					return;
				}
				models.getBdd().fireUpdate();
			}
		}else if(!e.isControlDown()){
			if(MASK == MouseEvent.BUTTON1_DOWN_MASK) {
				models.getBdd().add(coord.x, models.getLevelModel().getLevel(), coord.y, new Block(models.getBlockInfoModel().getId(), models.getBlockInfoModel().getMetadata()));
			}else if(MASK == MouseEvent.BUTTON3_DOWN_MASK) {
				models.getBdd().remove(coord.x, models.getLevelModel().getLevel(), coord.y);
			}
		}
	}



	@Override
	public void mouseDragged(MouseEvent e) {
		if(models.getActionModel().getAction() != Action.ADDorREMOVE) return;
		if(firstPressed) {
			if(!e.isControlDown()) {
				if(MASK == MouseEvent.BUTTON1_DOWN_MASK) models.getBdd().add(pressedCoord.x, models.getLevelModel().getLevel(), pressedCoord.y, new Block(models.getBlockInfoModel().getId(), models.getBlockInfoModel().getMetadata()));
				else if (MASK == MouseEvent.BUTTON3_DOWN_MASK) models.getBdd().remove(pressedCoord.x, models.getLevelModel().getLevel(), pressedCoord.y);
			}
			firstPressed = false;
		}
		Point coord = convert(e.getPoint());
		if(e.isControlDown()) {
			super.mouseDragged(e);
		}else if(coord.x != pressedCoord.x || coord.y != pressedCoord.y){
			if(MASK == MouseEvent.BUTTON1_DOWN_MASK) {
				models.getBdd().add(coord.x, models.getLevelModel().getLevel(), coord.y, new Block(models.getBlockInfoModel().getId(), models.getBlockInfoModel().getMetadata()));
			}else if(MASK == MouseEvent.BUTTON3_DOWN_MASK) {
				models.getBdd().remove(coord.x, models.getLevelModel().getLevel(), coord.y);
			}
		}
		pressedCoord = coord;
	}


}
