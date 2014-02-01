package fr.eyzox.minecraftPattern.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import fr.eyzox.minecraftPattern.gui.Core;
import fr.eyzox.minecraftPattern.io.PtrnReader;
import fr.eyzox.minecraftPattern.pattern.MCPattern;

public class OpenHandler implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!Core.getModel().isSaved() && !Core.getMenu().askForSave()) return;
		final JFrame f = new JFrame("Select a pattern to import");
		final JFileChooser chooser = new JFileChooser(new File("").getAbsolutePath());
		chooser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				if(event.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
					f.dispose();
				}else if(event.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
					try {
						Core.getModel().setFile(chooser.getSelectedFile());
						Core.getModel().importPattern(new MCPattern(new PtrnReader(Core.getModel().getFile())),true);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(Core.getCore(),
							    "Could not open this file (Error : IOException)",
							    "Open file error",
							    JOptionPane.ERROR_MESSAGE);
					}
					f.dispose();
				}
				
			}
		});
		FileFilter filter = new FileNameExtensionFilter("Minecraft Pattern Files", "ptrn");
		chooser.addChoosableFileFilter(filter);
		chooser.setFileFilter(filter);
		f.getContentPane().add(chooser);
		f.pack();
		f.setLocationRelativeTo(Core.getCore());
		f.setVisible(true);
	}

}
