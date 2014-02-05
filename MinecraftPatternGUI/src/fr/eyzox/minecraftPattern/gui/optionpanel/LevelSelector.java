package fr.eyzox.minecraftPattern.gui.optionpanel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import fr.eyzox.minecraftPattern.gui.testbranch.Level;

@SuppressWarnings("serial")
public class LevelSelector extends JPanel implements Observer{
	private JButton previous, next;
	private JTextField level;
	
	private Level levelModel;
	
	public LevelSelector(Level l) throws IOException {
		levelModel = l;
		levelModel.addObserver(this);
		
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
				levelModel.previous();
			}
		});
		
		next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				levelModel.next();
			}
		});
		
		level.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					int lvl = levelModel.getLevel();
					try {
						lvl = Integer.parseInt(level.getText());
					}catch(java.lang.NumberFormatException except) {
						JOptionPane.showMessageDialog(null,"Invalid Format","Error",JOptionPane.ERROR_MESSAGE);
					}
					levelModel.setLevel(lvl);
				}
				
			}
		});
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(previous);
		add(level);
		add(next);
	}

	@Override
	public void update(Observable o, Object arg) {
		level.setText(""+levelModel.getLevel());
	}
}