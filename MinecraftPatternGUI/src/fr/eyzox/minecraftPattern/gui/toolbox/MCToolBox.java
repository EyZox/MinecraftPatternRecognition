package fr.eyzox.minecraftPattern.gui.toolbox;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fr.eyzox.minecraftPattern.gui.BlockInfos;
import fr.eyzox.minecraftPattern.gui.MCPatternModel;
import fr.eyzox.minecraftPattern.gui.action.Action;

@SuppressWarnings("serial")
public class MCToolBox extends JPanel {

	private JScrollPane scrollPanel;
	private JPanel panel;
	private int scrollbarSize;
	private ButtonGroup group;
	private List<MCToolBoxItem> items = new ArrayList<MCToolBoxItem>();
	private MCToolBoxItem selectedItem;

	private MCPatternModel model;

	public MCToolBox(MCPatternModel model) {
		this.model = model;
		this.model.getView().getActionModel().setToolbox(this);
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

		group = new ButtonGroup();
		for(Integer id : BlockInfos.get().keySet()){
			final MCToolBoxItem item = new MCToolBoxItem(id);
			group.add(item);
			items.add(item);
			item.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent arg0) {
					if(item.isSelected()) {
						selectedItem = item;
						MCToolBox.this.model.getBlockInfoModel().setId(item.getId());
						if(MCToolBox.this.model.getView().getActionModel().getAction() == Action.SELECT) {
							Point selection = MCToolBox.this.model.getView().getSelectionModel().getSelection();
							if( selection != null) {
								MCToolBox.this.model.getPattern().getBlock(selection.x, MCToolBox.this.model.getLevel().getLevel(), selection.y).setId(item.getId());
								MCToolBox.this.model.getPattern().setChanged();
								MCToolBox.this.model.getPattern().notifyObservers();
							}
						}
					}
				}
			});
		}
		group.getElements().nextElement().setSelected(true);

	}

	public void refreshItems() {
		panel.removeAll();
		final int VGAP = 5;
		int availableLine = (scrollPanel.getViewport().getSize().height)/(MCToolBoxItem.preferredSize+(VGAP*2))         ;
		if(availableLine <= 0 ) availableLine = 1;
		int nbItemPerLine = BlockInfos.get().size()/availableLine;

		int i = 0;
		JPanel subPanel = null;
		for(final MCToolBoxItem item : items){
			if(i == 0) {
				subPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
				panel.add(subPanel);
			}
			i++;
			subPanel.add(item);
			if(i == nbItemPerLine+1) i = 0;
		}

	}
	
	public MCToolBoxItem getSelectedItem() {
		return selectedItem;
	}
}
