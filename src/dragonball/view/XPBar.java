package dragonball.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class XPBar extends JLayeredPane {

	public XPBar(int currXP, int targetXP) {

		setLayout(null);
		setBounds(0, 0, 250, 3);
		setPreferredSize(new Dimension(250, 3));

		JPanel XPBG = new JPanel();
		XPBG.setBounds(0, 0, 250, 3);
		XPBG.setBackground(Color.black);
		add(XPBG, -1);

		JPanel XP = new JPanel();
		XP.setBounds(0, 0, (int) (Math.ceil(currXP * 250 / targetXP)), 3);
		XP.setBackground(Color.green);
		add(XP, 0);

	}

}
