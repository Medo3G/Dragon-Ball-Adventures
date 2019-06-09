package dragonball.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class BattleScreen extends JLayeredPane {
	private JPanel playerStats;
	private JPanel foeStats;

	private GenericButton attack;
	private GenericButton block;
	private GenericButton use;

	private JPanel attacksContainer;
	private ArrayList<GenericButton> attackButtons;

	private JLabel actionText;

	private JLabel backgroundImage;
	private JLabel meSprite;
	private JLabel foeSprite;

	public BattleScreen() {

		setLayout(null);
		setBounds(0, 0, 1280, 720);
		setPreferredSize(new Dimension(1280, 720));

		JLayeredPane screen = new JLayeredPane();
		screen.setLayout(null);
		screen.setBounds(0, 0, 1280, 720);
		screen.setPreferredSize(new Dimension(1280, 720));
		add(screen);

		JLayeredPane images = new JLayeredPane();
		images.setLayout(null);
		images.setBounds(0, 0, 1280, 720);
		screen.add(images, -1);

		backgroundImage = new JLabel();
		backgroundImage.setBounds(0, 0, 1280, 720);
		// backgroundImage.setBackground(Color.cyan);
		images.add(backgroundImage, -1);
		// backgroundImage.setIcon(new ImageIcon("images/battle/bg/0.gif"));

		foeSprite = new JLabel();
		// meSprite.setBackground(Color.red);
		// meSprite.setOpaque(true);
		foeSprite.setHorizontalAlignment(SwingConstants.LEFT);
		foeSprite.setVerticalAlignment(SwingConstants.BOTTOM);
		foeSprite.setBounds(640, 0, 650, 680);
		images.add(foeSprite, 0);
		// foeSprite.setIcon(new ImageIcon("images/battle/fighters/Android
		// 17/Stand.gif"));

		meSprite = new JLabel();
		// meSprite.setBackground(Color.red);
		// meSprite.setOpaque(true);
		meSprite.setHorizontalAlignment(SwingConstants.RIGHT);
		meSprite.setVerticalAlignment(SwingConstants.BOTTOM);
		meSprite.setBounds(0, 0, 640, 680);
		images.add(meSprite, 0);
		// meSprite.setIcon(new
		// ImageIcon("images/battle/playable/Frieza/Stand.gif"));

		JPanel buttonsNStats = new JPanel();
		buttonsNStats.setLayout(null);
		buttonsNStats.setBounds(0, 0, 1280, 720);
		buttonsNStats.setBackground(new Color(0, 0, 0, 0));
		screen.add(buttonsNStats, 0);

		playerStats = new JPanel();
		playerStats.setBounds(10, 10, 500, 150);
		playerStats.setBackground(new Color(0, 0, 0, 0));
		buttonsNStats.add(playerStats);

		foeStats = new JPanel();
		foeStats.setBounds(770, 10, 500, 150);
		foeStats.setBackground(new Color(0, 0, 0, 0));
		buttonsNStats.add(foeStats);

		JLayeredPane bottomContainer = new JLayeredPane();
		bottomContainer.setBounds(65, 650, 1150, 40);
		buttonsNStats.add(bottomContainer);

		JPanel containerBG = new JPanel();
		containerBG.setBackground(new Color(0, 0, 0, 250));
		containerBG.setBounds(0, 0, 1150, 40);
		containerBG.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));
		bottomContainer.add(containerBG, -1);

		JPanel buttonsContainer = new JPanel();
		buttonsContainer.setLayout(new GridLayout(1, 3, 200, 0));
		buttonsContainer.setBounds(0, 0, 1150, 40);
		bottomContainer.add(buttonsContainer, 0);
		buttonsContainer.setBackground(new Color(0, 0, 0, 250));
		buttonsContainer.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));

		block = new GenericButton();
		block.setText("Block");
		block.setName("block");
		buttonsContainer.add(block);

		attack = new GenericButton();
		attack.setText("Attack");
		attack.setName("attack");
		buttonsContainer.add(attack);

		use = new GenericButton();
		use.setText("Use");
		use.setName("use");
		buttonsContainer.add(use);

		actionText = new JLabel("ActionText");
		actionText.setFont(new Font(actionText.getFont().getName(), actionText.getFont().getStyle(), 23));
		actionText.setForeground(Color.white);
		actionText.setBounds(0, 0, 1150, 40);
		actionText.setHorizontalAlignment(SwingConstants.CENTER);
		actionText.setVerticalAlignment(SwingConstants.CENTER);
		bottomContainer.add(actionText, 0);
		actionText.setVisible(false);

		attacksContainer = new JPanel();
		attacksContainer.setOpaque(true);
		attacksContainer.setBackground(Color.black);
		attacksContainer.setBorder(BorderFactory.createLineBorder(Color.white));
		attacksContainer.setLayout(new BoxLayout(attacksContainer, BoxLayout.PAGE_AXIS));
		// attacksContainer.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		attacksContainer.setBounds(515, 440, 250, 210);
		// attacksContainer.setBackground(new Color(0, 0, 0, 0));
		buttonsNStats.add(attacksContainer, 0);
		attacksContainer.setVisible(false);

		attackButtons = new ArrayList<>();

	}

	public JPanel getPlayerStats() {
		return playerStats;
	}

	public JPanel getFoeStats() {
		return foeStats;
	}

	public GenericButton getAttack() {
		return attack;
	}

	public GenericButton getBlock() {
		return block;
	}

	public GenericButton getUse() {
		return use;
	}

	public JPanel getAttacksContainer() {
		return attacksContainer;
	}

	public ArrayList<GenericButton> getAttackButtons() {
		return attackButtons;
	}

	public JLabel getActionText() {
		return actionText;
	}

	public JLabel getBackgroundImage() {
		return backgroundImage;
	}

	public JLabel getMeSprite() {
		return meSprite;
	}

	public JLabel getFoeSprite() {
		return foeSprite;
	}

}
