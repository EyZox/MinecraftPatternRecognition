package fr.eyzox.minecraftPattern.pattern;

public interface ComparableBlock {
	/**
	 * Gets the block's id
	 * @return id
	 */
	public int getId();
	/**
	 * Gets the block's metadata
	 * @return metadata or -1 if ignoring 
	 */
	public int getMetaData();
}
