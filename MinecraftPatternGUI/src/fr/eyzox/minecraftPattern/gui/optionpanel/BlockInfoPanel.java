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
import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import fr.eyzox.minecraftPattern.gui.BlockInfos;
import fr.eyzox.minecraftPattern.gui.Core;
import fr.eyzox.minecraftPattern.gui.action.Action;
import fr.eyzox.minecraftPattern.gui.action.ActionModel;
import fr.eyzox.minecraftPattern.gui.toolbox.MCToolBoxItem;

@SuppressWarnings("serial")
public class BlockInfoPanel extends JPanel implements Observer{

	private JTextField id = new JTextField(), value;
	private JLabel name = new JLabel();
	private JCheckBox ignoreValue = new JCheckBox("Ignore value");
	private JPanel preview;
	
	private boolean frameOpenned = false;
	
	private BlockInfoModel blockInforModel;
	private ActionModel actionModel;
	
	public BlockInfoPanel(BlockInfoModel bm, ActionModel am) {
		this.blockInforModel = bm;
		this.actionModel = am;
		blockInforModel.addObserver(this);
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
				if(blockInforModel.getId() >= 0)
					g2d.drawImage(BlockInfos.getImage(blockInforModel.getId()), 50, 50, getWidth()-100, getHeight()-100, null);

			}
		};

		JLabel lbName = new JLabel("Name : "), lbID = new JLabel("Id : "), lbValue = new JLabel("Value : ");

		value = new JTextField() {
			@Override
			public void setEnabled(boolean b) {
				super.setEnabled(b);
			}
		};
		
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
					blockInforModel.setMetadata(convertMetaData());
				}

			}
		});

		value.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent arg0) {
				if(frameOpenned) return;
				blockInforModel.setMetadata(convertMetaData());

			}
		});

		id.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(frameOpenned) return;
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					blockInforModel.setId(convertID());
				}

			}
		});

		id.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent arg0) {
				if(frameOpenned) return;
				blockInforModel.setId(convertID());

			}
		});
	}

	private int convertMetaData() {
		try {
			return Integer.parseInt(value.getText());
		}catch(java.lang.NumberFormatException e) {
			frameOpenned = true;
			JOptionPane.showMessageDialog(Core.getCore(),
					"Value must be an whole number",
					"Parse error",
					JOptionPane.ERROR_MESSAGE);
			frameOpenned = false;
			return -1;
		}
	}
	
	private int convertID() {
		try {
			return Integer.parseInt(id.getText());
		}catch(java.lang.NumberFormatException ex) {
			frameOpenned = true;
			JOptionPane.showMessageDialog(Core.getCore(),
					"ID must be a whole number",
					"Parse error",
					JOptionPane.ERROR_MESSAGE);
			frameOpenned = false;
			return -1;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(blockInforModel.getId() < 0) {
			id.setText("");
			name.setText("????");
			id.setEnabled(false);
			ignoreValue.setEnabled(false);
			value.setEnabled(false);
		}else {
			id.setText(""+blockInforModel.getId());
			name.setText(BlockInfos.getNameOf(blockInforModel.getId()));
			ignoreValue.setEnabled(true);
			if(blockInforModel.getMetadata() >= 0 ) {
				ignoreValue.setSelected(false);
				value.setText(""+blockInforModel.getMetadata());
			}else {
				value.setText("");
				ignoreValue.setSelected(true);
			}
			id.setEnabled(actionModel.getAction() == Action.SELECT);
		}
		
		preview.repaint();
	}
}
