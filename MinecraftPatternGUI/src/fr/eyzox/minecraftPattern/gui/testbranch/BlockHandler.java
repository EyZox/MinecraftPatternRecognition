package fr.eyzox.minecraftPattern.gui.testbranch;

import java.awt.Point;
import java.awt.event.MouseEvent;

import fr.eyzox.minecraftPattern.gui.action.Action;
import fr.eyzox.minecraftPattern.gui.optionpanel.BlockInfoModel;

public class BlockHandler extends ViewEventListener {
	
	public BlockHandler(View view) {
		super(view);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			final Point coord = convert(e.getPoint());
			
			if(view.getActionModel().getAction() == Action.SELECT) {
				final Block selectedBlock = view.getModel().getPattern().getBlock(coord.x, view.getModel().getLevel().getLevel(), coord.y);
				if(selectedBlock != null) {
					view.getSelectionModel().setSelection(coord);
					view.getModel().getBlockInfoModel().setProperties(selectedBlock.getId(), selectedBlock.getMetaData());
				}else {
					view.getSelectionModel().setSelection(null);
					view.getModel().getBlockInfoModel().setProperties(-1,-1);
				}	
			}else {
				//Action == ADD || REMOVE
				BlockInfoModel blockInfo = view.getModel().getBlockInfoModel();
				view.getModel().getPattern().add(coord.x, view.getModel().getLevel().getLevel(), coord.y, new Block(blockInfo.getId(), blockInfo.getMetadata()));
			}
			
		}else if(e.getButton() == MouseEvent.BUTTON3) {
			Point coord = convert(e.getPoint());
			view.getModel().getPattern().remove(coord.x, view.getModel().getLevel().getLevel(), coord.y);
		}

	}
}
