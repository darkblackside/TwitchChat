package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class OptionPane extends JTabbedPane
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 682581860013718025L;
	private Map<String, JComponent> tabbedComponents;
	
	public OptionPane()
	{
		tabbedComponents = new HashMap<String, JComponent>();
		this.setMinimumSize(new Dimension(400, 400));
		this.setPreferredSize(new Dimension(500, 600));
		this.setVisible(true);
	}
	
	public void addTabbedComponent(String s, JComponent newComponent)
	{
		tabbedComponents.put(s, newComponent);
		JPanel tmp = new JPanel(new FlowLayout());
		tmp.add(newComponent);
		this.addTab(s, tmp);
	}
	
	public JComponent getTabbedComponent(String s)
	{
		return tabbedComponents.get(s);
	}
	
	public void removeTabbedComponent(String s)
	{
		this.remove(this.getTabbedComponent(s));
		tabbedComponents.remove(s);
	}
}
