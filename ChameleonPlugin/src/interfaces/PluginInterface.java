package interfaces;
import javax.swing.JPanel;


public interface PluginInterface {

	public void addReceiver(iStatusReceiver receiver);
	public JPanel getPanel();
}
