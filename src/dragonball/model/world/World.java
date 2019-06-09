package dragonball.model.world;

import java.util.ArrayList;
import java.util.Random;

import dragonball.model.cell.Cell;
import dragonball.model.cell.Collectible;
import dragonball.model.cell.CollectibleCell;
import dragonball.model.cell.EmptyCell;
import dragonball.model.cell.FoeCell;
import dragonball.model.character.fighter.NonPlayableFighter;

public class World {

	private Cell[][] map;
	private int playerColumn;
	private int playerRow;

	public World() {
		map = new Cell[10][10];
	}

	public Cell[][] getMap() {
		return map;
	}

	public int getPlayerColumn() {
		return playerColumn;
	}

	public int getPlayerRow() {
		return playerRow;
	}

	public void generateMap(ArrayList<NonPlayableFighter> weakFoes, ArrayList<NonPlayableFighter> strongFoes) {
		Random number = new Random();
		FoeCell bossCell = new FoeCell(strongFoes.get(number.nextInt(strongFoes.size())));
		map[0][0] = bossCell;
		for (int i = 0; i < 15; i++) {
			FoeCell weakCell = new FoeCell(weakFoes.get(number.nextInt(weakFoes.size())));
			while (true) {
				int weakXPosition = number.nextInt(map.length);
				int weakYPosition = number.nextInt(map[weakXPosition].length);
				if (map[weakXPosition][weakYPosition] == null
						&& (weakXPosition != map.length - 1 || weakYPosition != map[weakXPosition].length - 1)) {
					map[weakXPosition][weakYPosition] = weakCell;
					break;
				}
			}
		}

		int senzuAmount = 3 + number.nextInt(3);
		for (int i = 0; i < senzuAmount; i++) {
			CollectibleCell senzuCell = new CollectibleCell(Collectible.SENZU_BEAN);
			while (true) {
				int senzuXPosition = number.nextInt(map.length);
				int senzuYPosition = number.nextInt(map[senzuXPosition].length);
				if (map[senzuXPosition][senzuYPosition] == null
						&& (senzuXPosition != map.length - 1 || senzuYPosition != map[senzuXPosition].length - 1)) {
					map[senzuXPosition][senzuYPosition] = senzuCell;
					break;
				}
			}
		}
		CollectibleCell ballCell = new CollectibleCell(Collectible.DRAGON_BALL);
		while (true) {
			int ballXPosition = number.nextInt(map.length);
			int ballYPosition = number.nextInt(map[ballXPosition].length);
			if (map[ballXPosition][ballYPosition] == null
					&& (ballXPosition != map.length - 1 || ballYPosition != map[ballXPosition].length - 1)) {
				map[ballXPosition][ballYPosition] = ballCell;
				break;
			}
		}

		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map.length; j++)
				if (map[i][j] == null)
					map[i][j] = new EmptyCell();

	}

	public String toString() {
		String output = "";
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++)
				output += map[i][j] + " ";
			output += '\n';
		}
		return output;
	}

}
