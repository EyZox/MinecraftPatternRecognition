package fr.eyzox.minecraftPattern.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import fr.eyzox.minecraftPattern.gui.action.ActionPanel;
import fr.eyzox.minecraftPattern.gui.blockinfo.BlockInfoPanel;
import fr.eyzox.minecraftPattern.gui.config.Config;
import fr.eyzox.minecraftPattern.gui.level.LevelPanel;
import fr.eyzox.minecraftPattern.gui.menu.MenuBar;
import fr.eyzox.minecraftPattern.gui.toolbox.Toolbox;
import fr.eyzox.minecraftPattern.gui.view.View;


@SuppressWarnings("serial")
public class Core extends JFrame {

	public static final String version = " Alpha 0.1";
	private static Properties properties;

	public Core() {
		super("Minecraft Pattern GUI");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		/***************************************************************/
		/*                      LOAD CONFIG                            */
		/***************************************************************/

		Config conf = new Config();
		properties = conf.loadConfig();


		/***************************************************************/
		/*                CREATE MODELS AND COMPONENTS                 */
		/***************************************************************/

		BlockEditionModels models = new BlockEditionModels();

		final PatternModel patternModel = new PatternModel(models.getBdd());
		Toolbox toolbox = new Toolbox(models);
		View view = new View(models);
		LevelPanel levelPanel = null;
		try {
			levelPanel = new LevelPanel(models.getLevelModel());
		} catch (IOException e) {
			System.err.println("Unable to create the LevelPanel");
			e.printStackTrace();
		}
		BlockInfoPanel blockInfoPanel = new BlockInfoPanel(models);
		ActionPanel actionPanel = new ActionPanel(models.getActionModel());

		MenuBar menuBar = new MenuBar(patternModel, view, models.getLevelModel()); 

		/***************************************************************/
		/*                    PREPARE COMPONENTS                       */
		/***************************************************************/

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		if(levelPanel != null) rightPanel.add(levelPanel);
		rightPanel.add(blockInfoPanel);

		JPanel southPanel = new JPanel(new BorderLayout());
		southPanel.add(actionPanel, BorderLayout.NORTH);
		southPanel.add(toolbox, BorderLayout.CENTER);

		/****************************************************************/
		/*                        PLACE COMPONENTS                      */
		/****************************************************************/


		JSplitPane splitPaneInfoView = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,view, new JScrollPane(rightPanel));
		splitPaneInfoView.setOneTouchExpandable(true);
		splitPaneInfoView.setContinuousLayout(true);

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,splitPaneInfoView, southPanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setContinuousLayout(true);

		getContentPane().add(splitPane, BorderLayout.CENTER);

		setJMenuBar(menuBar);

		/****************************************************************/
		/*                           SET SIZE                           */
		/****************************************************************/

		// Set the size of app
		int width = Integer.parseInt(conf.getDefaultValue("resolution-x"));
		int height = Integer.parseInt(conf.getDefaultValue("resolution-y")); 
		try {
			width = Integer.parseInt(properties.getProperty("resolution-x"));
		}catch(java.lang.NumberFormatException nfe) {
			properties.setProperty("resolution-x", ""+width);
			Config.saveProp(properties);
		}
		try {
			height = Integer.parseInt(properties.getProperty("resolution-y"));
		}catch(java.lang.NumberFormatException nfe) {
			properties.setProperty("resolution-y", ""+height);
			Config.saveProp(properties);
		}

		this.setPreferredSize(new Dimension(width,height));
		pack();
		splitPaneInfoView.setDividerLocation(0.75);
		splitPane.setDividerLocation(0.75);


		/*-------------------------------------------------------------------------*/

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(patternModel.close()) System.exit(0);
			}
		});

		patternModel.setSaved(true);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] agrs) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			System.err.println(e.getLocalizedMessage()+"\n"+e.getCause());
		}
		new Core();
	}


	public static Properties getProperties() {
		return properties;
	}
}
