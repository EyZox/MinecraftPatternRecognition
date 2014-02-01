package fr.eyzox.minecraftPattern.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import fr.eyzox.minecraftPattern.exception.SyntaxException;
import fr.eyzox.minecraftPattern.pattern.MCPatternBlock;
/**
 * A reader for minecraft pattern's file
 * @author Alexandre DUPONCHEL
 *
 */
public abstract class MCPatternReader{

	protected File file;


	/**
	 * Creates a new PatternReader using filename
	 * @param fileName
	 * 		Name of pattern's file
	 * @throws FileNotFoundException
	 * 		If file at fileName was not found
	 * @throws SyntaxException
	 * 		If file contains invalid line(s)
	 * @throws IOException
	 * 		If an I/O error occurs
	 */
	public MCPatternReader(String fileName) throws FileNotFoundException, IOException {
		this(new File(fileName));
	}

	/**
	 * Creates a new PatternReader using file
	 * @param file
	 * 		pattern's file
	 * @throws FileNotFoundException
	 * 		If file at fileName was not found
	 * @throws IOException
	 * 		If an I/O error occurs
	 */
	public MCPatternReader(File file) throws FileNotFoundException, IOException {
		this.file = file;
	}

	/**
	 * Closes the stream and releases any system resources associated with it. Once the stream has been closed, read() invocations will throw an IOException. Closing a previously closed stream has no effect.
	 * @throws IOException
	 * 		If an I/O error occurs
	 */
	public abstract void close() throws IOException;
	
	/**
	 * Extract all the pattern's blocks from the pattern file to a List
	 * @return All file's blocks into this List
	 * @throws IOException 
	 */
	public abstract List<MCPatternBlock> extractAll() throws IOException;
	
	/**
	 * Reads a block into the pattern file
	 * @return A PatternBlock from the pattern's file
	 * @throws IOException
	 * 		If an I/O error occurs
	 * @throws SyntaxException
	 * 		When file syntax is invalid
	 */
	public abstract MCPatternBlock read() throws IOException, SyntaxException;
}
