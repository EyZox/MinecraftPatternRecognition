package fr.eyzox.minecraftPattern.gui.action;

import java.util.Observable;

import fr.eyzox.minecraftPattern.gui.optionpanel.BlockInfoModel;
import fr.eyzox.minecraftPattern.gui.toolbox.MCToolBox;

public class ActionModel extends Observable {
	private Action action;
	private BlockInfoModel blockInfoModel;
	private MCToolBox toolbox;

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
		if(blockInfoModel != null) {
			if(this.action == Action.SELECT) blockInfoModel.setProperties(-1, -1);
			else if(toolbox != null) blockInfoModel.setProperties(toolbox.getSelectedItem().getId(), -1);
		}
		setChanged();
		notifyObservers();
	}
	
	public void setToolbox(MCToolBox toolbox) {
		this.toolbox = toolbox;
	}
	
	public void setBlockInfoModel(BlockInfoModel model) {
		this.blockInfoModel = model;
	}
}
