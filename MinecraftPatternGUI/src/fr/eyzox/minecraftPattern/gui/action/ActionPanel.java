package fr.eyzox.minecraftPattern.gui.action;

import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

@SuppressWarnings("serial")
public class ActionPanel extends JPanel implements Observer{

	private class JActionRadioButton extends JToggleButton {
		private Action action;
		
		public JActionRadioButton(Action action) {
			this.action = action;
			this.setMargin(new Insets(0, 0, 0, 0));
		}
	}
	
	private ActionModel model;
	
	private Map<Action, JActionRadioButton> actionMap;
	private ButtonGroup actionGroup;
	
	public ActionPanel(ActionModel m) {
		this.model = m;
		this.model.addObserver(this);
		actionMap = new TreeMap<Action, JActionRadioButton>();
		this.setLayout(new FlowLayout());
		
		for(Action a : Action.values()) {
			actionMap.put(a, new JActionRadioButton(a));
		}
		
		actionMap.get(Action.SELECT).setText("/data/cursor.png");
		actionMap.get(Action.ADDorREMOVE).setText("/data/addorremove.png");
		
		actionGroup = new ButtonGroup();
		for(Action a : actionMap.keySet()) {
			actionGroup.add(actionMap.get(a));
			this.add(actionMap.get(a));
		}
		
		Enumeration<AbstractButton> buttons = actionGroup.getElements();
		for(int i = 0; i<actionGroup.getButtonCount(); i++) {
			final JActionRadioButton b = (JActionRadioButton) buttons.nextElement();
			b.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent arg0) {
					if(b.isSelected() && model.getAction() != b.action) model.setAction(b.action);
				}
			});
		}
		
		actionMap.get(Action.SELECT).setSelected(true);
		setIcons();
	}

	private void setIcons() {
		for(Action a : actionMap.keySet()) {
			JActionRadioButton b = actionMap.get(a);
			try {
				b.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(b.getText()))));
				b.setText("");
			} catch (IOException e) {
				System.err.println("Unable to load "+b.getText());
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		actionMap.get(model.getAction()).setSelected(true);
		
	}
}
