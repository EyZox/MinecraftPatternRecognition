package fr.eyzox.minecraftPattern.gui.config;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import fr.eyzox.minecraftPattern.gui.customframe.FatalError;

public class BlockInfos {
	
	private static class BlockInfo {
		Image img;
		String name;
	}
	
	private static Map<Integer, BlockInfo> map;
	private static Image NOT_FOUND;
	public static final String CURSOR = "APP_CURSOR";  
	
	public static Map<Integer, BlockInfo> get() {
		if(map == null) init();
		return map;
	}
	
	public static Image getImage(int id) {
		if(map == null) init();
		if(map.get(id) == null) return NOT_FOUND;
		return map.get(id).img;
	}
	
	public static String getNameOf(int id) {
		if(map == null) init();
		if(map.get(id) == null) return "NOT FOUND";
		return map.get(id).name;
	}

	private static void init() {
		map = new TreeMap<Integer, BlockInfo>();
		try {
			NOT_FOUND = ImageIO.read(BlockInfos.class.getResource("/data/not_found.png"));
			
			File f = new File("BlockID.txt");
			if(!f.exists()) {
				BlockIDGenerator.generate();
			}
			BufferedReader br = new BufferedReader(new FileReader(f));
			Pattern p = Pattern.compile("(?<id>\\d+):(?<fileName>.*)");
			String line;
			
			while((line = br.readLine()) != null) {
				Matcher m = p.matcher(line);
				if(m.matches()) {
					BlockInfo b = new BlockInfo();
					try {
						b.img = ImageIO.read(new File("textures/blocks/"+m.group("fileName")));
					} catch (IOException e) {
						b.img = NOT_FOUND;
					}
					b.name = m.group("fileName").replaceAll("\\..*", "").replaceAll("_", " ");
					map.put(Integer.parseInt(m.group("id")), b);
				}
			}
			br.close();
		} catch (IOException e) {
			FatalError.open(e, true);
			e.printStackTrace();
		}
	}
}
