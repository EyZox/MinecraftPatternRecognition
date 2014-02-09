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

import fr.eyzox.minecraftPattern.gui.BlockEditionModels;
import fr.eyzox.minecraftPattern.gui.action.Action;
import fr.eyzox.minecraftPattern.gui.config.BlockInfos;

@SuppressWarnings("serial")
public class Toolbox extends JPanel {

	private JScrollPane scrollPanel;
	private JPanel panel;
	private int scrollbarSize;
	private ButtonGroup group;
	private List<ToolboxItem> items = new ArrayList<ToolboxItem>();
	private ToolboxItem selectedItem;


	public Toolbox(final BlockEditionModels models) {
		models.getActionModel().setToolbox(this);
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
			final ToolboxItem item = new ToolboxItem(id);
			group.add(item);
			items.add(item);
			item.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent arg0) {
					if(item.isSelected()) {
						selectedItem = item;
						models.getBlockInfoModel().setId(item.getId());
						if(models.getActionModel().getAction() == Action.SELECT) {
							for(Point selection : models.getSelectionModel().getSelection()) {
								models.getBdd().getBlock(selection.x, models.getLevelModel().getLevel(), selection.y).setId(item.getId());
							}
							models.getBdd().setChanged();
							models.getBdd().notifyObservers();
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
		int availableLine = (scrollPanel.getViewport().getSize().height)/(ToolboxItem.preferredSize+(VGAP*2))         ;
		if(availableLine <= 0 ) availableLine = 1;
		int nbItemPerLine = BlockInfos.get().size()/availableLine;

		int i = 0;
		JPanel subPanel = null;
		for(final ToolboxItem item : items){
			if(i == 0) {
				subPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
				panel.add(subPanel);
			}
			i++;
			subPanel.add(item);
			if(i == nbItemPerLine+1) i = 0;
		}

	}

	public ToolboxItem getSelectedItem() {
		return selectedItem;
	}
}
