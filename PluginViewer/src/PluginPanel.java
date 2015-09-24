import java.awt.BorderLayout;

import javax.swing.JPanel;

import testPlugins.DefaultPlugin;


public class PluginPanel extends JPanel {

	private ListingPanel listingPanel;
	private StatusPanel statusPanel;
	private JPanel displayPanel;
	
	public PluginPanel() {
		listingPanel = new ListingPanel(this);
		statusPanel = new StatusPanel();
		displayPanel = new JPanel();
		this.setLayout(new BorderLayout());
		this.add(listingPanel, BorderLayout.WEST);
		this.add(statusPanel, BorderLayout.SOUTH);
		this.add(displayPanel, BorderLayout.CENTER);
		
		DefaultPlugin testPlugin = new DefaultPlugin();
		testPlugin.addReceiver(statusPanel);
		listingPanel.addPlugin("Test", testPlugin.getPanel());
	}
	
	public void setDisplayPanel(JPanel dPanel) {
		System.out.println("Chaning display");
		this.remove(displayPanel);
		this.displayPanel = dPanel;
		this.add(displayPanel, BorderLayout.CENTER);
		this.validate();
	}
}
