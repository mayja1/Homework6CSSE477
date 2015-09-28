import interfaces.iStatusReceiver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;


public class StatusPanel extends JPanel implements iStatusReceiver {
	JLabel l;
	public StatusPanel() {
		this.setPreferredSize(new Dimension(100, 100));
		this.setBorder(new LineBorder(Color.BLACK));
		JLabel label = new JLabel("status");
		l = new JLabel("<html></html>");
		this.add(label, BorderLayout.NORTH);
		this.add(l, BorderLayout.CENTER);
	}

	@Override
	public void receiveMessage(String s) {
		String text = l.getText();
		l.setText( "<html>" + s + "<br>" + text.substring(6));
		//l.setText(s);
		this.repaint();
	}

}
