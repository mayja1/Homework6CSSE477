import interfaces.PluginInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import static java.nio.file.StandardCopyOption.*;

import javax.swing.JFrame;
public class PluginPanel extends JPanel implements ActionListener {


	private ListingPanel listingPanel;
	private StatusPanel statusPanel;
	private JPanel displayPanel;
	private JScrollPane scrollPanel;
	private JMenuBar menuBar;
	private JMenuItem uninstall;
	private JMenuItem install;
	public PluginPanel() {
		uninstall = new JMenuItem("Uninstall a plugin");
		install = new JMenuItem("Install a plugin");
		JMenu menu = new JMenu("File");
		menu.add(install);
		menu.addSeparator();
		menu.add(uninstall);
		menuBar = new JMenuBar();
		menuBar.add(menu);
		install.addActionListener(this);
		uninstall.addActionListener(this);
		
		listingPanel = new ListingPanel(this);
		statusPanel = new StatusPanel();
		displayPanel = new JPanel();
		this.setLayout(new BorderLayout());
		this.add(listingPanel, BorderLayout.WEST);
		scrollPanel = new JScrollPane(statusPanel);
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(scrollPanel, BorderLayout.SOUTH);
		this.add(displayPanel, BorderLayout.CENTER);
		try {
			getJarFiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setDisplayPanel(JPanel dPanel) {
		statusPanel.receiveMessage("Changing display");
		this.remove(displayPanel);
		if(dPanel == null) {
			dPanel = new JPanel();
		}
		this.displayPanel = dPanel;
		this.add(displayPanel, BorderLayout.CENTER);
		this.validate();
		this.repaint();
	}

	public void getJarFiles() throws IOException {
		File file = new File("src/Plugins/");
		String[] files = file.list();
		for (String file_name : files) {
			file = new File("src/Plugins/" + file_name);
			System.out.println(file_name);
			String classname = file_name.substring(0,file_name.length() - 4);
			URL[] urls = new URL[] { file.toURI().toURL() };
			URLClassLoader cl = new URLClassLoader(urls);
			try {
				Object plugin = cl.loadClass(classname).newInstance();
				PluginInterface pi = (PluginInterface) plugin;
				pi.addReceiver(this.statusPanel);
				this.listingPanel.addPlugin(file_name.substring(0,file_name.length()-4), pi.getPanel());
				cl.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cl.close();
		}
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser c = new JFileChooser("./src/Plugins/");
		int returnVal = c.showOpenDialog(this);
		if (e.getSource().equals(uninstall)) {
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = c.getSelectedFile();
				System.out.println(file.getName());
				this.listingPanel.removePlugin(file.getName().substring(0,
						file.getName().length() - 4));
				if (file.delete()) {
					System.out.println(file.getName() + " Was deleted!");
				} else {
					System.out.println("Delete Operation Failed. Check: "
							+ file);
				}
				// This is where a real application would open the file.
			}
		}
		
		if (e.getSource().equals(install)) {
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = c.getSelectedFile();
				try {
					File temp = new File("src/Plugins/"+file.getName());
					FileInputStream fis = new FileInputStream(file);
					FileOutputStream fos = new FileOutputStream(temp);
					int bytesRead;
					byte[] buf = new byte[1024];
					while((bytesRead = fis.read(buf)) > 0){
						fos.write(buf,0,bytesRead);
					}
					fis.close();
					fos.close();
				URL[] urls = new URL[] {file.toURI().toURL()};
				
				URLClassLoader cl = new URLClassLoader(urls);
				String file_name = file.getName();
				Object plugin = cl.loadClass(file_name.substring(0,file_name.length()-4)).newInstance();
				
				PluginInterface pi = (PluginInterface) plugin;
				pi.addReceiver(this.statusPanel);
				this.listingPanel.addPlugin(file_name.substring(0,file_name.length()-4), pi.getPanel());
				cl.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				// This is where a real application would open the file.
			}
		}
	}
}
