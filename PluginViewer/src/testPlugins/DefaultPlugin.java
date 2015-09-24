package testPlugins;
import interfaces.PluginInterface;
import interfaces.iStatusReceiver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class DefaultPlugin implements PluginInterface {

	private JPanel panel;
	private iStatusReceiver r;
	public DefaultPlugin() {
		panel = new JPanel();
		JButton test = new JButton("Press me");
		test.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				r.receiveMessage("Button was pressed");				
			}
			
		});
		panel.add(test);
		panel.validate();
	}
	
	@Override
	public void addReceiver(iStatusReceiver receiver) {
		r = receiver;
	}

	@Override
	public JPanel getPanel() {
		return panel;
	}
}
