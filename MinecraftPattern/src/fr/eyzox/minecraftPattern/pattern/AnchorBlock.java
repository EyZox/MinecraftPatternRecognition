package fr.eyzox.minecraftPattern.pattern;

public interface AnchorBlock {
	/**
	 * Gets the block from a relative position
	 * @param dx
	 * 		the X relative position
	 * @param dy
	 * 		the Y relative position
	 * @param dz
	 * 		the Z relative position
	 * @return The block from a relative position
	 */
	public ComparableBlock getBlockRelativeTo(int dx, int dy, int dz);
}
