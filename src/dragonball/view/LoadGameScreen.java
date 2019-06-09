package dragonball.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class LoadGameScreen extends JLayeredPane {

	private JPanel saves;
	private GenericButton ok;
	private GenericButton cancel;
	private ArrayList<GenericToggleButton> radioButtons;

	public LoadGameScreen() {

		setLayout(null);
		setBounds(0, 0, 1280, 720);
		setPreferredSize(new Dimension(1280, 720));
		
		JLabel loadBG = new JLabel(new ImageIcon("images/worldFunctions/worldFunctionsBG.jpg"));
		loadBG.setBounds(0, 0, 1280, 720);
		add(loadBG,-1);

		JPanel screen = new JPanel();
		screen.setLayout(null);
		screen.setBounds(0, 0, 1280, 720);
		screen.setPreferredSize(new Dimension(1280, 720));
		screen.setBackground(new Color(0,0,0,0));
		add(screen,0);

		saves = new JPanel();
		saves.setLayout(new BoxLayout(saves, BoxLayout.PAGE_AXIS));
		saves.setBackground(Color.black);

		JScrollPane savesScroll = new JScrollPane(saves);
		savesScroll.setBounds(390, 20, 500, 600);
		savesScroll.setBackground(Color.black);
		screen.add(savesScroll);

		JPanel buttonsContainer = new JPanel();
		buttonsContainer.setLayout(new GridLayout(1, 2, 40, 0));
		buttonsContainer.setBounds(390, 640, 500, 40);
		buttonsContainer.setBackground(new Color(0,0,0,0));
		screen.add(buttonsContainer);

		cancel = new GenericButton("Cancel");
		cancel.setName("loadGameCancel");
		buttonsContainer.add(cancel);

		ok = new GenericButton("OK");
		ok.setName("loadGameOK");
		buttonsContainer.add(ok);

		radioButtons = new ArrayList<>(); // Which are no longer radiobuttons

	}

	public JPanel getSaves() {
		return saves;
	}

	public GenericButton getOk() {
		return ok;
	}

	public GenericButton getCancel() {
		return cancel;
	}

	public ArrayList<GenericToggleButton> getRadioButtons() {
		return radioButtons;
	}

}
