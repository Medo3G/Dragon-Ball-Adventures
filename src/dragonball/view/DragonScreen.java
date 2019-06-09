package dragonball.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class DragonScreen extends JLayeredPane {

	private GenericButton ok;
	private GenericToggleButton[] wishes;
	private JLabel dragonName;
	private JLabel dragonImage;

	public DragonScreen() {
		setLayout(null);
		setBounds(0, 0, 1280, 720);
		setPreferredSize(new Dimension(1280, 720));
		setFocusable(true);
		requestFocusInWindow();

		dragonImage = new JLabel();
		dragonImage.setBounds(350, 5, 650, 528);
		dragonImage.setHorizontalAlignment(SwingConstants.CENTER);
		dragonImage.setVerticalAlignment(SwingConstants.TOP);
		add(dragonImage, -1);

		JLabel dragonBG = new JLabel(new ImageIcon("images/dragon/dragonBG.jpg"));
		dragonBG.setBounds(0, 0, 1280, 720);
		add(dragonBG, -1);

		JPanel screen = new JPanel();
		screen.setLayout(null);
		screen.setBounds(0, 0, 1280, 720);
		screen.setPreferredSize(new Dimension(1280, 720));
		screen.setBackground(new Color(0, 0, 0, 0));
		add(screen, 0);

		dragonName = new JLabel("Dragon Name");
		dragonName.setHorizontalAlignment(SwingConstants.CENTER);
		dragonName.setFont(new Font(dragonName.getFont().getName(), dragonName.getFont().getStyle(), 35));
		dragonName.setBounds(540, 350, 200, 40);
		dragonName.setBackground(new Color(255, 255, 255, 200));
		dragonName.setOpaque(true);
		// TODO setFontSize
		screen.add(dragonName);

		JPanel buttonsContainer = new JPanel();
		buttonsContainer.setLayout(new GridLayout(4, 1, 0, 10));
		buttonsContainer.setBounds(390, 430, 500, 200);
		buttonsContainer.setBackground(new Color(0, 0, 0, 0));
		screen.add(buttonsContainer);

		wishes = new GenericToggleButton[4];

		ButtonGroup bg = new ButtonGroup();

		GenericToggleButton senzuBeans = new GenericToggleButton("Snezu Beans");
		senzuBeans.setName("senzuBeans");
		buttonsContainer.add(senzuBeans);
		bg.add(senzuBeans);
		wishes[0] = senzuBeans;

		GenericToggleButton abilityPoints = new GenericToggleButton("Ability Points");
		abilityPoints.setName("abilityPoints");
		buttonsContainer.add(abilityPoints);
		bg.add(abilityPoints);
		wishes[1] = abilityPoints;

		GenericToggleButton superAttack = new GenericToggleButton("Super Attack");
		superAttack.setName("superAttack");
		buttonsContainer.add(superAttack);
		bg.add(superAttack);
		wishes[2] = superAttack;

		GenericToggleButton ultimateAttack = new GenericToggleButton("Ultimate Attack");
		ultimateAttack.setName("ultimateAttack");
		buttonsContainer.add(ultimateAttack);
		bg.add(ultimateAttack);
		wishes[3] = ultimateAttack;

		ok = new GenericButton("OK");
		ok.setName("dragonOK");
		ok.setBounds(480, 650, 320, 30);
		screen.add(ok);
	}

	public GenericButton getOk() {
		return ok;
	}

	public GenericToggleButton[] getWishes() {
		return wishes;
	}

	public JLabel getDragonName() {
		return dragonName;
	}

	public JLabel getDragonImage() {
		return dragonImage;
	}

}
