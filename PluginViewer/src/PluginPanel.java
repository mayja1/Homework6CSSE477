import interfaces.PluginInterface;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
		this.add(new JScrollPane(statusPanel), BorderLayout.SOUTH);
		this.add(displayPanel, BorderLayout.CENTER);

		// DefaultPlugin testPlugin = new DefaultPlugin();
		// testPlugin.addReceiver(statusPanel);
		// listingPanel.addPlugin("Test", testPlugin.getPanel());
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(1000);
						getJarFiles();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		t.start();
	}

	public void setDisplayPanel(JPanel dPanel) {
		//System.out.println("Chaning display");
		this.remove(displayPanel);
		this.displayPanel = dPanel;
		this.add(displayPanel, BorderLayout.CENTER);
		this.validate();
		this.repaint();
	}

	public void getJarFiles() throws IOException {
		File file = new File("src/Plugins/");
		String[] files = file.list();
		Set<String> ss = this.listingPanel.getPlugins();
		List<String> because_Reasons = new ArrayList<String>();
		because_Reasons.addAll(ss);
		for (String file_name : files) {
			file = new File("src/Plugins/" + file_name);
			String classname = file_name.substring(0,file_name.length() - 4);
			if (because_Reasons.contains(classname)) {
				because_Reasons.remove(classname);
				continue;
			}
			URL[] urls = new URL[] { file.toURI().toURL() };
			URLClassLoader cl = new URLClassLoader(urls);
			try {
				Object plugin = cl.loadClass(classname).newInstance();
				PluginInterface pi = (PluginInterface) plugin;
				pi.addReceiver(this.statusPanel);
				this.listingPanel.addPlugin(classname, pi.getPanel());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cl.close();
		}
		for (String name : because_Reasons) {
			this.listingPanel.removePlugin(name);
		}
	}
}
