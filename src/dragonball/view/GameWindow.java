package dragonball.view;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameWindow extends JFrame implements java.io.Serializable{

	public GameWindow() {
		setTitle("Dragon Ball Adventures");
		setDefaultCloseOperation(EXIT_ON_CLOSE); // TODO Change later
		setResizable(false);
		setVisible(true);
	}

}
