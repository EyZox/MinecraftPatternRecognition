package fr.eyzox.minecraftPattern.gui.level;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class LevelPanel extends JPanel implements Observer{
	private JButton previous, next;
	private JTextField level;
	
	private LevelModel levelModel;
	
	public LevelPanel(LevelModel l) {
		levelModel = l;
		levelModel.addObserver(this);
		
		previous = new JButton("/data/previous.png");
		next = new JButton("/data/next.png");
		level = new JTextField("0");
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
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; c.fill = GridBagConstraints.HORIZONTAL;
		add(previous,c);
		c.gridx = 2;
		add(next,c);
		c.gridx = 1; c.weightx = 1;
		add(level,c);
		
		setIcons(next);
		setIcons(previous);
		
	}

	private void setIcons(JButton b) {
		try {
			b.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(b.getText()))));
			b.setText("");
		} catch (IOException e) {
			System.err.println("Unable to load "+b.getText());
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		level.setText(""+levelModel.getLevel());
	}
}