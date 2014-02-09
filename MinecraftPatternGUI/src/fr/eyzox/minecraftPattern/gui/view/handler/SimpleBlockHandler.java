package fr.eyzox.minecraftPattern.gui.view.handler;

import java.awt.Point;
import java.awt.event.MouseEvent;

import fr.eyzox.minecraftPattern.gui.BlockEditionModels;
import fr.eyzox.minecraftPattern.gui.action.Action;
import fr.eyzox.minecraftPattern.gui.bdd.Block;
import fr.eyzox.minecraftPattern.gui.view.View;

public class SimpleBlockHandler extends BlockHandler {
	
	public SimpleBlockHandler(View view, BlockEditionModels models) {
		super(view, models);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(models.getActionModel().getAction() != Action.SELECT) {
			final Point coord = convert(e.getPoint());
			if(coord.x != pressedCoord.x || coord.y != pressedCoord.y) return;
			if(e.getButton() == MouseEvent.BUTTON1) {
				models.getBdd().add(coord.x, models.getLevelModel().getLevel(), coord.y, new Block(models.getBlockInfoModel().getId(), models.getBlockInfoModel().getMetadata()));
			}else if(e.getButton() == MouseEvent.BUTTON3) {
				models.getBdd().remove(coord.x, models.getLevelModel().getLevel(), coord.y);
			}
		}
	}
}
