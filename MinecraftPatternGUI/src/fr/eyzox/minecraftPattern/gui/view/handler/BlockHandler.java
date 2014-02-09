package fr.eyzox.minecraftPattern.gui.view.handler;

import java.awt.Point;
import java.awt.event.MouseEvent;

import fr.eyzox.minecraftPattern.gui.BlockEditionModels;
import fr.eyzox.minecraftPattern.gui.view.View;

public abstract class BlockHandler extends ViewEventListener {
	
	protected Point pressedCoord;
	protected BlockEditionModels models;
	
	public BlockHandler(View view, BlockEditionModels models) {
		super(view);
		this.models = models;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressedCoord = convert(e.getPoint());
	}
	
	
	
}
