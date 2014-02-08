package fr.eyzox.minecraftPattern.gui.toolbox;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.eyzox.minecraftPattern.gui.config.BlockInfos;

@SuppressWarnings("serial")
public class ToolboxItem extends JRadioButton{
	
	private int id;
	private final static Border selectedBorder = BorderFactory.createLoweredBevelBorder();
	public final static int preferredSize = 32;
	
	public ToolboxItem(int id) {
		super();
		this.setId(id);
		setPreferredSize(new Dimension(preferredSize,preferredSize));
		this.setIcon(new ImageIcon(BlockInfos.getImage(id).getScaledInstance(getPreferredSize().width-4, getPreferredSize().height-4, Image.SCALE_FAST)));
		setBorder(selectedBorder);
		this.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				setBorderPainted(ToolboxItem.this.isSelected());
			}
		});
		
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
