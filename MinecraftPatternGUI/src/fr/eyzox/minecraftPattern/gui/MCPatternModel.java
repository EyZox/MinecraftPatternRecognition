package fr.eyzox.minecraftPattern.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;

import fr.eyzox.minecraftPattern.pattern.MCPattern;
import fr.eyzox.minecraftPattern.pattern.MCPatternBlock;
import fr.eyzox.minecraftPattern.pattern.MCPatternBlock2D;

public class MCPatternModel extends Observable{
	
	public class coord implements Comparable<coord>{
		private int x;
		private int y;
		
		public coord(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(coord o) {
			if(x < o.x) return -1;
			if(x > o.x) return 1;
			if(y < o.y) return -1;
			if(y > o.y) return 1;
			return 0;
		}
	}
	
	private Map<Integer, Map<coord,MCPatternBlock2D>> pattern;
	private boolean saved;
	private File file;
	
	public MCPatternModel() {
		this.pattern = new TreeMap<Integer, Map<coord,MCPatternBlock2D>>();
		saved = true;
	}

	public void fireUpdate() {
		saved = false;
		setChanged();
		notifyObservers();
	}

	public void add(int level, MCPatternBlock2D block) {
		Map<coord,MCPatternBlock2D> pattern2D = pattern.get(level);
		if(pattern2D == null) {
			pattern2D = new TreeMap<coord,MCPatternBlock2D>();
			pattern.put(level, pattern2D);
		}
		pattern2D.put(new coord(block.getX(), block.getZ()),block);
		fireUpdate();
	}
	
	public void remove(int level, MCPatternBlock2D block) {
		Map<coord,MCPatternBlock2D> pattern2D = pattern.get(level);
		if(pattern2D != null) {
			pattern2D.remove(block);
			fireUpdate();
		}
		
	}
	
	public void remove(int x, int y, int z) {
		Map<coord,MCPatternBlock2D> pattern2D = pattern.get(y);
		if(pattern2D != null) {
			pattern2D.remove(new coord(x,z));
		}
		fireUpdate();
		
	}
	
	public void importPattern(MCPattern p, boolean forceNew){
		if(forceNew) {
			pattern.clear();
		}
		for(MCPatternBlock b : p.getPattern()) {
			if(!pattern.keySet().contains(b.getY())){
				pattern.put(b.getY(), new TreeMap<coord,MCPatternBlock2D>());
			}
			pattern.get(b.getY()).put(new coord(b.getX(),b.getZ()),b);
		}
		fireUpdate();
	}
	
	public MCPattern export() {
		List<MCPatternBlock> res = new ArrayList<MCPatternBlock>();
		for(Integer level : pattern.keySet()) {
			for(coord c: pattern.get(level).keySet()) {
				res.add(new MCPatternBlock(pattern.get(level).get(c), level));
			}
		}
		return new MCPattern(res);
	}
	
	public Map<Integer,Map<coord,MCPatternBlock2D>> getPattern() {
		return pattern;
	}

	public void setPattern(Map<Integer, Map<coord,MCPatternBlock2D>> pattern) {
		this.pattern = pattern;
		fireUpdate();
	}
	
	public coord getCoord(int x, int y) {
		return new coord(x, y);
	}
	
	public void clear() {
		pattern.clear();
		fireUpdate();
		saved = true;
	}

	public boolean isSaved() {
		return saved;
	}
	
	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
