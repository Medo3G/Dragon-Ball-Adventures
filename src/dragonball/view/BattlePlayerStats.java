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
public class BattlePlayerStats extends JLayeredPane {

	public BattlePlayerStats(String name, int level, int currHP, int maxHP, int currKi, int maxKi, int currStamina,
			int maxStamina, boolean hasTurn) {

		setLayout(null);
		setBounds(0, 0, 500, 150);
		setPreferredSize(new Dimension(500, 150));

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
		healthText.setFont(new Font(healthText.getFont().getName(), healthText.getFont().getStyle(), 30));
		health.add(healthText, 0);

		JPanel healthBar = new JPanel();
		healthBar.setBounds(2, 2, (int) Math.ceil(currHP * 496.0 / maxHP), 36);
		healthBar.setBackground(Color.red);
		health.add(healthBar, 1);

		JPanel nameContainer = new JPanel();
		nameContainer.setBackground(new Color(0, 0, 0,0));
		nameContainer.setLayout(new BoxLayout(nameContainer, BoxLayout.LINE_AXIS));
		nameContainer.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		nameContainer.setBackground(new Color(255,255,255,200));
		nameContainer.setBounds(10, 42, 480, 30);
		add(nameContainer);

		JLabel levelLabel = new JLabel();
		levelLabel.setText("LVL: " + level);
		levelLabel.setFont(new Font(healthText.getFont().getName(), healthText.getFont().getStyle(), 10));
		levelLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		nameContainer.add(levelLabel);

		JLabel nameLabel = new JLabel();
		nameLabel.setText(name);
		nameLabel.setFont(new Font(healthText.getFont().getName(), healthText.getFont().getStyle(), 20));
		nameLabel.setPreferredSize(new Dimension(430, 30));
		nameLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		nameContainer.add(nameLabel);
		nameContainer.add(Box.createRigidArea(new Dimension(5, 0)));

		JPanel ki = new JPanel();
		ki.setBounds(0, 75, 500, 20);
		ki.setBackground(new Color(0, 0, 0,0));
		ki.setLayout(new BoxLayout(ki, BoxLayout.LINE_AXIS));
		add(ki);

		JPanel kiContainer = new JPanel();
		kiContainer.setBounds(0, 0, 300, 20);		
		kiContainer.setPreferredSize(new Dimension(300, 20));
		kiContainer.setMaximumSize(new Dimension(300, 20));
		kiContainer.setLayout(new GridLayout(1, maxKi, 1, 0));
		kiContainer.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		kiContainer.setBackground(Color.black);
		ki.add(kiContainer);

		for (int i = 0; i < maxKi; i++) {
			if (i < currKi) {
				JPanel k = new JPanel();
				k.setBackground(Color.blue);
				kiContainer.add(k);
			} else {
				kiContainer.add(new JLabel(""));
			}
		}

		JLabel kiText = new JLabel(currKi + "/" + maxKi + " Ki");
		kiText.setVerticalAlignment(SwingConstants.BOTTOM);
		kiText.setBackground(new Color(255,255,255,200));
		kiText.setOpaque(true);
		ki.add(Box.createRigidArea(new Dimension(5, 0)));
		ki.add(kiText);

		JPanel stamina = new JPanel();
		stamina.setBounds(0, 100, 500, 20);
		stamina.setBackground(new Color(0, 0, 0,0));
		stamina.setLayout(new BoxLayout(stamina, BoxLayout.LINE_AXIS));
		add(stamina);

		JPanel staminaontainer = new JPanel();
		staminaontainer.setBounds(0, 0, 300, 20);
		staminaontainer.setPreferredSize(new Dimension(300, 20));
		staminaontainer.setMaximumSize(new Dimension(300, 20));
		staminaontainer.setLayout(new GridLayout(1, maxStamina, 1, 0));
		staminaontainer.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		staminaontainer.setBackground(Color.black);
		stamina.add(staminaontainer);

		for (int i = 0; i < maxStamina; i++) {
			if (i < currStamina) {
				JPanel s = new JPanel();
				s.setBackground(Color.green);
				staminaontainer.add(s);
			} else {
				staminaontainer.add(new JLabel(""));
			}
		}

		JLabel StaminaText = new JLabel(currStamina + "/" + maxStamina + " Stamina");
		StaminaText.setVerticalAlignment(SwingConstants.BOTTOM);
		StaminaText.setBackground(new Color(255,255,255,200));
		StaminaText.setOpaque(true);
		stamina.add(Box.createRigidArea(new Dimension(5, 0)));
		stamina.add(StaminaText);

		if (hasTurn) {
			JLabel turnIndicator = new JLabel("Currently Playing");
			turnIndicator.setBackground(new Color(255,255,255,200));
			turnIndicator.setOpaque(true);
			turnIndicator.setBounds(5, 125, turnIndicator.getPreferredSize().width,
					turnIndicator.getPreferredSize().height);
			add(turnIndicator);
		}

	}

}
