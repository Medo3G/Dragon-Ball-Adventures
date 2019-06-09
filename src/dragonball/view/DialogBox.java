package dragonball.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DialogBox extends JLayeredPane {
	private GenericButton ok;

	public DialogBox(String[] message) {

		setBounds(0, 0, 1280, 720);
		setPreferredSize(new Dimension(1280, 720));
		JPanel overlay = new JPanel();
		overlay.setBounds(0, 0, 1280, 720);
		overlay.setBackground(new Color(0, 0, 0, 100));
		overlay.addMouseListener(new MouseAdapter() {
		});
		overlay.addKeyListener(new KeyAdapter() {
		});

		JPanel messagePanel = new JPanel();
		messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.PAGE_AXIS));
		messagePanel.setBackground(null);
		messagePanel.setOpaque(true);

		JPanel dialogBox = new JPanel();
		dialogBox.setLayout(null);
		dialogBox.setBackground(Color.black);
		dialogBox.setBorder(BorderFactory.createLineBorder(Color.white, 2));

		for (int i = 0; i < message.length; i++) {
			JLabel displayedMessage = new JLabel(message[i]);
			displayedMessage.setBackground(null);
			displayedMessage.setForeground(Color.white);
			displayedMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
			displayedMessage.setPreferredSize(new Dimension(Math.max(displayedMessage.getPreferredSize().width, 300),
					displayedMessage.getPreferredSize().height));
			messagePanel.add(displayedMessage);

		}

		messagePanel.setBounds(10, 10, messagePanel.getPreferredSize().width, messagePanel.getPreferredSize().height);

		dialogBox.add(messagePanel);

		ok = new GenericButton("OK");
		ok.requestFocusInWindow();
		ok.setName("dialogBoxOk");
		ok.setBounds(messagePanel.getPreferredSize().width / 2 - 25, messagePanel.getPreferredSize().height + 15, 70,
				30);

		dialogBox.add(ok);
		dialogBox.setSize(20 + messagePanel.getPreferredSize().width, 50 + messagePanel.getPreferredSize().height);

		dialogBox.setLocation(640 - dialogBox.getWidth() / 2, 360 - dialogBox.getHeight() / 2);

		add(dialogBox, 0);
		add(overlay, 1);

	}

	public GenericButton getOk() {
		return ok;
	}

}
