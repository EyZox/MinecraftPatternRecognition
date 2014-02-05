package fr.eyzox.minecraftPattern.gui.testbranch;

import fr.eyzox.minecraftPattern.pattern.ComparableBlock;

public class Block implements ComparableBlock {
	private int id,metadata;
	
	public Block(int id, int metadata) {
		this.id =id;
		this.metadata = metadata;
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public int getMetaData() {
		return metadata;
	}

	public void setMetadata(int metadata) {
		this.metadata = metadata;
	}

	public void setId(int id) {
		this.id = id;
	}

	
}
