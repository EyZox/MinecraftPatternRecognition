package fr.eyzox.minecraftPattern.gui.action;

import java.util.Observable;

import fr.eyzox.minecraftPattern.gui.toolbox.MCToolBox;

public class ActionModel extends Observable {
	private Action action;
	private MCToolBox toolbox;
	
	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
		if(toolbox != null) {
			switch(action) {
			case ADDorREMOVE:
				toolbox.getModel().setProperties(toolbox.getSelectedItem().getId(), -1);
				break;
			case SELECT:
				toolbox.getModel().setProperties(-1, -1);
				break;
			default:
				break;
			
			}
		}
		
		setChanged();
		notifyObservers();
	}
	
	public void setToolbox(MCToolBox toolbox) {
		this.toolbox = toolbox;
	}
}
