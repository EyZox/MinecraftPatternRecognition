package fr.eyzox.minecraftPattern.gui.optionpanel;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import fr.eyzox.minecraftPattern.gui.FatalErrorPanel;

@SuppressWarnings("serial")
public class OptionPanel extends JPanel {
	private LevelSelector selectLevel;
	private BlockInfoPanel blockInfoPanel;

	public OptionPanel() {
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		try {
			selectLevel = new LevelSelector();
			selectLevel.setMaximumSize(new Dimension(Integer.MAX_VALUE,selectLevel.getPreferredSize().height));
			add(selectLevel);
		} catch (IOException e) {
			new FatalErrorPanel(e, true);
			e.printStackTrace();
		}
		
		blockInfoPanel = new BlockInfoPanel();
		add(blockInfoPanel);
	}

	public LevelSelector getSelectLevel() {
		return selectLevel;
	}

	public BlockInfoPanel getBlockInfoPanel() {
		return blockInfoPanel;
	}
	
	
}
