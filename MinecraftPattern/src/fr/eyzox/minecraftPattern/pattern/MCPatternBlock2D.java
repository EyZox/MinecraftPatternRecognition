package fr.eyzox.minecraftPattern.pattern;

/**
 * A generic class to work without Forge or other dependencies representing a block in X and Z axis
 * @author Alexandre DUPONCHEL
 *
 */
public class MCPatternBlock2D {
	
	protected int id,x,z, dataValue;
	
	/**
	 * Creates a new block and ignore data value
	 * @param id
	 * 		The minecraft ID of the block, see <a href="http://minecraft.gamepedia.com/Data_values">Minecraft data values</a>
	 * @param x
	 * 		The X position of the block
	 * @param z
	 *  	The Z position of the block
	 */
	public MCPatternBlock2D(int id, int x, int z) {
		this(id,-1,x,z);
	}

	/**
	 * Creates a new block
	 * @param id
	 * 		The minecraft ID of the block, see <a href="http://minecraft.gamepedia.com/Data_values">Minecraft data values</a>
	 * @param dataValue
	 * 		The specifif data value of a block
	 * @param x
	 * 		The X position of the block
	 * @param z
	 *  	The Z position of the block
	 */
	public MCPatternBlock2D(int id, int dataValue, int x, int z) {
		this.id = id;
		this.dataValue = dataValue;
		this.x = x;
		this.z =z;
	}

	/**
	 * Gets the block's ID
	 * @return The minecraft ID of the block, see <a href="http://minecraft.gamepedia.com/Data_values">Minecraft data values</a>
	 */
	public int getID() {
		return id;
	}

	/**
	 * Sets the block's ID
	 * @param iD
	 * 		The minecraft ID of the block, see <a href="http://minecraft.gamepedia.com/Data_values">Minecraft data values</a>
	 */
	public void setID(int iD) {
		id = iD;
	}

	/**
	 * Gets the x-axis position of the block
	 * @return the x position
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the block's X-position
	 * @param x
	 * 		The relative x-position to a block (could be negative)
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the z-axis position of the block
	 * @return the z position
	 */
	public int getZ() {
		return z;
	}

	/**
	 * Sets the block's Z-position
	 * @param z
	 * 		The relative z-position to a block (could be negative)
	 */
	public void setZ(int z) {
		this.z = z;
	}
	
	/**
	 * Gets the specifics Data Value of the block
	 * @return the specifics Data Value of the block
	 */
	public int getDataValue() {
		return dataValue;
	}

	/**
	 * Sets the Data Value of the block
	 * @param dataValue
	 * 		if null : ignore data value
	 */
	public void setDataValue(int dataValue) {
		this.dataValue = dataValue;
	}

	/**
	 * Verifies if the block has the same properties of an other block 
	 */
	@Override
	public boolean equals(Object obj) {
		try {
			MCPatternBlock block = (MCPatternBlock) obj;
			return equalsIgnoreDataValue(block) && this.dataValue == block.dataValue; 
		}catch(ClassCastException e) {
			return false;
		}
	}
	
	/**
	 * Verifies if the block has the same properties of an other block ignoring data Value
	 */
	public boolean equalsIgnoreDataValue(Object obj) {
		try {
			MCPatternBlock block = (MCPatternBlock) obj;
			return this.id == block.id && this.x == block.x && this.z == block.z; 
		}catch(ClassCastException e) {
			return false;
		}
	}
	
	/**
	 * @return ID(DataValue) [X , Z]
	 */
	@Override
	public String toString() { return this.id + (dataValue!=-1?"("+getDataValue()+")":"")+ "["+this.x+" , "+this.z+"]"; }

	/**
	 * Equals equivalent but with ComparableBlock 
	 * @param b block to compare
	 * @return true if matches, else false
	 */
	public boolean matches(ComparableBlock b) {
		return this.id == b.getId() && (this.dataValue == -1 || this.dataValue == b.getMetaData());
	}
}
