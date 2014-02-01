package fr.eyzox.minecraftPattern.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import fr.eyzox.minecraftPattern.gui.AboutFrame;
import fr.eyzox.minecraftPattern.gui.Core;
import fr.eyzox.minecraftPattern.io.MCPatternWriter;
import fr.eyzox.minecraftPattern.io.PtrnWriter;

@SuppressWarnings("serial")
public class MCPGUIMenuBar extends JMenuBar {

	private static boolean saveSucessfull;
	private JMenu file = new JMenu("File"), view = new JMenu("View"), help = new JMenu("Help");

	/* file */
	private JMenuItem open = new JMenuItem("Open ..."), saveAs = new JMenuItem("Save as ..."), save = new JMenuItem("Save"), exit = new JMenuItem("Exit");
	private JMenuItem newPattern = new JMenuItem("New"), option = new JMenuItem("starting option");
	/* view */
	private JMenuItem showGrid = new JMenuItem("Show Grid"), showAxes = new JMenuItem("Show Axes");
	private JMenuItem axisColor = new JMenuItem("Set Axis color"), gridColor = new JMenuItem("Set grid color");
	private JMenuItem previousLevel = new JMenuItem("Previous level"), nextLevel = new JMenuItem("Next level");

	/*Help*/
	private JMenuItem about = new JMenuItem("About MinecraftPattern Gui ...");

	public MCPGUIMenuBar() {
		/* file */
		file.add(newPattern);
		file.add(open);
		file.add(saveAs);
		file.add(save);
		file.addSeparator();
		file.add(option);
		file.addSeparator();
		file.add(exit);

		/*view*/
		view.add(showAxes);
		view.add(showGrid);
		view.addSeparator();
		view.add(axisColor);
		view.add(gridColor);
		view.addSeparator();
		view.add(previousLevel);
		view.add(nextLevel);

		/*help*/
		help.add(about);

		/*this*/
		add(file);
		add(view);
		add(help);

		/*EVENT*/
		/*file*/
		newPattern.addActionListener(new NewPatternHandler());
		open.addActionListener(new OpenHandler());
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				save();

			}
		});
		saveAs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				save();

			}
		});
		
		option.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new StartingOptionFrame();
				
			}
		});
		
		exit.addActionListener(new ExitHandler());
		/*view*/
		showAxes.setSelected(Core.getView().isSHOW_AXES());
		showAxes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Core.getView().setSHOW_AXES(!Core.getView().isSHOW_AXES());
				showAxes.setSelected(Core.getView().isSHOW_AXES());
			}
		});

		showGrid.setSelected(Core.getView().isSHOW_GRID());
		showGrid.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Core.getView().setSHOW_GRID(!Core.getView().isSHOW_GRID());
				showGrid.setSelected(Core.getView().isSHOW_GRID());
			}
		});
		
		axisColor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ColorFrame(ColorFrame.To.AXIS);
			}
		});
		
		gridColor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ColorFrame(ColorFrame.To.GRID);
			}
		});
		
		previousLevel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Core.getOptionPanel().getSelectLevel().previous();
			}
		});
		
		nextLevel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Core.getOptionPanel().getSelectLevel().next();
				
			}
		});
		
		/*help*/
		about.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new AboutFrame();
				
			}
		});
	}

	public boolean save() {
		return save(false);
	}

	private boolean save(boolean as) {
		if(as || Core.getModel().getFile() == null) {
			final JFrame f = new JFrame("Save pattern");
			final JFileChooser chooser = new JFileChooser(new File("").getAbsolutePath());
			chooser.setApproveButtonText("Save");
			chooser.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent event) {
					if(event.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
						saveSucessfull = false;
						f.dispose();
					}else if(event.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
						String name = chooser.getSelectedFile().getAbsolutePath();
						if(!name.endsWith(".ptrn")) {
							name += ".ptrn";
						}
						Core.getModel().setFile(new File(name));
						write();
						f.dispose();
					}

				}
			});
			f.getContentPane().add(chooser);
			f.pack();
			f.setLocationRelativeTo(Core.getCore());
			f.setVisible(true);
		}else {
			write();
		}

		return saveSucessfull;
	}

	private void write() {
		try {
			MCPatternWriter pw = new PtrnWriter(Core.getModel().getFile());
			pw.writePattern(Core.getModel().export());
			pw.close();
			saveSucessfull = true;
			Core.getModel().setSaved(true);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(Core.getCore(),
					"Could not save this file (Error : IOException)",
					"Open file error",
					JOptionPane.ERROR_MESSAGE);
			saveSucessfull = false;
		}
	}

	public boolean askForSave() {

		int save = JOptionPane.showConfirmDialog(null, "Do you want save before closing this pattern ?", 
				"Closing current pattern", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if(save == JOptionPane.YES_OPTION){
			return Core.getMenu().save() ;
		}
		return true;
	}
}
