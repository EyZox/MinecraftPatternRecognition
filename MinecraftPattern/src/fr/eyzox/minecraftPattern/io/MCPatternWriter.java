package fr.eyzox.minecraftPattern.io;

import java.io.File;

import fr.eyzox.minecraftPattern.pattern.MCPattern;
import fr.eyzox.minecraftPattern.pattern.MCPatternBlock;

public abstract class MCPatternWriter {
	
	protected File file;
	/**
	 * Creates a new instance of MCPatternWriter using file
	 * @param file
	 */
	public MCPatternWriter(File file) {
		this.file = file;
	}
	
	/**
	 * Creates a new instance of MCPatternWriter using filename
	 * @param fileName
	 */
	public MCPatternWriter(String fileName) {
		this(new File(fileName));
	}
	
	/**
	 * Writes a block into the file
	 * @param block
	 */
	public abstract void writeBlock(MCPatternBlock block);
	/**
	 * Writes all pattern's block into the file
	 * @param p
	 */
	public abstract void writePattern(MCPattern p);
	
	/**
	 * Closes ressources stream
	 */
	public abstract void close();
}
