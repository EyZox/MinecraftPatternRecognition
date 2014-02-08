package fr.eyzox.minecraftPattern.gui.action;

import java.util.Observable;

import fr.eyzox.minecraftPattern.gui.blockinfo.BlockInfoModel;
import fr.eyzox.minecraftPattern.gui.toolbox.Toolbox;

public class ActionModel extends Observable {
	private Action action;
	private BlockInfoModel blockInfoModel;
	private Toolbox toolbox;

	public ActionModel(BlockInfoModel blockInfoModel) {
		this.blockInfoModel = blockInfoModel;
	}
	
	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
		if(this.action == Action.SELECT) blockInfoModel.setProperties(-1, -1);
		else if(toolbox != null) blockInfoModel.setProperties(toolbox.getSelectedItem().getId(), -1);
		setChanged();
		notifyObservers();
	}
	
	public void setToolbox(Toolbox toolbox) {
		this.toolbox = toolbox;
	}
}
