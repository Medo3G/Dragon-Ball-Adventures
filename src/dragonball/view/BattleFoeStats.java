package dragonball.view;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class BattleFoeStats extends JLayeredPane {

	public BattleFoeStats(String name, int level, int currHP, int maxHP, int currKi, int maxKi, int currStamina,
			int maxStamina, boolean hasTurn) {

		setLayout(null);
		setBounds(0, 0, 500, 150);
		setPreferredSize(new Dimension(500, 150));
		setBackground(Color.cyan);

		JLayeredPane health = new JLayeredPane();
		health.setBounds(0, 0, 500, 40);
		add(health);

		JPanel healthBG = new JPanel();
		healthBG.setBounds(0, 0, 500, 40);
		healthBG.setBackground(Color.black);
		health.add(healthBG, 2);

		JLabel healthText = new JLabel(currHP + "/" + maxHP);
		healthText.setForeground(Color.white);
		healthText.setBounds(10, 5, 480, 30);
		healthText.setHorizontalAlignment(SwingConstants.RIGHT);
		healthText.setFont(new Font(healthText.getFont().getName(), healthText.getFont().getStyle(), 30));
		health.add(healthText, 0);

		JPanel healthBarContainer = new JPanel();
		healthBarContainer.setBounds(2, 2, 496, 36);
		healthBarContainer.setBackground(Color.black);
		healthBarContainer.setLayout(new BoxLayout(healthBarContainer, BoxLayout.LINE_AXIS));
		healthBarContainer.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		health.add(healthBarContainer, 1);

		JPanel healthBar = new JPanel();
		healthBar.setSize((int) Math.ceil(currHP * 496.0 / maxHP), 36);
		healthBar.setPreferredSize(new Dimension((int) Math.ceil(currHP * 496.0 / maxHP), 36));
		healthBar.setMaximumSize(new Dimension((int) Math.ceil(currHP * 496.0 / maxHP), 36));
		healthBar.setBackground(Color.red);
		healthBarContainer.add(healthBar);

		JPanel nameContainer = new JPanel();
		// JLabel nameContainer = new JLabel();
		nameContainer.setBackground(new Color(0, 0, 0, 0));
		nameContainer.setLayout(new BoxLayout(nameContainer, BoxLayout.LINE_AXIS));
		nameContainer.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		nameContainer.setBackground(new Color(255, 255, 255, 200));
		nameContainer.setBounds(10, 42, 480, 30);
		add(nameContainer);

		JLabel nameLabel = new JLabel();
		nameLabel.setText(name);
		nameLabel.setFont(new Font(healthText.getFont().getName(), healthText.getFont().getStyle(), 20));
		// nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		// nameLabel.setMaximumSize(new Dimension(430, 30));
		nameLabel.setPreferredSize(new Dimension(430, 30));
		nameLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		nameContainer.add(nameLabel);
		nameContainer.add(Box.createRigidArea(new Dimension(5, 0)));

		// nameContainer.setForeground(Color.white);

		JLabel levelLabel = new JLabel();
		levelLabel.setText("LVL: " + level);
		levelLabel.setFont(new Font(healthText.getFont().getName(), healthText.getFont().getStyle(), 10));
		// levelLabel.setMaximumSize(new Dimension(50, 30));
		// levelLabel.setPreferredSize(new
		// Dimension(levelLabel.getPreferredSize().width, 30));
		levelLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		nameContainer.add(levelLabel);

		JPanel ki = new JPanel();
		ki.setBounds(0, 75, 500, 20);
		ki.setBackground(new Color(0, 0, 0, 0));
		ki.setLayout(new BoxLayout(ki, BoxLayout.LINE_AXIS));
		ki.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		add(ki);

		JPanel kiContainer = new JPanel();
		kiContainer.setBounds(0, 0, 300, 20);
		kiContainer.setPreferredSize(new Dimension(300, 20));
		kiContainer.setMaximumSize(new Dimension(300, 20));
		kiContainer.setLayout(new GridLayout(1, maxKi, 1, 0));
		kiContainer.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		kiContainer.setBackground(Color.black);
		ki.add(kiContainer);

		for (int i = maxKi; i > 0; i--) {
			if (i <= currKi) {
				JPanel k = new JPanel();
				k.setBackground(Color.blue);
				kiContainer.add(k);
			} else {
				kiContainer.add(new JLabel(""));
			}
		}

		JLabel kiText = new JLabel(currKi + "/" + maxKi + " Ki");
		// kiText.setMaximumSize(new Dimension(195, 20));
		kiText.setVerticalAlignment(SwingConstants.BOTTOM);
		ki.add(Box.createRigidArea(new Dimension(5, 0)));
		kiText.setBackground(new Color(255, 255, 255, 200));
		kiText.setOpaque(true);
		ki.add(kiText);

		JPanel stamina = new JPanel();
		stamina.setBounds(0, 100, 500, 20);
		stamina.setBackground(new Color(0, 0, 0, 0));
		stamina.setLayout(new BoxLayout(stamina, BoxLayout.LINE_AXIS));
		stamina.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		add(stamina);

		JPanel staminaontainer = new JPanel();
		staminaontainer.setBounds(0, 0, 300, 20);
		staminaontainer.setPreferredSize(new Dimension(300, 20));
		staminaontainer.setMaximumSize(new Dimension(300, 20));
		staminaontainer.setLayout(new GridLayout(1, maxStamina, 1, 0));
		staminaontainer.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		staminaontainer.setBackground(Color.black);
		stamina.add(staminaontainer);

		for (int i = maxStamina; i > 0; i--) {
			if (i <= currStamina) {
				JPanel s = new JPanel();
				s.setBackground(Color.green);
				staminaontainer.add(s);
			} else {
				staminaontainer.add(new JLabel(""));
			}
		}

		JLabel StaminaText = new JLabel(currStamina + "/" + maxStamina + " Stamina");
		// StaminaText.setMaximumSize(new Dimension(195, 20));
		StaminaText.setVerticalAlignment(SwingConstants.BOTTOM);
		StaminaText.setBackground(new Color(255, 255, 255, 200));
		StaminaText.setOpaque(true);
		stamina.add(Box.createRigidArea(new Dimension(5, 0)));
		stamina.add(StaminaText);

		if (hasTurn) {
			JLabel turnIndicator = new JLabel("Currently Playing");
			turnIndicator.setBackground(new Color(255, 255, 255, 200));
			turnIndicator.setOpaque(true);
			turnIndicator.setBounds(490 - turnIndicator.getPreferredSize().width, 125,
					turnIndicator.getPreferredSize().width, turnIndicator.getPreferredSize().height);
			add(turnIndicator);
		}

	}

}
