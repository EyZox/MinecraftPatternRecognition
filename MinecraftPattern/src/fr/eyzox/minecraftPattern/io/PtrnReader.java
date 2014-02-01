package fr.eyzox.minecraftPattern.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.eyzox.minecraftPattern.exception.SyntaxException;
import fr.eyzox.minecraftPattern.pattern.MCPatternBlock;

public class PtrnReader extends MCPatternReader {
	private static final Pattern LINE_OK = Pattern.compile("(?<id>\\d+)((\\[(?<dataValue>\\d+)\\])?)\\((?<x>-?\\d+),(?<y>-?\\d+),(?<z>-?\\d+)\\)");
	private static final String IGNORE = " |\t|#.*";
	private BufferedReader br;
	
	public PtrnReader(File file) throws FileNotFoundException, IOException {
		super(file);
		br = new BufferedReader(new FileReader(file));
	}
	
	@Override
	public MCPatternBlock read() throws IOException, SyntaxException {
		String line = br.readLine();
		if(line == null) return null;
		line = line.replaceAll(IGNORE, "");
		if(line.length() == 0) return read();
		Matcher m = LINE_OK.matcher(line);
		if(m.matches()) {
			return new MCPatternBlock(
					Integer.parseInt(m.group("id")),
					m.group("dataValue")!=null?Integer.parseInt(m.group("dataValue")):-1,
							Integer.parseInt(m.group("x")),
							Integer.parseInt(m.group("y")),
							Integer.parseInt(m.group("z")));
		}else {
			throw new SyntaxException(file);
		}
	}
	
	@Override
	public void close() throws IOException {
		br.close();
	}

	@Override
	public List<MCPatternBlock> extractAll() throws IOException {
		List<MCPatternBlock> list = new ArrayList<MCPatternBlock>();
		MCPatternBlock b;
		try {
			while((b = read())!=null) {
				list.add(b);
			}
		} catch (SyntaxException e) {
			System.err.println(e.getMessage());
		}
		return list;
	}
}
