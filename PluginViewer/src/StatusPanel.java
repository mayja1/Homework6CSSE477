import interfaces.iStatusReceiver;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class StatusPanel extends JPanel implements iStatusReceiver {
	JLabel l;
	public StatusPanel() {
		this.setPreferredSize(new Dimension(100, 100));
		this.setBorder(new LineBorder(Color.BLACK));
		l = new JLabel("status");
		this.add(l);
	}

	@Override
	public void receiveMessage(String s) {
		l.setText(s);
		this.repaint();
	}

}
