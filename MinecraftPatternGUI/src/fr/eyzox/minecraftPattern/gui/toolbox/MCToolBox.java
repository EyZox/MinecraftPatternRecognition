package fr.eyzox.minecraftPattern.gui.toolbox;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import fr.eyzox.minecraftPattern.gui.action.Action;
import fr.eyzox.minecraftPattern.gui.action.ActionModel;
import fr.eyzox.minecraftPattern.gui.optionpanel.BlockInfoModel;

@SuppressWarnings("serial")
public class MCToolBox extends JPanel {

	private JScrollPane scrollPanel;
	private JPanel panel;
	private int scrollbarSize;
	private ButtonGroup group;
	private List<MCToolBoxItem> items = new ArrayList<MCToolBoxItem>();
	private MCToolBoxItem selectedItem;

	private BlockInfoModel model;
	private ActionModel actionModel;

	public MCToolBox(BlockInfoModel model, ActionModel actionModel) {
		this.model = model;
		this.actionModel = actionModel;
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

	public void refreshItems() {
		panel.removeAll();
		final int VGAP = 5;
		int availableLine = (scrollPanel.getViewport().getSize().height)/(MCToolBoxItem.preferredSize+(VGAP*2))         ;
		if(availableLine <= 0 ) availableLine = 1;
		int nbItemPerLine = BlockInfos.get().size()/availableLine;

		int i = 0;
		JPanel subPanel = null;
		group = new ButtonGroup();
		for(final MCToolBoxItem item : items){
			if(i == 0) {
				subPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
				panel.add(subPanel);
			}
			i++;
			subPanel.add(item);
			group.add(item);
			item.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent arg0) {
					if(item.isSelected()) {
						selectedItem = item;
						if( actionModel.getAction() != Action.SELECT) model.setProperties(item.getId(), -1);
					}
				}
			});

			if(i == nbItemPerLine+1) {
				i = 0;
			}
		}

		if(selectedItem == null) group.getElements().nextElement().setSelected(true);

	}

	public MCToolBoxItem getSelectedItem() {
		return selectedItem;
	}

	public BlockInfoModel getModel() {
		return model;
	}
}
