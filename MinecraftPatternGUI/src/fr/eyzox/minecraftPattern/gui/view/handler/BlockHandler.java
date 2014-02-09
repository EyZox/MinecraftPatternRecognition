package fr.eyzox.minecraftPattern.gui.view.handler;

import java.awt.Point;
import java.awt.event.MouseEvent;

import fr.eyzox.minecraftPattern.gui.BlockEditionModels;
import fr.eyzox.minecraftPattern.gui.action.Action;
import fr.eyzox.minecraftPattern.gui.bdd.Block;
import fr.eyzox.minecraftPattern.gui.view.View;

public class BlockHandler extends ViewEventListener {

	private int MASK;
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
		if(e.getButton() == MouseEvent.BUTTON1) {
			MASK = MouseEvent.BUTTON1_DOWN_MASK;
		}else if(e.getButton() == MouseEvent.BUTTON3) {
			MASK = MouseEvent.BUTTON3_DOWN_MASK;
		}else {
			MASK = MouseEvent.NOBUTTON;
		}
		firstPressed = true;
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		if(models.getActionModel().getAction() != Action.ADDorREMOVE) return;
		final Point coord = convert(e.getPoint());
		if(coord.x != pressedCoord.x || coord.y != pressedCoord.y) {
			if(e.isControlDown()) {
				//Rectangle
			}
		}else {
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
			models.getBdd().add(pressedCoord.x, models.getLevelModel().getLevel(), pressedCoord.y, new Block(models.getBlockInfoModel().getId(), models.getBlockInfoModel().getMetadata()));
			firstPressed = false;
		}
		Point coord = convert(e.getPoint());
		if(e.isControlDown()) {
			//Rectangle
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
