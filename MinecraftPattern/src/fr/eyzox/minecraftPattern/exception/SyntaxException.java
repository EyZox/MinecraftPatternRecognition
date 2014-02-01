package fr.eyzox.minecraftPattern.exception;

import java.io.File;
/**
 * An Exception who indicates if an minecraft pattern's file contain invalid line(s)
 * @author Alexandre DUPONCHEL
 *
 */
@SuppressWarnings("serial")
public class SyntaxException extends Exception {
	private File file;
	
	/**
	 * Creates a new instance of SyntaxException
	 * @param file
	 * 		The file where the exception occurs 
	 */
	public SyntaxException(File file) {
		super("File "+file.getName()+" is corrupted, please check syntax of your pattern file");
		this.file = file;
	}
	
	/**
	 * Gets the file where the exception occurs
	 * @return The file where the exception occurs 
	 */
	public File getFile() {
		return file;
	}

}
