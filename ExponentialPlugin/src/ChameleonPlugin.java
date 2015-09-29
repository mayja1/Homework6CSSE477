

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicOptionPaneUI;

import interfaces.PluginInterface;
import interfaces.iStatusReceiver;

public class ChameleonPlugin implements PluginInterface {

	private JPanel panel;
	private iStatusReceiver r;
	
	public ChameleonPlugin() {
		panel = new JPanel();
//		LayoutManager lm = new BasicOptionPaneUI.ButtonAreaLayout(true, 5);
//		lm.addLayoutComponent("myPanel", this.panel);
		JButton expo = new JButton("Chameleon");
		
		expo.addActionListener(new ActionListener(){
			Random gen = new Random();
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				float r = gen.nextFloat();
				float g = gen.nextFloat();
				float b = gen.nextFloat();
				Color randoColor = new Color(r, g, b);
				panel.setBackground(randoColor);
			}
			
		});
		panel.add(expo);
		panel.validate();
	}
	
	@Override
	public void addReceiver(iStatusReceiver receiver) {
		this.r = receiver;
	}

	@Override
	public JPanel getPanel() {
		return this.panel;
	}

}