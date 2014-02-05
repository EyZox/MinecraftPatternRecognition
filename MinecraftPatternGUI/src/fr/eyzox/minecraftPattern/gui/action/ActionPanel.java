package fr.eyzox.minecraftPattern.gui.action;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class ActionPanel extends JPanel implements Observer{

	private class JActionRadioButton extends JRadioButton {
		private Action action;
		
		public JActionRadioButton(Action action) {
			this.action = action;
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
		
		actionMap.get(Action.SELECT).setText("cursor");
		actionMap.get(Action.ADDorREMOVE).setText("+/-");
		
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
	}

	@Override
	public void update(Observable o, Object arg) {
		actionMap.get(model.getAction()).setSelected(true);
		
	}
}
