package fr.eyzox.minecraftPattern.gui.menu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import fr.eyzox.minecraftPattern.gui.Core;

@SuppressWarnings("serial")
public class ColorFrame extends JFrame {
	private JColorChooser chooser;
	
	public enum To {
		AXIS,GRID;
	}
	
	public ColorFrame(final To action) {
		super("Selection de couleurs");
		/*Ecran de la selection de couleur */
		switch(action) {
		case AXIS:
			chooser = new JColorChooser(Core.getView().getAXES_COLOR());
			break;
		case GRID:
			chooser = new JColorChooser(Core.getView().getGRID_COLOR());
			break;
		default:
			break;
		
		}
		chooser.getPreviewPanel().getParent().setVisible(false);
		chooser.setBorder(new TitledBorder("Couleur du trait"));

		JPanel colorPanel = new JPanel(new GridLayout(1,0));
		colorPanel.add(chooser);
		
		/*Buttons*/
		JButton ok = new JButton("Ok");
		//JButton reset = new JButton("Reset");
		JButton annuler = new JButton("Cancel");
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(ok); /*buttonPanel.add(reset);*/ buttonPanel.add(annuler);
		
		this.getContentPane().add(colorPanel,BorderLayout.CENTER);
		this.getContentPane().add(buttonPanel,BorderLayout.SOUTH);
		
		
		/* EVENTS */
		
		annuler.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();

			}

		});
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				switch(action) {
				case AXIS:
					Core.getView().setAXES_COLOR(chooser.getColor());
					break;
				case GRID:
					Core.getView().setGRID_COLOR(chooser.getColor());
					break;
				default:
					break;
				
				}
				dispose();
			}

		});
		// Later
		/*reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.setStrokeColor(Color.BLACK);
				model.setFillColor(Color.WHITE);
				dispose();
			}
		});*/
		/*
		addComponentListener(new ComponentAdapter() {
			
			@Override
			public void componentShown(ComponentEvent arg0) {
				stroke.setColor(model.getStrokeColor());
				fill.setColor(model.getFillColor());
			}
		});
		*/
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
