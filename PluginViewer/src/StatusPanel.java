import interfaces.iStatusReceiver;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class StatusPanel extends JPanel implements iStatusReceiver {
	JLabel l;
	public StatusPanel() {
		this.setPreferredSize(new Dimension(100, 100));
		this.setBorder(new LineBorder(Color.BLACK));
		JLabel label = new JLabel("status");
		l = new JLabel("<html></html>");
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(label);
		this.add(l);
		this.add( Box.createVerticalStrut(400) );
	}

	@Override
	public void receiveMessage(String s) {
		String text = l.getText();
		l.setText( "<html>" + s + "<br>" + text.substring(6));
		//l.setText(s);
		this.repaint();
	}

}
