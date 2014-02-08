package fr.eyzox.minecraftPattern.gui.view.handler;

import java.awt.Point;
import java.awt.event.MouseEvent;

import fr.eyzox.minecraftPattern.gui.BlockEditionModels;
import fr.eyzox.minecraftPattern.gui.action.Action;
import fr.eyzox.minecraftPattern.gui.bdd.Block;
import fr.eyzox.minecraftPattern.gui.view.View;

public class BlockHandler extends ViewEventListener {
	
	private BlockEditionModels models;
	
	
	
	public BlockHandler(View view, BlockEditionModels models) {
		super(view);
		this.models = models;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			final Point coord = convert(e.getPoint());
			
			if(models.getActionModel().getAction() == Action.SELECT) {
				final Block selectedBlock = models.getBdd().getBlock(coord.x, models.getLevelModel().getLevel(), coord.y);
				if(selectedBlock != null) {
					models.getSelectionModel().setSelection(coord);
					models.getBlockInfoModel().setProperties(selectedBlock.getId(), selectedBlock.getMetaData());
				}else {
					models.getSelectionModel().setSelection(null);
					models.getBlockInfoModel().setProperties(-1,-1);
				}	
			}else {
				//Action == ADD || REMOVE
				models.getBdd().add(coord.x, models.getLevelModel().getLevel(), coord.y, new Block(models.getBlockInfoModel().getId(), models.getBlockInfoModel().getMetadata()));
			}
			
		}else if(e.getButton() == MouseEvent.BUTTON3) {
			Point coord = convert(e.getPoint());
			models.getBdd().remove(coord.x, models.getLevelModel().getLevel(), coord.y);
		}

	}
}
