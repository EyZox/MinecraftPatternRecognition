package fr.eyzox.minecraftPattern.gui.blockinfo;

import java.util.Observable;

public class BlockInfoModel extends Observable {
	private int id = -1;
	private int metadata = -1;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
		setChanged();
		notifyObservers();
	}
	public int getMetadata() {
		return metadata;
	}
	public void setMetadata(int metadata) {
		this.metadata = metadata;
		setChanged();
		notifyObservers();
	}
	
	public void setProperties(int id, int metadata) {
		this.id = id;
		this.metadata = metadata;
		setChanged();
		notifyObservers();
	}
	
}
