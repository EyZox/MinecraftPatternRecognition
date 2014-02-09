package fr.eyzox.minecraftPattern.gui.blockinfo;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import fr.eyzox.minecraftPattern.gui.BlockEditionModels;
import fr.eyzox.minecraftPattern.gui.action.Action;
import fr.eyzox.minecraftPattern.gui.config.BlockInfos;
import fr.eyzox.minecraftPattern.gui.toolbox.ToolboxItem;

@SuppressWarnings("serial")
public class BlockInfoPanel extends JPanel implements Observer{

	private JTextField id = new JTextField(), value = new JTextField();
	private JLabel name = new JLabel();
	private JCheckBox ignoreValue = new JCheckBox("Ignore value");
	private JPanel preview;

	private boolean frameOpenned = false;

	private BlockEditionModels models;

	public BlockInfoPanel(BlockEditionModels m) {
		this.models = m;
		models.getBlockInfoModel().addObserver(this);

		setBorder(new TitledBorder("Block information"));

		// Declaration des composants
		preview = new JPanel() {
			{
				setPreferredSize(new Dimension(ToolboxItem.preferredSize*2+100, ToolboxItem.preferredSize*2+100));

			}
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				Graphics2D g2d = (Graphics2D) g;
				if(models.getBlockInfoModel().getId() == -2) {
					g2d.drawImage(BlockInfos.getMULTI(), 50, 50, getWidth()-100, getHeight()-100, null);
				}else if(models.getBlockInfoModel().getId() >= 0)
					g2d.drawImage(BlockInfos.getImage(models.getBlockInfoModel().getId()), 50, 50, getWidth()-100, getHeight()-100, null);
			}
		};

		JLabel lbName = new JLabel("Name : "), lbID = new JLabel("Id : "), lbValue = new JLabel("Value : ");

		//Placement des composants
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; c.gridwidth = GridBagConstraints.REMAINDER; c.weightx = 1; 
		add(preview,c);
		c.gridy++; c.gridwidth = 1; c.weightx = 0;c.fill = GridBagConstraints.HORIZONTAL;
		add(lbName,c);
		c.gridy++;
		add(lbID,c);
		c.gridy++;
		add(lbValue,c);
		c.gridy++; c.gridwidth = GridBagConstraints.REMAINDER; c.weightx = 1;
		add(ignoreValue,c);
		c.gridy = 1; c.gridx++;
		add(name,c);
		c.gridy++;
		add(id,c);
		c.gridy++;
		add(value,c);

		//Event
		ignoreValue.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(frameOpenned) return;
				value.setEnabled(!ignoreValue.isSelected());
			}
		});

		value.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(frameOpenned) return;
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					updateMetaData();
				}

			}
		});

		value.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent arg0) {
				if(frameOpenned) return;
				updateMetaData();

			}
		});

		id.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(frameOpenned) return;
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					updateID();
				}

			}
		});

		id.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent arg0) {
				if(frameOpenned) return;
				updateID();

			}
		});
	}

	private int convert(String str) {
		try {
			return Integer.parseInt(str);
		}catch(java.lang.NumberFormatException e) {
			frameOpenned = true;
			JOptionPane.showMessageDialog(this,
					"This value must be an whole number",
					"Parse error",
					JOptionPane.ERROR_MESSAGE);
			frameOpenned = false;
			return -1;
		}
	}

	private void updateID() {
		int id = convert(this.id.getText());
		models.getBlockInfoModel().setId(id);
		if(id >= 0 && models.getActionModel().getAction() == Action.SELECT ) {
			for(Point selection : models.getSelectionModel().getSelection()) {
				models.getBdd().getBlock(selection.x, models.getLevelModel().getLevel(), selection.y).setId(id);
			}
			models.getBdd().fireUpdate();
		}
	}

	private void updateMetaData() {
		int metadata = convert(this.value.getText());
		models.getBlockInfoModel().setMetadata(metadata);
		if(models.getActionModel().getAction() == Action.SELECT ) {
			for(Point selection : models.getSelectionModel().getSelection()) {
				models.getBdd().getBlock(selection.x, models.getLevelModel().getLevel(), selection.y).setMetadata(metadata);
			}
			models.getBdd().fireUpdate();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(models.getBlockInfoModel().getId() < 0) {
			id.setText("");
			if(models.getBlockInfoModel().getId() == -2) {
				name.setText("multi-selection");
				id.setEnabled(true);
				ignoreValue.setEnabled(true);
				if(models.getBlockInfoModel().getMetadata() >= 0) {
					value.setText(""+models.getBlockInfoModel().getMetadata());
					ignoreValue.setSelected(false);
				}else ignoreValue.setSelected(true);
			}else {
				name.setText("????");
				id.setEnabled(false);
				ignoreValue.setEnabled(false);
				value.setEnabled(false);
			}
		}else {
			id.setText(""+models.getBlockInfoModel().getId());
			name.setText(BlockInfos.getNameOf(models.getBlockInfoModel().getId()));
			ignoreValue.setEnabled(true);
			if(models.getBlockInfoModel().getMetadata() >= 0 ) {
				ignoreValue.setSelected(false);
				value.setText(""+models.getBlockInfoModel().getMetadata());
			}else {
				value.setText("");
				ignoreValue.setSelected(true);
			}
			id.setEnabled(models.getActionModel().getAction() == Action.SELECT);
		}

		preview.repaint();
	}
}
