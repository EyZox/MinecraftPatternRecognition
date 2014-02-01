package fr.eyzox.minecraftPattern.gui.toolbox;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fr.eyzox.minecraftPattern.gui.BlockInfos;
import fr.eyzox.minecraftPattern.gui.Core;
import fr.eyzox.minecraftPattern.pattern.MCPatternBlock2D;

@SuppressWarnings("serial")
public class MCToolBox extends JPanel {

	private int selectedBlock = -1;
	private JScrollPane scrollPanel;
	private JPanel panel;
	private int scrollbarSize;
	private ButtonGroup group;
	private List<MCToolBoxItem> items = new ArrayList<MCToolBoxItem>();
	
	private MCPatternBlock2D block;

	public MCToolBox() {
		this.setLayout(new GridLayout(1,0));
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		scrollPanel = new JScrollPane(panel);
		this.add(scrollPanel);
		scrollbarSize = scrollPanel.getVerticalScrollBar().getPreferredSize().width;
		scrollPanel.setPreferredSize(new Dimension(scrollPanel.getPreferredSize().width,scrollPanel.getPreferredSize().height+scrollbarSize ));
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				refreshItems();
				
			}
		});
		
		for(Integer id : BlockInfos.get().keySet()){
			items.add(new MCToolBoxItem(id));
		}
		
	}

	public int getSelectedBlock() {
		return selectedBlock;
	}

	public void setSelectedBlock(int selectedBlock) {
		this.selectedBlock = selectedBlock;
		block.setID(selectedBlock);
		block.setDataValue(-1);
		Core.getOptionPanel().getBlockInfoPanel().update(block,true);
	}
	
	public void refreshItems() {
		ButtonModel selectedButton = null;
		if(group != null) selectedButton = group.getSelection();
		panel.removeAll();
		final int VGAP = 5;
		int availableLine = (scrollPanel.getViewport().getSize().height)/(MCToolBoxItem.preferredSize+(VGAP*2))         ;
		if(availableLine <= 0 ) availableLine = 1;
		int nbItemPerLine = BlockInfos.get().size()/availableLine;
		
		int i = 0;
		JPanel subPanel = null;
		group = new ButtonGroup();
		for(final MCToolBoxItem block : items){
			if(i == 0) {
				subPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
				panel.add(subPanel);
			}
			i++;
			subPanel.add(block);
			group.add(block);
			block.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					setSelectedBlock(block.getId());

				}
			});
			if(i == nbItemPerLine+1) {
				i = 0;
			}
		}
		
		if(block == null) {
			AbstractButton button = group.getElements().nextElement();
			button.setSelected(true);
			block = new MCPatternBlock2D(((MCToolBoxItem)(button)).getId(), 0, 0);
			setSelectedBlock(block.getID());
		}
		
		if(selectedButton != null) selectedButton.setSelected(true);

	}
}
