package fr.eyzox.minecraftPattern.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import fr.eyzox.minecraftPattern.pattern.MCPattern;
import fr.eyzox.minecraftPattern.pattern.MCPatternBlock;

public class PtrnWriter extends MCPatternWriter{
	private PrintWriter pw;
	private boolean lineBegin = true;
	
	public PtrnWriter(File file) throws IOException {
		super(file);
		pw = new PrintWriter(new FileWriter(file));
	}
	
	public PtrnWriter(String fileName) throws IOException {
		this(new File(fileName));
	}
	
	@Override
	public void writeBlock(MCPatternBlock block) {
		if(!lineBegin) pw.println();
		pw.print(block.getID()+(block.getDataValue()!=-1?"["+block.getDataValue()+"]":"")+"("+block.getX()+","+block.getY()+","+block.getZ()+")");
		pw.flush();
		lineBegin = false;
	}
	
	public void writeComment(String comment) {
		String[] lineTab = comment.split("\n|\r");
		for(String s : lineTab){
			pw.println("#"+s);
			pw.flush();
			lineBegin = true;
		}
	}
	
	@Override
	public void writePattern(MCPattern p) {
		writeComment(new Date().toString());
		for(MCPatternBlock b : p.getPattern()) {
			writeBlock(b);
		}
	}
	
	@Override
	public void close() {
		pw.close();
	}
}
