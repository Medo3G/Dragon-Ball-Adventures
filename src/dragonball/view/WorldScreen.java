package dragonball.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class WorldScreen extends JLayeredPane {

	private JPanel map;

	private JLabel raceImage;

	private JLabel fighterName;
	private JLabel fighterLevel;
	private JPanel XPBar;
	private JLabel MaxHP;
	private JLabel MaxKi;
	private JLabel MaxStamina;
	private JLabel PhysicalDamage;
	private JLabel BlastDamage;

	private JLabel playerName;
	private JLabel dragonballs;
	private JLabel senzubeans;
	private JLabel exploredMaps;

	private ArrayList<GenericButton> buttons;

	public WorldScreen() {

		setLayout(null);
		setBounds(0, 0, 1280, 720);
		setPreferredSize(new Dimension(1280, 720));
		setFocusable(true);
		requestFocusInWindow();

		JPanel screen = new JPanel();
		screen.setLayout(null);
		screen.setBounds(0, 0, 1280, 720);
		screen.setPreferredSize(new Dimension(1280, 720));
		add(screen);

		JLayeredPane mapContainer = new JLayeredPane();
		mapContainer.setLayout(null);
		mapContainer.setBounds(280, 0, 720, 720);

		screen.add(mapContainer);

		JLabel mapBG = new JLabel();
		mapBG.setBounds(0, 0, 1280, 720);
		ImageIcon image = new ImageIcon("images/world/map.png");
		mapBG.setIcon(image);
		mapContainer.add(mapBG, -1);

		map = new JPanel();
		map.setBounds(10, 10, 700, 700);
		map.setLayout(new GridLayout(10, 10, 0, 0));
		map.setBackground(new Color(0, 0, 0, 0));
		map.setName("map");

		mapContainer.add(map, 0);

		/*****************/

		JLayeredPane activeFighterInfo = new JLayeredPane();
		activeFighterInfo.setLayout(null);
		activeFighterInfo.setBounds(0, 0, 280, 720);
		activeFighterInfo.setBackground(Color.cyan);
		screen.add(activeFighterInfo);

		JLabel leftBG = new JLabel(new ImageIcon("images/world/leftBG.jpg"));
		leftBG.setBounds(0, 0, 280, 720);
		activeFighterInfo.add(leftBG, -1);

		raceImage = new JLabel();
		raceImage.setBounds(15, 20, 250, 450);
		raceImage.setHorizontalAlignment(SwingConstants.CENTER);
		raceImage.setVerticalAlignment(SwingConstants.BOTTOM);
		activeFighterInfo.add(raceImage, 0);

		fighterName = new JLabel("Fighter Name");
		fighterName.setHorizontalAlignment(SwingConstants.CENTER);
		fighterName.setOpaque(true);
		fighterName.setBounds(15, 480, 250, 40);
		fighterName.setBackground(new Color(0, 0, 0, 200));
		fighterName.setForeground(Color.white);
		activeFighterInfo.add(fighterName, 0);

		fighterLevel = new JLabel("LVL: ");
		fighterLevel.setHorizontalAlignment(SwingConstants.CENTER);
		fighterLevel.setOpaque(true);
		fighterLevel.setBounds(15, 520, 250, 20);
		fighterLevel.setBackground(new Color(0, 0, 0, 200));
		fighterLevel.setForeground(Color.white);
		activeFighterInfo.add(fighterLevel, 0);

		XPBar = new JPanel();
		XPBar.setLayout(new BorderLayout());
		XPBar.setBounds(15, 540, 250, 3);
		activeFighterInfo.add(XPBar, 0);

		JPanel activeFighterStats = new JPanel();
		activeFighterStats.setBounds(15, 560, 250, 150);
		activeFighterStats.setLayout(new GridLayout(5, 1));
		activeFighterStats.setBackground(new Color(0, 0, 0, 200));
		activeFighterStats.setForeground(Color.white);
		activeFighterInfo.add(activeFighterStats, 0);

		MaxHP = new JLabel("Max HP");
		MaxHP.setForeground(Color.white);
		activeFighterStats.add(MaxHP);

		MaxKi = new JLabel("Max Ki");
		MaxKi.setForeground(Color.white);
		activeFighterStats.add(MaxKi);

		MaxStamina = new JLabel("Max Stamina");
		MaxStamina.setForeground(Color.white);
		activeFighterStats.add(MaxStamina);

		PhysicalDamage = new JLabel("Physical Damage");
		PhysicalDamage.setForeground(Color.white);
		activeFighterStats.add(PhysicalDamage);

		BlastDamage = new JLabel("Blast Damage");
		BlastDamage.setForeground(Color.white);
		activeFighterStats.add(BlastDamage);

		/*******************/
		JLayeredPane infoNButtons = new JLayeredPane();
		infoNButtons.setBounds(1000, 0, 280, 720);
		infoNButtons.setBackground(Color.orange);
		infoNButtons.setLayout(null);
		screen.add(infoNButtons);

		JLabel rightBG = new JLabel(new ImageIcon("images/world/rightBG.jpg"));
		rightBG.setBounds(0, 0, 280, 720);
		infoNButtons.add(rightBG, -1);

		JPanel infoContainer = new JPanel();
		infoContainer.setLayout(new GridLayout(4, 1, 0, 10));
		infoContainer.setBounds(15, 20, 250, 280);
		infoContainer.setBackground(new Color(0, 0, 0, 200));
		infoNButtons.add(infoContainer, 0);

		playerName = new JLabel("Player Name");
		playerName.setForeground(Color.white);
		infoContainer.add(playerName);
		playerName.setHorizontalAlignment(SwingConstants.CENTER);

		dragonballs = new JLabel("Dragonballs");
		dragonballs.setForeground(Color.white);
		dragonballs.setIcon(new ImageIcon("images/world/dragonballs.png"));
		infoContainer.add(dragonballs);

		senzubeans = new JLabel("SenzuBeans");
		senzubeans.setForeground(Color.white);
		senzubeans.setIcon(new ImageIcon("images/world/senzubeans.png"));
		infoContainer.add(senzubeans);

		exploredMaps = new JLabel("ExploredMaps");
		exploredMaps.setForeground(Color.white);
		exploredMaps.setIcon(new ImageIcon("images/world/exploredmaps.png"));
		infoContainer.add(exploredMaps);

		JPanel buttonsContainer = new JPanel();
		buttonsContainer.setLayout(new GridLayout(5, 1));
		buttonsContainer.setBackground(new Color(0, 0, 0, 0));
		buttonsContainer.setBounds(15, 550, 250, 150);
		infoNButtons.add(buttonsContainer, 0);

		buttons = new ArrayList<>();

		GenericButton createFighter = new GenericButton("Create Fighter");
		createFighter.setName("createFighter");
		buttonsContainer.add(createFighter);
		buttons.add(createFighter);

		GenericButton switchFighter = new GenericButton("Switch Fighter");
		buttonsContainer.add(switchFighter);
		switchFighter.setName("switchFighter");
		buttons.add(switchFighter);

		GenericButton assignAttacks = new GenericButton("Assign Attacks");
		buttonsContainer.add(assignAttacks);
		buttons.add(assignAttacks);
		assignAttacks.setName("assignAttacks");

		GenericButton upgradeFighter = new GenericButton("Upgrade Fighter");
		buttonsContainer.add(upgradeFighter);
		buttons.add(upgradeFighter);
		upgradeFighter.setName("upgradeFighter");

		GenericButton save = new GenericButton("Save");
		buttonsContainer.add(save);
		buttons.add(save);
		save.setName("save");

	}

	public JPanel getMap() {
		return map;
	}

	public JLabel getRaceImage() {
		return raceImage;
	}

	public JLabel getFighterName() {
		return fighterName;
	}

	public JLabel getFighterLevel() {
		return fighterLevel;
	}

	public JPanel getXPBar() {
		return XPBar;
	}

	public JLabel getMaxHP() {
		return MaxHP;
	}

	public JLabel getMaxKi() {
		return MaxKi;
	}

	public JLabel getMaxStamina() {
		return MaxStamina;
	}

	public JLabel getPhysicalDamage() {
		return PhysicalDamage;
	}

	public JLabel getBlastDamage() {
		return BlastDamage;
	}

	public JLabel getPlayerName() {
		return playerName;
	}

	public JLabel getDragonballs() {
		return dragonballs;
	}

	public JLabel getSenzubeans() {
		return senzubeans;
	}

	public JLabel getExploredMaps() {
		return exploredMaps;
	}

	public ArrayList<GenericButton> getButtons() {
		return buttons;
	}

}
