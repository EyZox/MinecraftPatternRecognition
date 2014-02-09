package fr.eyzox.minecraftPattern.gui.menu.jmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import fr.eyzox.minecraftPattern.gui.level.LevelModel;
import fr.eyzox.minecraftPattern.gui.menu.ColorFrame;
import fr.eyzox.minecraftPattern.gui.view.View;

@SuppressWarnings("serial")
public class ViewMenu extends JMenu {
	public enum To {AXIS,GRID;}
	private View view;
	private LevelModel levelModel;
	private JMenuItem showGrid, showAxes, axisColor, gridColor, previousLevel, nextLevel;
	
	public ViewMenu(View view, LevelModel levelModel) {
		super("View");
		showGrid = new JCheckBoxMenuItem("Show Grid");
		showAxes = new JCheckBoxMenuItem("Show Axes");
		axisColor = new JMenuItem("Axis color");
		gridColor = new JMenuItem("Grid color");
		previousLevel = new JMenuItem("Previous level");
		nextLevel = new JMenuItem("Next level");
		
		showGrid.setSelected(view.isSHOW_GRID());
		showAxes.setSelected(view.isSHOW_AXES());
		
		this.view = view;
		this.levelModel = levelModel;
		
		addMenuItems();
		addListeners();
	}
	
	public void addMenuItems() {
		add(showAxes);
		add(showGrid);
		addSeparator();
		add(axisColor);
		add(gridColor);
		addSeparator();
		add(nextLevel);
		add(previousLevel);
	}
	
	private void addListeners() {
		showAxes.addActionListener(new ShowAxesHandler());
		showGrid.addActionListener(new ShowGridHandler());
		axisColor.addActionListener(new AxisColorHandler());
		gridColor.addActionListener(new GridColorHandler());
		nextLevel.addActionListener(new NextLevelHandler());
		previousLevel.addActionListener(new PreviousLevelHandler());
	}
	
	private class ShowAxesHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			view.setSHOW_AXES(showAxes.isSelected());
		}
	}
	
	private class ShowGridHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			view.setSHOW_GRID(showGrid.isSelected());
		}
	}
	
	private class AxisColorHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			new ColorFrame(To.AXIS, view);
		}
	}
	
	private class GridColorHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			new ColorFrame(To.GRID, view);
		}
	}
	
	private class NextLevelHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			levelModel.next();
		}
	}
	
	private class PreviousLevelHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			levelModel.previous();
		}
	}
}
