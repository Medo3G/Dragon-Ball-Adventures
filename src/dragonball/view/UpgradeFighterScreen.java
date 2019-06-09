package dragonball.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class UpgradeFighterScreen extends JLayeredPane {

	private JLabel fighterName;
	private JLabel abilityPoints;
	private JLabel MaxHP;
	private JLabel MaxKi;
	private JLabel MaxStamina;
	private JLabel PhysicalDamage;
	private JLabel BlastDamage;

	private GenericButton upgradeHP;
	private GenericButton upgradeKi;
	private GenericButton upgradeStamina;
	private GenericButton upgradePhysical;
	private GenericButton upgradeBlast;

	private GenericButton ok;

	public UpgradeFighterScreen() {

		setLayout(null);
		setBounds(0, 0, 1280, 720);
		setPreferredSize(new Dimension(1280, 720));

		JPanel screen = new JPanel();
		screen.setLayout(null);
		screen.setBounds(0, 0, 1280, 720);
		screen.setPreferredSize(new Dimension(1280, 720));
		screen.setBackground(new Color(0,0,0,0));
		add(screen,0);
		
		JPanel fakeUpgradeContainer = new JPanel();
		fakeUpgradeContainer.setBackground(new Color(0,0,0));
		fakeUpgradeContainer.setOpaque(true);
		fakeUpgradeContainer.setBounds(380,23,520,635);
		fakeUpgradeContainer.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));
		add(fakeUpgradeContainer,-1);
		
		
		JLabel upgradeBG = new JLabel(new ImageIcon("images/worldFunctions/worldFunctionsBG.jpg"));
		upgradeBG.setBounds(0, 0, 1280, 720);
		add(upgradeBG,-1);

		JPanel statsContainer = new JPanel();
		statsContainer.setLayout(new GridLayout(7, 1));
		statsContainer.setBounds(390, 25, 400, 630);
		statsContainer.setBackground(new Color(0,0,0,0));
		screen.add(statsContainer);

		fighterName = new JLabel("Name: ");
		fighterName.setForeground(Color.white);
		fighterName.setBackground(Color.black);
		fighterName.setOpaque(true);
		statsContainer.add(fighterName);

		abilityPoints = new JLabel("Ability Points: ");
		abilityPoints.setForeground(Color.white);
		abilityPoints.setBackground(Color.black);
		abilityPoints.setOpaque(true);
		statsContainer.add(abilityPoints);

		MaxHP = new JLabel("Max HP");
		MaxHP.setForeground(Color.white);
		MaxHP.setBackground(Color.black);
		MaxHP.setOpaque(true);
		statsContainer.add(MaxHP);

		MaxKi = new JLabel("Max Ki");
		MaxKi.setForeground(Color.white);
		MaxKi.setBackground(Color.black);
		MaxKi.setOpaque(true);
		statsContainer.add(MaxKi);

		MaxStamina = new JLabel("Max Stamina");
		MaxStamina.setForeground(Color.white);
		MaxStamina.setBackground(Color.black);
		MaxStamina.setOpaque(true);
		statsContainer.add(MaxStamina);

		PhysicalDamage = new JLabel("Physical Damage");
		PhysicalDamage.setForeground(Color.white);
		PhysicalDamage.setBackground(Color.black);
		PhysicalDamage.setOpaque(true);
		statsContainer.add(PhysicalDamage);

		BlastDamage = new JLabel("Max Damage");
		BlastDamage.setForeground(Color.white);
		BlastDamage.setBackground(Color.black);
		BlastDamage.setOpaque(true);
		statsContainer.add(BlastDamage);

		JPanel buttonsContainer = new JPanel();
		buttonsContainer.setLayout(new GridLayout(5, 1, 0, 40));
		buttonsContainer.setBounds(790, 245, 100, 380);
		buttonsContainer.setBackground(new Color(0,0,0,0));
		screen.add(buttonsContainer);

		upgradeHP = new GenericButton("+50");
		upgradeHP.setName("upgradeHP");
		buttonsContainer.add(upgradeHP);

		upgradeKi = new GenericButton("+1");
		upgradeKi.setName("upgradeKi");
		buttonsContainer.add(upgradeKi);

		upgradeStamina = new GenericButton("+1");
		upgradeStamina.setName("upgradeStamina");
		buttonsContainer.add(upgradeStamina);

		upgradePhysical = new GenericButton("+50");
		upgradePhysical.setName("upgradePhysical");
		buttonsContainer.add(upgradePhysical);

		upgradeBlast = new GenericButton("+50");
		upgradeBlast.setName("upgradeBlast");
		buttonsContainer.add(upgradeBlast);

		ok = new GenericButton("OK");
		ok.setName("upgradeFighterOK");
		ok.setBounds(490, 670, 300, 30);
		screen.add(ok);

	}

	public JLabel getFighterName() {
		return fighterName;
	}

	public JLabel getAbilityPoints() {
		return abilityPoints;
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

	public GenericButton getUpgradeHP() {
		return upgradeHP;
	}

	public GenericButton getUpgradeKi() {
		return upgradeKi;
	}

	public GenericButton getUpgradeStamina() {
		return upgradeStamina;
	}

	public GenericButton getUpgradePhysical() {
		return upgradePhysical;
	}

	public GenericButton getUpgradeBlast() {
		return upgradeBlast;
	}

	public GenericButton getOk() {
		return ok;
	}

}
