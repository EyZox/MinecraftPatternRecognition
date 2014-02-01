package fr.eyzox.minecraftPattern.gui;


import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class AboutFrame extends JFrame {

	public AboutFrame() {
		super("About");
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(new JLabel("Author : EyZox"));
		panel.add(new JLabel("version : "+Core.version));
		panel.add(new JLabel() {
			{
				setText("For more info : visit "+Config.site);
				addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						try {
							Desktop.getDesktop().browse(new URI(Config.site));
						} catch (IOException e1) {
							new FatalErrorPanel(e1, false);
							e1.printStackTrace();
						} catch (URISyntaxException e1) {
							new FatalErrorPanel(e1, false);
							e1.printStackTrace();
						}
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						setCursor(new Cursor(Cursor.HAND_CURSOR));
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}
				});
			}
		});

		getContentPane().add(new JScrollPane(panel));
		pack();
		setLocationRelativeTo(Core.getCore());
		setVisible(true);
	}
	
}
