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
public class SwitchFighterScreen extends JLayeredPane {

	private JPanel fighters;
	private GenericButton ok;
	private GenericButton cancel;
	private ArrayList<GenericToggleButton> radioButtons;

	public SwitchFighterScreen() {

		setLayout(null);
		setBounds(0, 0, 1280, 720);
		setPreferredSize(new Dimension(1280, 720));
		//setBackground(Color.orange);
		
		JLabel switchBG = new JLabel(new ImageIcon("images/worldFunctions/worldFunctionsBG.jpg"));
		switchBG.setBounds(0, 0, 1280, 720);
		add(switchBG,-1);
		
		fighters = new JPanel();
		fighters.setLayout(new BoxLayout(fighters, BoxLayout.PAGE_AXIS));
		// saves.setBounds(390, 20, 500, 600);
		fighters.setBackground(Color.black);
		// add(saves);
		JScrollPane savesScroll = new JScrollPane(fighters);
		savesScroll.setBounds(390, 20, 500, 600);
		savesScroll.setBackground(Color.black);
		add(savesScroll,0);
		
		JPanel buttonsContainer = new JPanel();
		buttonsContainer.setLayout(new GridLayout(1, 2, 40, 0));
		buttonsContainer.setBounds(390, 640, 500, 40);
		buttonsContainer.setBackground(new Color(0,0,0,0));
		add(buttonsContainer,0);

		cancel = new GenericButton("Cancel");
		cancel.setName("switchFighterCancel");
		buttonsContainer.add(cancel);

		ok = new GenericButton("OK");
		ok.setName("switchFighterOK");
		buttonsContainer.add(ok);

		radioButtons = new ArrayList<>(); // Which are no longer radiobuttons
	}

	public JPanel getFighters() {
		return fighters;
	}

	public GenericButton getCancel() {
		return cancel;
	}

	public GenericButton getOk() {
		return ok;
	}

	public ArrayList<GenericToggleButton> getRadioButtons() {
		return radioButtons;
	}

}
