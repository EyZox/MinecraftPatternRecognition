package fr.eyzox.minecraftPattern.gui.optionpanel;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import fr.eyzox.minecraftPattern.gui.FatalError;
import fr.eyzox.minecraftPattern.gui.MCPatternModel;

@SuppressWarnings("serial")
public class OptionPanel extends JPanel {
	private LevelSelector selectLevel;
	private BlockInfoPanel blockInfoPanel;

	public OptionPanel(MCPatternModel model) {
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		try {
			selectLevel = new LevelSelector(model.getLevel());
			selectLevel.setMaximumSize(new Dimension(Integer.MAX_VALUE,selectLevel.getPreferredSize().height));
			add(selectLevel);
		} catch (IOException e) {
			FatalError.open(e, true);
			e.printStackTrace();
		}
		
		blockInfoPanel = new BlockInfoPanel(model.getBlockInfoModel(), model.getView().getActionModel());
		add(blockInfoPanel);
	}

	public LevelSelector getSelectLevel() {
		return selectLevel;
	}

	public BlockInfoPanel getBlockInfoPanel() {
		return blockInfoPanel;
	}
	
	
}
