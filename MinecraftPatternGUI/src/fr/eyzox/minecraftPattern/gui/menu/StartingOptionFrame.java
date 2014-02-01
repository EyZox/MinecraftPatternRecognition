package fr.eyzox.minecraftPattern.gui.menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import fr.eyzox.minecraftPattern.gui.Config;
import fr.eyzox.minecraftPattern.gui.Core;

@SuppressWarnings("serial")
public class StartingOptionFrame extends JFrame {
	
	private Map<String, JTextField> valueMap = new TreeMap<String, JTextField>();
	private JButton ok, cancel;
	private Config conf = new Config();
	
	public StartingOptionFrame() {
		super("Config");
		final Properties p = Core.getProperties();
		
		ok = new JButton("ok"); cancel = new JButton("cancel");
		
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(String key : valueMap.keySet()) {
					p.setProperty(key, valueMap.get(key).getText());
				}
				Config.saveProp(p);
				dispose();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				
			}
		});
		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0; c.fill = GridBagConstraints.BOTH; c.ipadx = 15; c.ipady = 5; c.insets = new Insets(5, 5, 5, 5);
		for(String key : conf.getDefaultValues().keySet()) {
			c.gridx = 0; c.gridwidth = 1; c.weightx = 0;
			panel.add(new JLabel(key),c);
			c.gridx = 1; c.gridwidth = GridBagConstraints.REMAINDER; c.weightx = 1;
			String value = p.getProperty(key);
			JTextField tf = value==null?new JTextField():new JTextField(value);
			panel.add(tf,c);
			valueMap.put(key, tf);
			c.gridy++;
		}
		
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(ok); buttonPanel.add(cancel);
		
		getContentPane().add(new JScrollPane(panel), BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		setMinimumSize(new Dimension(300,0));
		pack();
		setLocationRelativeTo(Core.getCore());
		setVisible(true);
	}
}
