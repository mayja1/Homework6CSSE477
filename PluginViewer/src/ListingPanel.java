import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class ListingPanel extends JPanel {

	private DefaultListModel<String> listModel;
	private JList<String> listbox;
	private PluginPanel pluginPanel;
	private HashMap<String, JPanel> panelMap;
	private String activePlugin;
	
	public ListingPanel(final PluginPanel pluginPanel) {
		this.setLayout( new BorderLayout() );
		this.setPreferredSize(new Dimension(100, 100));
		this.setBorder(new LineBorder(Color.BLACK));
		
		this.pluginPanel = pluginPanel;
		this.panelMap = new HashMap<>();
		listModel = new DefaultListModel<String>();
		listbox = new JList<String>( listModel );
		activePlugin = "";
		listbox.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				String name = listbox.getSelectedValue();
				pluginPanel.setDisplayPanel(panelMap.get(name));
				activePlugin = name;
			}
			
		});
		this.add( listbox, BorderLayout.CENTER );
		this.add(new JLabel("Plugin List"), BorderLayout.NORTH);
	}
	
	public void addPlugin(String name, JPanel panel) {
		if(!panelMap.containsKey(name)){
			this.panelMap.put(name, panel);
			this.listModel.addElement(name);
			this.validate();
			this.repaint();
		}
	}
	
	public void removePlugin(String name) {
		if(activePlugin == name) {
			listbox.setSelectedIndex(-1);
		}
		listModel.removeElement(name);
	}
}
