

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicOptionPaneUI;

import interfaces.PluginInterface;
import interfaces.iStatusReceiver;

public class ExponentialPlugin implements PluginInterface {

	private JPanel panel;
	private iStatusReceiver r;
	
	public ExponentialPlugin() {
		panel = new JPanel();
//		LayoutManager lm = new BasicOptionPaneUI.ButtonAreaLayout(true, 5);
//		lm.addLayoutComponent("myPanel", this.panel);
		JButton expo = new JButton("I can square things");
		final JLabel lbl = new JLabel("I am a label");
		expo.setSize(200, 200);
		lbl.setSize(200,200);
		
		expo.addActionListener(new ActionListener(){

			private int currentNum = 2;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int prev = this.currentNum;
				this.currentNum = this.currentNum * this.currentNum;
				if(this.currentNum == 0) {
					r.receiveMessage("Oops... I got too big");
					r.receiveMessage("Setting back to 2");
					this.currentNum = 2;
				} else {
					r.receiveMessage("I started at: " + prev);
					r.receiveMessage("I have squared up to: " + this.currentNum);
				}
				lbl.setText("The number is currently: " + this.currentNum);
			}
			
		});
		panel.add(expo);
		panel.add(lbl);
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
