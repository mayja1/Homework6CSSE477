import interfaces.PluginInterface;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.JPanel;

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
		try {
			getJarFiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// DefaultPlugin testPlugin = new DefaultPlugin();
		// testPlugin.addReceiver(statusPanel);
		// listingPanel.addPlugin("Test", testPlugin.getPanel());
	}

	public void setDisplayPanel(JPanel dPanel) {
		System.out.println("Chaning display");
		this.remove(displayPanel);
		this.displayPanel = dPanel;
		this.add(displayPanel, BorderLayout.CENTER);
		this.validate();
	}

	public void getJarFiles() throws IOException {
		File file = new File("src/Plugins/");
		String[] files = file.list();
		for (String file_name : files) {
			file = new File("src/Plugins/" + file_name);
			URL[] urls = new URL[] {file.toURI().toURL()};
			URLClassLoader cl = new URLClassLoader(urls);
			try {
				System.out.println(file_name.substring(0,file_name.indexOf('.')));
				Object plugin = cl.loadClass(file_name.substring(0,file_name.length()-4)).newInstance();
				PluginInterface pi = (PluginInterface) plugin;
				pi.addReceiver(this.statusPanel);
				this.listingPanel.addPlugin(file_name.substring(0,file_name.length()-4), pi.getPanel());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
