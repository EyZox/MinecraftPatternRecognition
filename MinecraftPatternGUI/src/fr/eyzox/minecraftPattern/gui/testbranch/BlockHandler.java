package fr.eyzox.minecraftPattern.gui.testbranch;

import java.awt.Point;
import java.awt.event.MouseEvent;

import fr.eyzox.minecraftPattern.gui.action.Action;

public class BlockHandler extends ViewEventListener {
	
	public BlockHandler(View view) {
		super(view);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			final Point coord = convert(e.getPoint());
			if(view.getActionModel().getAction() == Action.SELECT) {
				Block b = view.getModel().getPattern().getBlock(coord.x, view.getModel().getLevel().getLevel(), coord.y);
				if(b == null) {
					view.getModel().getBlockInfoModel().setProperties(-1, -1);
				}else {
					view.getModel().getBlockInfoModel().setProperties(b.getId(), b.getMetaData());
				}
				view.getSelectionModel().setSelection(coord);
			}else {
				view.getModel().getPattern().add(coord.x, 0, coord.y, new Block(view.getModel().getBlockInfoModel().getId(), view.getModel().getBlockInfoModel().getMetadata()));
			}
			
		}else if(e.getButton() == MouseEvent.BUTTON3) {
			Point coord = convert(e.getPoint());
			view.getModel().getPattern().remove(coord.x, 0, coord.y);
		}

	}
}
