package fr.eyzox.minecraftPattern.gui.optionpanel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import fr.eyzox.minecraftPattern.gui.Core;

@SuppressWarnings("serial")
public class LevelSelector extends JPanel {
	private JButton previous, next;
	private JTextField level;
	
	public LevelSelector() throws IOException {
		previous = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/data/previous.png"))));
		next = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/data/next.png"))));
		level = new JTextField("0");
		level.setPreferredSize(new Dimension(64, level.getPreferredSize().height));
		level.setHorizontalAlignment(SwingConstants.CENTER);
		previous.setBorder(null);next.setBorder(null);
		previous.setContentAreaFilled(false);next.setContentAreaFilled(false);
		
		this.setBorder(new TitledBorder("Level (Y)"));
		
		
		previous.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				previous();
			}
		});
		
		next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				next();
			}
		});
		
		level.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					int lvl = Core.getView().getLevel();
					try {
						lvl = Integer.parseInt(level.getText());
					}catch(java.lang.NumberFormatException except) {
						JOptionPane.showMessageDialog(null,"Invalid Format","Error",JOptionPane.ERROR_MESSAGE);
					}
					Core.getView().setLevel(lvl);
				}
				
			}
		});
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(previous);
		add(level);
		add(next);
	}
	
	public void previous() {
		Core.getView().setLevel(Core.getView().getLevel()-1);
		level.setText(""+Core.getView().getLevel());
	}
	
	public void next() {
		Core.getView().setLevel(Core.getView().getLevel()+1);
		level.setText(""+Core.getView().getLevel());
	}
	
	public int getLevel() {
		return Integer.parseInt(level.getText());
	}
}