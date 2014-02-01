package fr.eyzox.minecraftPattern.gui.optionpanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import fr.eyzox.minecraftPattern.gui.BlockInfos;
import fr.eyzox.minecraftPattern.gui.Core;
import fr.eyzox.minecraftPattern.gui.toolbox.MCToolBoxItem;
import fr.eyzox.minecraftPattern.pattern.MCPatternBlock2D;

@SuppressWarnings("serial")
public class BlockInfoPanel extends JPanel{

	private JTextField id = new JTextField(), value = new JTextField();
	private JLabel name = new JLabel();
	private JCheckBox ignoreValue = new JCheckBox("Ignore value");
	private JPanel preview;

	private MCPatternBlock2D block;
	private boolean frameOpenned = false;

	public BlockInfoPanel() {
		setBorder(new TitledBorder("Block information"));
		// Declaration des composants
		preview = new JPanel() {
			{
				setPreferredSize(new Dimension(MCToolBoxItem.preferredSize*2+100, MCToolBoxItem.preferredSize*2+100));

			}
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				Graphics2D g2d = (Graphics2D) g;
				if(block!=null)
					g2d.drawImage(BlockInfos.getImage(block.getID()), 50, 50, getWidth()-100, getHeight()-100, null);

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
				if(ignoreValue.isSelected()) {
					block.setDataValue(-1);
					Core.getModel().fireUpdate();
				}else {
					setNewValue();
				}

			}
		});

		value.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(frameOpenned) return;
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					setNewValue();
				}

			}
		});

		value.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent arg0) {
				if(frameOpenned) return;
				setNewValue();

			}
		});

		id.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(frameOpenned) return;
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					setNewID();
				}

			}
		});

		id.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent arg0) {
				if(frameOpenned) return;
				setNewID();

			}
		});
	}

	private void setNewID() {
		try {
			block.setID(Integer.parseInt(id.getText()));
			Core.getModel().fireUpdate();
			preview.repaint();
		}catch(java.lang.NumberFormatException ex) {
			frameOpenned = true;
			JOptionPane.showMessageDialog(Core.getCore(),
					"ID must be a whole number",
					"Parse error",
					JOptionPane.ERROR_MESSAGE);
			frameOpenned = false;
		}
	}

	private void setNewValue() {
		if(value.getText().length() == 0) return;
		try {
			int i = Integer.parseInt(value.getText());
			block.setDataValue(i);
			ignoreValue.setSelected(false);
			Core.getModel().fireUpdate();
		}catch(java.lang.NumberFormatException e) {
			frameOpenned = true;
			JOptionPane.showMessageDialog(Core.getCore(),
					"Value must be a binary string of maximum 8 characteres which each could be only '1' or '0' ",
					"Parse error",
					JOptionPane.ERROR_MESSAGE);
			frameOpenned = false;
			value.setText("");
		}
	}

	public void update(MCPatternBlock2D block, boolean readOnly) {
		if(block != null) value.setText(block.getDataValue()!=-1?(""+block.getDataValue()):"");
		if(block == null || block.getID() == -1) {
			this.block = null;
			name.setText("???");
			id.setText(""); id.setEnabled(false);
			ignoreValue.setEnabled(false);
			value.setEnabled(false);
		}else {
			this.block = block;
			ignoreValue.setEnabled(true);
			name.setText(BlockInfos.getNameOf(block.getID()));
			id.setText(""+block.getID());
			id.setEnabled(!readOnly);
			value.setEnabled(true);
			if(block.getDataValue() == -1) {
				ignoreValue.setSelected(true);
				value.setEnabled(false);
			}else {
				ignoreValue.setSelected(false);
			}
		}

		repaint();
	}

	public int getValue() {
		if(ignoreValue.isSelected()) return -1;
		try {
			return Integer.parseInt(value.getText());
		}catch(java.lang.NumberFormatException e) {
			frameOpenned = true;
			JOptionPane.showMessageDialog(Core.getCore(),
					"Value must be a binary string of maximum 8 characteres which each could be only '1' or '0' ",
					"Parse error",
					JOptionPane.ERROR_MESSAGE);
			frameOpenned = false;
			value.setText("");
			return -1;
		}
	}
}
