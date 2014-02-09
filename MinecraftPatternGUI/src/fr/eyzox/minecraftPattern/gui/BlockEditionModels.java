package fr.eyzox.minecraftPattern.gui;

import fr.eyzox.minecraftPattern.gui.action.ActionModel;
import fr.eyzox.minecraftPattern.gui.bdd.BlockBDD;
import fr.eyzox.minecraftPattern.gui.blockinfo.BlockInfoModel;
import fr.eyzox.minecraftPattern.gui.level.LevelModel;
import fr.eyzox.minecraftPattern.gui.selection.SelectionModel;

public class BlockEditionModels {
	private BlockBDD bdd;
	private SelectionModel selectionModel;
	private ActionModel actionModel;
	private BlockInfoModel blockInfoModel;
	private LevelModel levelModel;
	
	public BlockEditionModels() {
		bdd = new BlockBDD();
		selectionModel = new SelectionModel();
		blockInfoModel = new BlockInfoModel();
		levelModel = new LevelModel();
		actionModel = new ActionModel(blockInfoModel, selectionModel);
	}

	public BlockBDD getBdd() {
		return bdd;
	}

	public SelectionModel getSelectionModel() {
		return selectionModel;
	}

	public ActionModel getActionModel() {
		return actionModel;
	}

	public BlockInfoModel getBlockInfoModel() {
		return blockInfoModel;
	}

	public LevelModel getLevelModel() {
		return levelModel;
	}
	
	
}
