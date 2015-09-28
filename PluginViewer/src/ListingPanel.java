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
	private String active_plugin = "";
	
	public ListingPanel(final PluginPanel pluginPanel) {
		this.setLayout( new BorderLayout() );
		this.setPreferredSize(new Dimension(100, 100));
		this.setBorder(new LineBorder(Color.BLACK));
		
		this.pluginPanel = pluginPanel;
		this.panelMap = new HashMap<>();
		listModel = new DefaultListModel<String>();
		listbox = new JList<String>( listModel );
		listbox.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				String name = listbox.getSelectedValue();
				pluginPanel.setDisplayPanel(panelMap.get(name));
				active_plugin = name;
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
	public Set<String> getPlugins(){
		return this.panelMap.keySet();
	}
	public void removePlugin(String name){
		this.panelMap.remove(name);
		this.listModel.removeElement(name);
		try {
			Files.delete((new File("src/Temp_Plugins/" + name + ".jar")).toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(this.active_plugin == name){
			this.listbox.setSelectedIndex(-1);
		}
	}
}
