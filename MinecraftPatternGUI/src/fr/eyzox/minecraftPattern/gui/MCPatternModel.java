package fr.eyzox.minecraftPattern.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import fr.eyzox.minecraftPattern.gui.optionpanel.BlockInfoModel;
import fr.eyzox.minecraftPattern.gui.testbranch.Block;
import fr.eyzox.minecraftPattern.gui.testbranch.Level;
import fr.eyzox.minecraftPattern.gui.testbranch.View;
import fr.eyzox.minecraftPattern.pattern.MCPattern;
import fr.eyzox.minecraftPattern.pattern.MCPatternBlock;

public class MCPatternModel implements Observer{
	
	private BlockBDD pattern;
	private Level level;
	private BlockInfoModel blockInfoModel;
	private View view;

	private boolean saved;
	private File file;
	
	public MCPatternModel() {
		this.pattern = new BlockBDD();
		this.level = new Level();
		this.blockInfoModel = new BlockInfoModel();
		this.view = new View(this);
		view.getActionModel().setBlockInfoModel(blockInfoModel);
		
		this.pattern.addObserver(this);
	}

	public void importPattern(MCPattern p, boolean forceNew){
		if(forceNew) {
			pattern.clear();
		}
		for(MCPatternBlock b : p.getPattern()) {
			pattern.add(b);
		}
	}
	
	public MCPattern export() {
		List<MCPatternBlock> res = new ArrayList<MCPatternBlock>();
		for(Integer levelY : pattern.getBlockMap().keySet()) {
			for(Integer levelZ : pattern.getZXMap(levelY).keySet()) {
				for(Integer levelX : pattern.getXMap(levelY, levelZ).keySet()) {
					Block b = pattern.getZXMap(levelY).get(levelZ).get(levelX);
					res.add(new MCPatternBlock(b.getId(), b.getMetaData(), levelX, levelY, levelZ));
				}
			}
		}
		return new MCPattern(res);
	}
	
	public void clear() {
		pattern.clear();
		saved = true;
	}
	
	public BlockBDD getPattern() {
		return pattern;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public View getView() {
		return view;
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
	
	public BlockInfoModel getBlockInfoModel() {
		return blockInfoModel;
	}

	@Override
	public void update(Observable o, Object arg) {
		saved = false;
	}
}
