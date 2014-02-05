package fr.eyzox.minecraftPattern.gui;

import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;

import fr.eyzox.minecraftPattern.gui.testbranch.Block;
import fr.eyzox.minecraftPattern.pattern.MCPatternBlock;

public class BlockBDD extends Observable{
	// Map<Y,Map<X, Map<Z, Block>>>
	private Map<Integer,Map<Integer,Map<Integer,Block>>> blocks;
	
	public BlockBDD() {
		blocks = new TreeMap<Integer,Map<Integer,Map<Integer,Block>>>();
	}
	
	public void add(MCPatternBlock b) {
		this.add(b.getX(), b.getY(), b.getZ(), new Block(b.getID(), b.getDataValue()));
	}
	
	public void add(int x, int y, int z, Block b) {
		Map<Integer, Map<Integer, Block>> levelY = blocks.get(y);
		if(levelY == null) {
			levelY = new TreeMap<Integer, Map<Integer, Block>>();
			blocks.put(y, levelY);
		}
		Map<Integer, Block> levelZ = levelY.get(z);
		if(levelZ == null){
			levelZ = new TreeMap<Integer, Block>();
			levelY.put(z, levelZ);
		}
		levelZ.put(x, b);
		setChanged();
		notifyObservers();
	}
	
	public void remove(MCPatternBlock b) {
		this.remove(b.getX(), b.getY(), b.getZ());
	}
	
	public void remove(int x, int y, int z) {
		Map<Integer, Map<Integer, Block>> levelY = blocks.get(y);
		if(levelY == null) return;
		Map<Integer, Block> levelZ = levelY.get(z);
		if(levelZ == null) return;
		levelZ.remove(x);
		
		if(levelZ.size() == 0) levelY.remove(z);
		if(levelY.size() == 0) blocks.remove(y);
		
		setChanged();
		notifyObservers();
	}
	
	public Block getBlock(int x,int y, int z) {
		Map<Integer, Map<Integer, Block>> levelY = blocks.get(y);
		if(levelY == null) return null;
		Map<Integer, Block> levelZ = levelY.get(z);
		if(levelZ == null) return null;
		return levelZ.get(x);
		
	}
	
	public MCPatternBlock getMCPatternBlock(int x, int y, int z) {
		Block b = this.getBlock(x, y, z);
		if(b == null) return null;
		return new MCPatternBlock(b.getId(),b.getMetaData(), x, y, z);
	}
	
	public Map<Integer,Map<Integer,Map<Integer,Block>>> getBlockMap() {
		return blocks;
	}
	
	public Map<Integer,Map<Integer,Block>> getZXMap(int y) {
		return blocks.get(y);
	}
	
	public Map<Integer,Block> getXMap(int y, int z) {
		Map<Integer, Map<Integer, Block>> levelY = blocks.get(y);
		if(levelY == null) return null;
		return levelY.get(z);
	}
	
	public void clear() {
		blocks.clear();
		setChanged();
		notifyObservers();
	}
}
