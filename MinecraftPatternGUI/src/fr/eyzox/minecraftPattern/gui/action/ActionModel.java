package fr.eyzox.minecraftPattern.gui.action;

import java.util.Observable;

import fr.eyzox.minecraftPattern.gui.blockinfo.BlockInfoModel;
import fr.eyzox.minecraftPattern.gui.selection.SelectionModel;
import fr.eyzox.minecraftPattern.gui.toolbox.Toolbox;

public class ActionModel extends Observable {
	private Action action;
	private BlockInfoModel blockInfoModel;
	private SelectionModel selectionModel;
	private Toolbox toolbox;

	public ActionModel(BlockInfoModel blockInfoModel, SelectionModel selectionModel) {
		this.blockInfoModel = blockInfoModel;
		this.selectionModel = selectionModel;
	}
	
	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
		if(this.action == Action.SELECT) blockInfoModel.setProperties(-1, -1);
		else {
			selectionModel.clear();
			selectionModel.fireUpdate();
			if(toolbox != null) blockInfoModel.setProperties(toolbox.getSelectedItem().getId(), -1);
		}
		setChanged();
		notifyObservers();
	}
	
	public void setToolbox(Toolbox toolbox) {
		this.toolbox = toolbox;
	}
}
