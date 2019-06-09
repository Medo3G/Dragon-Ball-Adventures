package dragonball.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TitleScreen extends JLayeredPane {

	private GenericButton newGame;
	private GenericButton loadGame;

	public TitleScreen() {
		setBackground(Color.BLACK);
		setSize(1280, 720);
		setPreferredSize(new Dimension(1280, 720));
		setLayout(null);
		
		JLabel titleBG = new JLabel(new ImageIcon("images/title/titleBG.gif"));
		titleBG.setBounds(0, 0, 1280, 720);
		add(titleBG,-1);

		JPanel buttonsContainer = new JPanel();
		buttonsContainer.setLayout(null);
		buttonsContainer.setBounds(getWidth() * 7 / 24, getHeight() * 2 / 3, getWidth() * 5 / 12, 30);
		add(buttonsContainer,0);
		buttonsContainer.setBackground(new Color(0,0,0,0));
		newGame = new GenericButton("New Game");
		newGame.setName("newGame");
		newGame.setBounds(0, 0, 200, 30);
		loadGame = new GenericButton("Load Game");
		loadGame.setName("loadGame");
		loadGame.setBounds(buttonsContainer.getWidth() - 200, 0, 200, 30);
		buttonsContainer.add(newGame);
		buttonsContainer.add(loadGame);

	}

	public GenericButton getNewGame() {
		return newGame;
	}

	public GenericButton getLoadGame() {
		return loadGame;
	}

}
