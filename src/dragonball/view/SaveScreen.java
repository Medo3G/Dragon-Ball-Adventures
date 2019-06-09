package dragonball.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class SaveScreen extends JLayeredPane {
	private JTextArea save;
	private GenericButton ok;
	private GenericButton cancel;

	public SaveScreen() {
		setLayout(null);
		setBounds(0, 0, 1280, 720);
		setPreferredSize(new Dimension(1280, 720));
		
		JLabel saveBG = new JLabel(new ImageIcon("images/worldFunctions/worldFunctionsBG.jpg"));
		saveBG.setBounds(0, 0, 1280, 720);
		add(saveBG,-1);

		JPanel screen = new JPanel();
		screen.setLayout(null);
		screen.setBounds(0, 0, 1280, 720);
		screen.setPreferredSize(new Dimension(1280, 720));
		screen.setBackground(new Color(0,0,0,0));
		add(screen,0);

		JPanel saveContainer = new JPanel();
		saveContainer.setLayout(null);
		saveContainer.setBounds(390, 310, 500, 100);
		saveContainer.setBackground(new Color(0, 0, 0, 200));
		saveContainer.setBorder(BorderFactory.createLineBorder(Color.white, 2));
		screen.add(saveContainer);

		save = new JTextArea();
		save.setBounds(25, 15, 450, 30);

		saveContainer.add(save);

		JPanel buttonsContainer = new JPanel();
		buttonsContainer.setLayout(new GridLayout(1, 2, 10, 0));
		buttonsContainer.setBounds(150, 55, 200, 30);
		buttonsContainer.setBackground(new Color(0, 0, 0, 0));
		saveContainer.add(buttonsContainer);

		cancel = new GenericButton("Cancel");
		cancel.setName("saveCancel");
		buttonsContainer.add(cancel);

		ok = new GenericButton("OK");
		ok.setName("saveOK");
		buttonsContainer.add(ok);
	}

	public JTextArea getSave() {
		return save;
	}

	public GenericButton getOk() {
		return ok;
	}

	public GenericButton getCancel() {
		return cancel;
	}

}
