package fr.eyzox.minecraftPattern.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import fr.eyzox.minecraftPattern.gui.bdd.Block;
import fr.eyzox.minecraftPattern.gui.bdd.BlockBDD;
import fr.eyzox.minecraftPattern.io.MCPatternWriter;
import fr.eyzox.minecraftPattern.io.PtrnReader;
import fr.eyzox.minecraftPattern.io.PtrnWriter;
import fr.eyzox.minecraftPattern.pattern.MCPattern;
import fr.eyzox.minecraftPattern.pattern.MCPatternBlock;

public class PatternModel implements Observer{

	private BlockBDD pattern;

	private boolean saved;
	private File file;

	public PatternModel(BlockBDD bdd) {
		this.pattern = bdd;		
		this.pattern.addObserver(this);
	}

	public void importPattern(MCPattern p, boolean forceNew){
		if(forceNew) {
			pattern.clear();
		}
		for(MCPatternBlock b : p.getPattern()) {
			pattern.add(b);
		}
	}

	public MCPattern export() {
		List<MCPatternBlock> res = new ArrayList<MCPatternBlock>();
		for(Integer levelY : pattern.getBlockMap().keySet()) {
			for(Integer levelZ : pattern.getZXMap(levelY).keySet()) {
				for(Integer levelX : pattern.getXMap(levelY, levelZ).keySet()) {
					Block b = pattern.getZXMap(levelY).get(levelZ).get(levelX);
					res.add(new MCPatternBlock(b.getId(), b.getMetaData(), levelX, levelY, levelZ));
				}
			}
		}
		return new MCPattern(res);
	}

	public boolean close() {
		if(!isSaved()) {
			int askForSave = JOptionPane.showConfirmDialog(null, "Do you want save before closing this pattern ?", 
					"Closing current pattern", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
			switch(askForSave) {
			case JOptionPane.YES_OPTION:
				do {
					save(false);
					if(!isSaved()) {
						int askForRetry = JOptionPane.showConfirmDialog(null, "There was an error during saving, do you wand try again ?", 
								"Closing current pattern", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
						if(askForRetry == JOptionPane.NO_OPTION) return true;
						if(askForRetry != JOptionPane.YES_OPTION) return false;
					}
				}while(!isSaved());
				break;
			case JOptionPane.NO_OPTION :
				return true;
			default :
				return false;
			}
		}
		return true;
	}

	public void save(boolean saveAs) {
		if(saveAs || file == null) {
			final JFrame f = new JFrame("Save pattern");
			final JFileChooser chooser = new JFileChooser(new File("").getAbsolutePath());
			FileFilter filter = new FileNameExtensionFilter("Minecraft Pattern Files", "ptrn");
			chooser.addChoosableFileFilter(filter);
			chooser.setFileFilter(filter);
			chooser.setApproveButtonText("Save");
			chooser.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent event) {
					if(event.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
						f.dispose();
					}else if(event.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
						String name = chooser.getSelectedFile().getAbsolutePath();
						if(!name.endsWith(".ptrn")) {
							name += ".ptrn";
						}
						setFile(new File(name));
						write();
						f.dispose();
					}

				}
			});
			f.getContentPane().add(chooser);
			f.pack();
			f.setLocationRelativeTo(null);
			f.setVisible(true);
		}else {
			write();
		}
	}
	
	public void open() {
		final JFrame f = new JFrame("Select a pattern to open");
		final JFileChooser chooser = new JFileChooser(new File("").getAbsolutePath());
		FileFilter filter = new FileNameExtensionFilter("Minecraft Pattern Files", "ptrn");
		chooser.addChoosableFileFilter(filter);
		chooser.setFileFilter(filter);
		chooser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				if(event.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
					f.dispose();
				}else if(event.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
					try {
						file = chooser.getSelectedFile();
						importPattern(new MCPattern(new PtrnReader(file)),true);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null,
							    "Could not open this file (Error : IOException)",
							    "Open file error",
							    JOptionPane.ERROR_MESSAGE);
					}
					f.dispose();
				}
				
			}
		});
		
		f.getContentPane().add(chooser);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private void write() {
		try {
			MCPatternWriter pw = new PtrnWriter(file);
			pw.writePattern(export());
			pw.close();
			saved = true;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Could not save this file (Error : IOException)",
					"Save file error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void clear() {
		file = null;
		pattern.clear();
		saved = true;
	}

	public BlockBDD getPattern() {
		return pattern;
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public void update(Observable o, Object arg) {
		saved = false;
	}
}
