package fr.eyzox.minecraftPattern.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class FatalErrorPanel extends JFrame {
	public FatalErrorPanel(Exception e, final boolean mustExit) {
		super("Fatal Error");
		JTextArea area = new JTextArea();
		area.setEditable(false);
		area.setText("A fatal error occured, application must shut down.\n"
				+ "Error Message : "+ e.getMessage()+"\n\n"
						+ "Stack Trace : \n"+e.getStackTrace());
		JPanel panel = new JPanel();
		panel.add(area);
		
		JButton ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mustExit)System.exit(1);
				else dispose();
			}
		});
		
		getContentPane().add(new JScrollPane(panel));
		this.pack();
		this.setLocationRelativeTo(Core.getCore());
		this.setAlwaysOnTop(true);
		this.setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(mustExit)System.exit(1);
			}
		});
	}
}
