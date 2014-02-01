package fr.eyzox.minecraftPattern.pattern;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.eyzox.minecraftPattern.io.MCPatternReader;
import fr.eyzox.minecraftPattern.io.MCPatternWriter;
/**
 * A pattern's representation
 * @author Alexandre DUPONCHEL
 *
 */
public class MCPattern {
	
	private List<MCPatternBlock> pattern;
	
	/**
	 * Creates an empty pattern
	 */
	public MCPattern() {
		pattern = new ArrayList<MCPatternBlock>();
	};
	
	/**
	 * Creates a pattern with a @see MCPatternReader but don't close it after use
	 * @param reader
	 * 		The MCPatternReader associated with the pattern's file 
	 * @throws IOException
	 * 		If an I/O error occurs
	 */
	public MCPattern(MCPatternReader reader) throws IOException {
		pattern = reader.extractAll();
	}

	/**
	 * Creates a MCPattern with an array of MCPatternBlock
	 * @param blocks
	 * 		A collection of MCPatternBlock representing the pattern
	 */
	public MCPattern(Collection<MCPatternBlock> blocks) {
		this.pattern = new ArrayList<MCPatternBlock>(blocks);
	}

	/**
	 * Gets the pattern
	 * @return the pattern
	 */
	public Collection<MCPatternBlock> getPattern() {
		return pattern;
	}

	/**
	 * Sets the pattern with an array of MCPatternBlock
	 * @param pattern
	 * 		A collection of MCPatternBlock representing the pattern
	 */	
	public void setPattern(Collection<MCPatternBlock> pattern) {
		this.pattern = new ArrayList<MCPatternBlock>(pattern);
	}
	
	/**
	 * Adds a new block to the pattern
	 * @param e
	 * 		The block which must be added
	 * @return
	 * 		True if operation is successful else false
	 */
	public boolean add(MCPatternBlock e) {
		return pattern.add(e);
	}

	/**
	 * Adds some new blocks to the pattern
	 * @param c
	 * 		The collection of blocks which must be added
	 * @return
	 * 		True if operation is successful else false
	 */
	public boolean addAll(Collection<? extends MCPatternBlock> c) {
		return pattern.addAll(c);
	}

	/**
	 * Removes a block from the pattern
	 * @param o
	 * 		The block which must be removed
	 * @return
	 * 		true if this list changed as a result of the call
	 */
	public boolean remove(MCPatternBlock o) {
		return pattern.remove(o);
	}

	/**
	 * Removes some blocks from the pattern
	 * @param c
	 * 		A collection of block which must be removed
	 * @return
	 * 		true if this list changed as a result of the call
	 */
	public boolean removeAll(Collection<MCPatternBlock> c) {
		return pattern.removeAll(c);
	}

	/**
	 * Gets the number of block in the pattern
	 * @return The number of block
	 */
	public int getNbBlock() {
		return pattern.size();
	}

	/**
	 * Tests if a block matches with the pattern
	 * @param anchor
	 * 		The block where pattern is based
	 * @return true if matches or false else.
	 */
	public boolean matches(AnchorBlock anchor) {
		for(MCPatternBlock b : pattern) {
			if(!b.matches(anchor.getBlockRelativeTo(b.getX(), b.getY(), b.getZ()))) return false;
		}
		return true;
	}
	
	/**
	 * Saves the current pattern into a new file specified by fileName
	 * @param MCPatternWriter
	 * @throws IOException If an I/O error occurs
	 */
	public void save(MCPatternWriter writer) throws IOException {
		writer.writePattern(this);
		writer.close();
	}
}
