import java.awt.Frame;

import javax.swing.JFrame;


public class PluginViewerApp {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		PluginPanel panel = new PluginPanel();
		frame.add(panel);
		frame.setJMenuBar(panel.getMenuBar());
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.validate();
	}
}
