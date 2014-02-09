package fr.eyzox.minecraftPattern.gui.view.handler;

import java.awt.Point;
import java.awt.event.MouseEvent;

import fr.eyzox.minecraftPattern.gui.BlockEditionModels;
import fr.eyzox.minecraftPattern.gui.action.Action;
import fr.eyzox.minecraftPattern.gui.bdd.Block;
import fr.eyzox.minecraftPattern.gui.view.View;

public class MultiBlockHandler extends BlockHandler {

	private int MASK;
	
	public MultiBlockHandler(View view, BlockEditionModels models) {
		super(view, models);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		if(e.getButton() == MouseEvent.BUTTON1) {
			MASK = MouseEvent.BUTTON1_DOWN_MASK;
		}else if(e.getButton() == MouseEvent.BUTTON3) {
			MASK = MouseEvent.BUTTON3_DOWN_MASK;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point coord = convert(e.getPoint());
		if((e.getModifiersEx() & MASK) == MASK && (coord.x != pressedCoord.x || coord.y != pressedCoord.y)){
			if(MASK == MouseEvent.BUTTON1_DOWN_MASK) {
				if(models.getActionModel().getAction() == Action.SELECT) {
					if(!models.getSelectionModel().getSelection().contains(coord) && models.getBdd().getBlock(coord.x, models.getLevelModel().getLevel(), coord.y) != null) models.getSelectionModel().add(coord);
				}else {
					models.getBdd().add(coord.x, models.getLevelModel().getLevel(), coord.y, new Block(models.getBlockInfoModel().getId(), models.getBlockInfoModel().getMetadata()));
				}
			}else if(MASK == MouseEvent.BUTTON3_DOWN_MASK) {
				if(models.getActionModel().getAction() == Action.SELECT) {
					models.getSelectionModel().remove(coord);
				}else {
					models.getBdd().remove(coord.x,  models.getLevelModel().getLevel(), coord.y);
				}
			}
			pressedCoord = coord;
		}
	}

}
