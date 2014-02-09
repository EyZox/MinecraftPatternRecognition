package fr.eyzox.minecraftPattern.gui.menu.jmenu.view.color;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import fr.eyzox.minecraftPattern.gui.view.View;

@SuppressWarnings("serial")
public class ColorSubMenu extends JMenu {
	private View view;
	private JMenuItem axisColor, gridColor, selectionColor;
	
	public ColorSubMenu(View view) {
		super("Color");
		
		axisColor = new JMenuItem("Axis");
		gridColor = new JMenuItem("Grid");
		selectionColor = new JMenuItem("Selection");
		
		addMenuItems();
		addListeners();
		
		this.view = view;
		
	}

	private void addMenuItems() {
		add(axisColor);
		add(gridColor);
		add(selectionColor);
	}

	private void addListeners() {
		axisColor.addActionListener(new AxisColorHandler());
		gridColor.addActionListener(new GridColorHandler());
		selectionColor.addActionListener(new SelectionColorHandler());
	}
	
	private class AxisColorHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			new ColorFrame(new IColorAction() {
				@Override
				public void setColor(Color c) {
					view.setAXES_COLOR(c);
				}

				@Override
				public Color getColor() {
					return view.getAXES_COLOR();
				}
			});
		}
	}

	private class GridColorHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			new ColorFrame(new IColorAction() {
				@Override
				public void setColor(Color c) {
					view.setGRID_COLOR(c);
				}

				@Override
				public Color getColor() {
					return view.getGRID_COLOR();
				}
			});
		}
	}

	private class SelectionColorHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			new ColorFrame(new IColorAction() {
				@Override
				public void setColor(Color c) {
					view.setSELECTION_COLOR(c);
				}

				@Override
				public Color getColor() {
					return view.getSELECTION_COLOR();
				}
			});
		}
	}
}
