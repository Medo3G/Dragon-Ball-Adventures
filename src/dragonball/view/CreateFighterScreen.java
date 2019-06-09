package dragonball.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class CreateFighterScreen extends JPanel {

	private JTextField fighterInput;
	private GenericButton ok;
	private GenericButton cancel;
	private JTextArea raceBio;
	private ArrayList<JToggleButton> races;
	private final String[] bios = new String[] {
			"The inhabitants of the planet earth are weak by nature. However, some trained really hard and became in league with other warrior races. The Human race is the most balanced of all the races in terms of strength and defence.\nFamous Earthlings include : Krillin, Yamcha and Tien",
			"The Saiyans are naturally born to be a warrior race. They are known for their amazing strength and skills in battle. The Saiyans were an evil race, invading other planets, destroy their inhabitants and sell the planets to the highest bidder. Their planet was later on destroyed by the evil lord Frieza destroying most of the Saiyan race leaving only few Saiyans alive, those fortunate enough to be away from planet Vegeta including Goku and prince Vegeta himself. After Vegeta’s battle with Goku on earth while he was searching for the dragon balls on earth, Vegeta was defeated and little by little he knew the error of his evil ways and started to join the Z-Fighters in defending the universe. The Saiyan race has a special transformation called Super Saiyan, this transformation increases the power and strength of the Saiyans.\nFamous Saiyans include : Goku and Vegeta as well as their half Saiyans sons and daughters.",
			"The inhabitants of planet Namek are strong yet peaceful people. They have a lot of magical abilities and special powers. A Namekian can create a set of Dragon Balls, among other things. They are known for their regenerative abilities and their cunning and wisdom. \nFamous Namekians include : Piccolo and Kami.",
			"Frieza was probably considered the strongest in the universe for quite so long. His hunger for power grew each and everyday. Frieza had a lot of warriors and fighters(including the Saiyans) invading planets for him expanding his empire by each passing day. Thanks to his incredible powers he managed to keep his subordinates from defying him. However, he feared that the Saiyans might unlock their hidden potential and transform into Super Saiyans and defeat him as by then, none of the Saiyans did achieve this transformation that was known only from legends. So, Frieza decided to exterminate the Saiyans and destroy planet Vegeta. In doing so he angered prince Vegeta who vowed to get revenge for his race. Later on Frieza travelled to planet Namek in order to search for the original dragon balls and wish for eternal life. After a fierce battle with Goku, Vegeta and others on planet Namek, Goku finally transformed into a Super Saiyan and was the first one able to overpower Frieza and defeated him eventually. Even Though Frieza is now dead, others from his race continue to live on. Fighters of this race are known for their incredible speed and strength. \nFamous Characters of this race : Frieza and his father King Cold.",
			"Unleashed by the evil wizard Bibidi, Majin Buu in his original (Kid Buu) form went on a killing and destruction spree obliterating entire civilizations with just his breath. Thanks to the Grand Supreme Kai intervention, he infused his own goodness into Majin Buu making him less evil. However, the monster still remained and after a while good Buu and evil Buu separated resulting in the toughest battle the Z-Fighters had yet to encounter. Eventually, the evil Kid Buu was defeated and the good Buu lived on starting his own race. Majin fighters are known for their great health and stamina. \nFamous Majin fighters : Buu in all his forms (Majin Buu, Super Buu and Kid Buu)." };

	public CreateFighterScreen() {
		setLayout(null);
		setBounds(0, 0, 1280, 720);
		setPreferredSize(new Dimension(1280, 720));

		JPanel racesContainer = new JPanel();
		racesContainer.setLayout(new GridLayout(1, 5));
		racesContainer.setBounds(0, 0, 1280, 525);
		racesContainer.setBackground(Color.blue);
		add(racesContainer);

		JToggleButton earthling = new JToggleButton(new ImageIcon("images/create/earthling.jpg"));
		earthling.setName("earthling");
		earthling.setFocusable(false);
		earthling.setSelectedIcon(new ImageIcon("images/create/earthling-selected.jpg"));
		earthling.setBackground(null);
		racesContainer.add(earthling);

		JToggleButton saiyan = new JToggleButton(new ImageIcon("images/create/saiyan.jpg"));
		saiyan.setName("saiyan");
		saiyan.setFocusable(false);
		saiyan.setSelectedIcon(new ImageIcon("images/create/saiyan-selected.jpg"));
		saiyan.setBackground(null);
		racesContainer.add(saiyan);

		JToggleButton namekian = new JToggleButton(new ImageIcon("images/create/namekian.jpg"));
		namekian.setName("namekian");
		namekian.setFocusable(false);
		namekian.setSelectedIcon(new ImageIcon("images/create/namekian-selected.jpg"));
		namekian.setBackground(null);
		racesContainer.add(namekian);

		JToggleButton frieza = new JToggleButton(new ImageIcon("images/create/frieza.jpg"));
		frieza.setName("frieza");
		frieza.setFocusable(false);
		frieza.setSelectedIcon(new ImageIcon("images/create/frieza-selected.jpg"));
		frieza.setBackground(null);
		racesContainer.add(frieza);

		JToggleButton majin = new JToggleButton(new ImageIcon("images/create/majin.jpg"));
		majin.setName("majin");
		majin.setFocusable(false);
		majin.setSelectedIcon(new ImageIcon("images/create/majin-selected.jpg"));
		majin.setBackground(null);
		racesContainer.add(majin);

		ButtonGroup group = new ButtonGroup();
		races = new ArrayList<>();
		races.add(earthling);
		group.add(earthling);
		earthling.setSelected(true);
		group.add(saiyan);
		races.add(saiyan);
		group.add(namekian);
		races.add(namekian);
		group.add(frieza);
		races.add(frieza);
		group.add(majin);
		races.add(majin);

		/******************/
		JLayeredPane bottomContainer = new JLayeredPane();
		bottomContainer.setBounds(0,525,1280,195);
		add(bottomContainer);
		
		JLabel bottomBG = new JLabel(new ImageIcon("images/create/botttomBG.jpg"));
		bottomBG.setBounds(0,0,1280,195);
		bottomContainer.add(bottomBG, -1);
		
		JPanel namesContainer = new JPanel();
		namesContainer.setLayout(null);
		namesContainer.setBounds(6, 8, 500, 175);
		namesContainer.setBackground(new Color(0, 0, 0));
		namesContainer.setBorder(BorderFactory.createLineBorder(Color.white));
		bottomContainer.add(namesContainer,0);

		

		JLabel fighterName = new JLabel("Fighter Name:");
		fighterName.setForeground(Color.white);
		fighterName.setBounds(10, 55, 128, 30);
		namesContainer.add(fighterName);

		
		fighterInput = new JTextField();
		fighterInput.setName("fighterInput");
		fighterInput.setBounds(120, 55, 350, 30);
		namesContainer.add(fighterInput);

		JPanel buttonsContainer = new JPanel();
		buttonsContainer.setLayout(new GridLayout(1, 2, 10, 0));
		buttonsContainer.setBounds(320, 110, 150, 30);
		buttonsContainer.setBackground(new Color(0, 0, 0, 0));
		namesContainer.add(buttonsContainer);

		cancel = new GenericButton("Cancel");
		cancel.setName("createFighterCancel");
		buttonsContainer.add(cancel);

		ok = new GenericButton("OK");
		ok.setName("createFighterOK");
		buttonsContainer.add(ok);

		raceBio = new JTextArea();
		raceBio.setText(bios[0]);
		// raceBio.setLayout(null);
		raceBio.setEditable(false);
		raceBio.setLineWrap(true);
		raceBio.setWrapStyleWord(true);
		raceBio.setBounds(517, 8, 758, 180);
		raceBio.setFocusable(false);
		bottomContainer.add(raceBio,0);
		raceBio.setBackground(new Color(0,0,0));
		raceBio.setBorder(BorderFactory.createLineBorder(Color.white));
		raceBio.setForeground(Color.white);
	}

	public JTextField getFighterInput() {
		return fighterInput;
	}

	public GenericButton getOk() {
		return ok;
	}

	public GenericButton getCancel() {
		return cancel;
	}

	public JTextArea getRaceBio() {
		return raceBio;
	}

	public ArrayList<JToggleButton> getRaces() {
		return races;
	}

	public String[] getBios() {
		return bios;
	}

}
