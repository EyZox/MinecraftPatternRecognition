package fr.eyzox.minecraftPattern.pattern;
/**
 * A generic class to work without Forge or other dependencies
 * @author Alexandre DUPONCHEL
 *
 */
public class MCPatternBlock extends MCPatternBlock2D{
	
	// from MCPatternBlock2D : id, dataValue, x, z;
	protected int y;

	
	/**
	 * Creates a new block and ignore data value
	 * @param id
	 * 		The minecraft ID of the block, see <a href="http://minecraft.gamepedia.com/Data_values">Minecraft data values</a>
	 * @param x
	 * 		The X position of the block
	 * @param y
	 *  	The Y position of the block
	 * @param z
	 *  	The Z position of the block
	 */
	public MCPatternBlock(int id, int x, int y, int z) {
		this(id,-1,x,y,z);
	}
	
	/**
	 * Creates a new block
	 * @param id
	 * 		The minecraft ID of the block, see <a href="http://minecraft.gamepedia.com/Data_values">Minecraft data values</a>
	 * @param dataValue
	 * 		The specifif data value of a block
	 * @param x
	 * 		The X position of the block
	 * @param y
	 *  	The Y position of the block
	 * @param z
	 *  	The Z position of the block
	 */
	public MCPatternBlock(int id,int dataValue, int x, int y, int z) {
		super(id,dataValue,x,z);
		this.y = y;
	}
	
	/**
	 * Creates a new block from a 2D block and a depth (y axis)
	 * @param block2D
	 * 		The 2D block
	 * @param y
	 * 	The depth
	 */
	public MCPatternBlock(MCPatternBlock2D block2D, int y) {
		this(block2D.id, block2D.dataValue, block2D.x,y, block2D.z);
	}

	/**
	 * Gets the y-axis position of the block
	 * @return the y position
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the block's Y-position
	 * @param y
	 * 		The relative y-position to a block (could be negative)
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Verifies if the block has the same properties of an other block 
	 */
	@Override
	public boolean equals(Object obj) {
		try {
			MCPatternBlock block = (MCPatternBlock) obj;
			return super.equals(block) && this.y == block.y; 
		}catch(ClassCastException e) {
			return false;
		}
	}
	
	/**
	 * Verifies if the block has the same properties of an other block ignoring dataValue
	 */
	@Override
	public boolean equalsIgnoreDataValue(Object obj) {
		try {
			MCPatternBlock block = (MCPatternBlock) obj;
			return super.equalsIgnoreDataValue(block) && this.y == block.y; 
		}catch(ClassCastException e) {
			return false;
		}
	}
	
	/**
	 * @return ID(Data Value) [X , Y , Z]
	 */
	@Override
	public String toString() { return this.id + (dataValue!=-1?"("+getDataValue()+")":"")+ "["+this.x+" , "+this.y+" , "+this.z+"]"; }
}
