package fr.eyzox.minecraftPattern.gui.panel;

import java.awt.Graphics;

import javax.swing.JPanel;

import fr.eyzox.minecraftPattern.gui.BlockInfos;
import fr.eyzox.minecraftPattern.pattern.MCPatternBlock2D;

@SuppressWarnings("serial")
public class MCPatternBlock2DUI extends JPanel implements OrientedGridItem{

	private MCPatternBlock2D block;
	
	public MCPatternBlock2DUI(MCPatternBlock2D block) {
		this.block = block;
	}
	
	public MCPatternBlock2D getBlock() { return block;}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(BlockInfos.getImage(block.getID()), 0, 0, this.getSize().width, this.getSize().height, null);
	}

	@Override
	public int getCoordX() {
		return block.getX();
	}

	@Override
	public int getCoordY() {
		return block.getZ();
	}

}
