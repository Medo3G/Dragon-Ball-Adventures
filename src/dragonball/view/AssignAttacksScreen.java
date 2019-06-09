package dragonball.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class AssignAttacksScreen extends JLayeredPane {

	private GenericToggleButton superSwitch;
	private GenericToggleButton ultSwitch;
	private JPanel superPanel;
	private JPanel ultPanel;
	private GenericButton assignSuper;
	private GenericButton assignUlt;
	private ArrayList<GenericToggleButton> supers;
	private ArrayList<GenericToggleButton> ults;
	private ArrayList<GenericToggleButton> playerSupers;
	private ArrayList<GenericToggleButton> playerUlts;
	private JPanel playerSupersPanel;
	private JPanel playerUltsPanel;
	private GenericButton ok;
	private JPanel allSupers;
	private JPanel allUlts;

	public AssignAttacksScreen() {
		setLayout(null);
		setBounds(0, 0, 1280, 720);
		setPreferredSize(new Dimension(1280, 720));
		setBackground(new Color(0,0,0,0));
		
		JLabel assignBG = new JLabel(new ImageIcon("images/worldFunctions/worldFunctionsBG.jpg"));
		assignBG.setBounds(0, 0, 1280, 720);
		add(assignBG,-1);

		JPanel screen = new JPanel();
		screen.setLayout(null);
		screen.setBounds(0, 0, 1280, 720);
		screen.setPreferredSize(new Dimension(1280, 720));
		screen.setBackground(new Color(0,0,0,0));
		add(screen, 0);

		JPanel superOrUlt = new JPanel();
		superOrUlt.setLayout(new GridLayout(1, 2));
		superOrUlt.setBounds(340, 50, 600, 40);
		superOrUlt.setBackground(new Color(0,0,0,0));
		screen.add(superOrUlt);

		ButtonGroup bg = new ButtonGroup();

		superSwitch = new GenericToggleButton("Super Attacks");
		superSwitch.setName("superSwitch");
		superSwitch.setSelected(true);
		superOrUlt.add(superSwitch);
		bg.add(superSwitch);

		ultSwitch = new GenericToggleButton("Ultimate Attacks");
		ultSwitch.setName("ultSwitch");
		superOrUlt.add(ultSwitch);
		bg.add(ultSwitch);

		superPanel = new JPanel();
		superPanel.setLayout(null);
		superPanel.setBounds(200, 100, 880, 550);
		screen.add(superPanel);
		superPanel.setBackground(Color.black);
		superPanel.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));
		superPanel.setVisible(true);

		allSupers = new JPanel();
		allSupers.setLayout(new BoxLayout(allSupers, BoxLayout.PAGE_AXIS));
		allSupers.setBackground(Color.black);

		JScrollPane superScroll = new JScrollPane(allSupers);
		superScroll.setBounds(20, 20, 400, 500);
		superScroll.setBackground(Color.black);
		superPanel.add(superScroll);

		playerSupersPanel = new JPanel();
		playerSupersPanel.setLayout(new GridLayout(4, 1));
		playerSupersPanel.setBounds(460, 100, 400, 300);
		playerSupersPanel.setBackground(Color.black);
		superPanel.add(playerSupersPanel);

		assignSuper = new GenericButton("Assign");
		assignSuper.setName("assignSuper");
		assignSuper.setBounds(460, 440, 400, 30);
		superPanel.add(assignSuper);

		ultPanel = new JPanel();
		ultPanel.setLayout(null);
		ultPanel.setBounds(200, 100, 880, 550);
		ultPanel.setBackground(Color.black);
		ultPanel.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));
		screen.add(ultPanel);
		ultPanel.setVisible(false);

		allUlts = new JPanel();
		allUlts.setLayout(new BoxLayout(allUlts, BoxLayout.PAGE_AXIS));
		allUlts.setBackground(Color.black);

		JScrollPane ultScroll = new JScrollPane(allUlts);
		ultScroll.setBounds(20, 20, 400, 500);
		ultScroll.setBackground(Color.black);
		ultPanel.add(ultScroll);

		playerUltsPanel = new JPanel();
		playerUltsPanel.setLayout(new GridLayout(2, 1));
		playerUltsPanel.setBounds(460, 175, 400, 150);
		playerUltsPanel.setBackground(Color.black);
		ultPanel.add(playerUltsPanel);

		assignUlt = new GenericButton("Assign");
		assignUlt.setName("assignUlt");
		assignUlt.setBounds(460, 440, 400, 30);
		ultPanel.add(assignUlt);

		ok = new GenericButton("OK");
		ok.setName("assignAttacksOK");
		ok.setBounds(490, 670, 300, 30);
		screen.add(ok);

		supers = new ArrayList<>();
		ults = new ArrayList<>();
		playerSupers = new ArrayList<>();
		playerUlts = new ArrayList<>();

	}

	public GenericToggleButton getSuperSwitch() {
		return superSwitch;
	}

	public GenericToggleButton getUltSwitch() {
		return ultSwitch;
	}

	public JPanel getSuperPanel() {
		return superPanel;
	}

	public JPanel getUltPanel() {
		return ultPanel;
	}

	public GenericButton getAssignSuper() {
		return assignSuper;
	}

	public GenericButton getAssignUlt() {
		return assignUlt;
	}

	public ArrayList<GenericToggleButton> getSupers() {
		return supers;
	}

	public ArrayList<GenericToggleButton> getUlts() {
		return ults;
	}

	public ArrayList<GenericToggleButton> getPlayerSupers() {
		return playerSupers;
	}

	public ArrayList<GenericToggleButton> getPlayerUlts() {
		return playerUlts;
	}

	public JPanel getPlayerSupersPanel() {
		return playerSupersPanel;
	}

	public JPanel getPlayerUltsPanel() {
		return playerUltsPanel;
	}

	public GenericButton getOk() {
		return ok;
	}

	public JPanel getAllSupers() {
		return allSupers;
	}

	public JPanel getAllUlts() {
		return allUlts;
	}

}
