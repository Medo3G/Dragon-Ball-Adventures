package dragonball.model.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import dragonball.model.attack.Attack;
import dragonball.model.attack.MaximumCharge;
import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.SuperSaiyan;
import dragonball.model.attack.UltimateAttack;
import dragonball.model.character.fighter.NonPlayableFighter;
import dragonball.model.dragon.Dragon;
import dragonball.model.player.Player;
import dragonball.model.world.World;

public class Game {
	private Player player;
	private World world;
	private ArrayList<NonPlayableFighter> weakFoes;
	private ArrayList<NonPlayableFighter> strongFoes;
	private ArrayList<Attack> attacks;
	private ArrayList<Dragon> dragons;

	public Player getPlayer() {
		return player;
	}

	public World getWorld() {
		return world;
	}

	public ArrayList<NonPlayableFighter> getWeakFoes() {
		return weakFoes;
	}

	public ArrayList<NonPlayableFighter> getStrongFoes() {
		return strongFoes;
	}

	public ArrayList<Attack> getAttacks() {
		return attacks;
	}

	public ArrayList<Dragon> getDragons() {
		return dragons;
	}

	public Game() throws IOException {
		player = null;
		attacks = new ArrayList<Attack>();
		strongFoes = new ArrayList<NonPlayableFighter>();
		weakFoes = new ArrayList<NonPlayableFighter>();
		dragons = new ArrayList<Dragon>();
		loadAttacks("Database-Attacks.csv");
		loadFoes("Database-Foes-Range1.csv");
		loadDragons("Database-Dragons.csv");
		world = new World();
		world.generateMap(weakFoes, strongFoes);
	}

	private ArrayList<String> loadCSV(String filePath) throws IOException {
		String currentLine = "";
		ArrayList<String> output = new ArrayList<String>();
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			output.add(currentLine);
		}
		return output;

	}

	private void loadAttacks(String filePath) throws IOException {
		ArrayList<String> dataInput = loadCSV(filePath);
		for (int i = 0; i < dataInput.size(); i++) {
			String[] attackInfo = dataInput.get(i).split(",");
			if (attackInfo[0].equals("SA"))
				attacks.add(new SuperAttack(attackInfo[1], Integer.parseInt(attackInfo[2])));
			else if (attackInfo[0].equals("UA"))
				attacks.add(new UltimateAttack(attackInfo[1], Integer.parseInt(attackInfo[2])));
			else if (attackInfo[0].equals("SS"))
				attacks.add(new SuperSaiyan());
			else if (attackInfo[0].equals("MC"))
				attacks.add(new MaximumCharge());
		}
	}

	private void loadFoes(String filePath) throws IOException {
		ArrayList<String> dataInput = loadCSV(filePath);
		for (int i = 0; i < dataInput.size(); i = i + 3) {
			String[] foeInfo = dataInput.get(i).split(",");
			String[] foeSupers = dataInput.get(i + 1).split(",");
			String[] foeUlts = dataInput.get(i + 2).split(",");
			ArrayList<SuperAttack> supers = new ArrayList<SuperAttack>();
			for (int j = 0; j < foeSupers.length; j++)
				for (int j2 = 0; j2 < attacks.size(); j2++)
					if (attacks.get(j2).getName().equals(foeSupers[j]) && attacks.get(j2) instanceof SuperAttack) {
						supers.add((SuperAttack) attacks.get(j2));
						break;
					}
			ArrayList<UltimateAttack> ults = new ArrayList<UltimateAttack>();
			for (int j = 0; j < foeUlts.length; j++)
				for (int j2 = 0; j2 < attacks.size(); j2++)
					if (attacks.get(j2).getName().equals(foeUlts[j]) && attacks.get(j2) instanceof UltimateAttack) {
						ults.add((UltimateAttack) attacks.get(j2));
						break;
					}
			NonPlayableFighter foe = new NonPlayableFighter(foeInfo[0], Integer.parseInt(foeInfo[1]),
					Integer.parseInt(foeInfo[2]), Integer.parseInt(foeInfo[3]), Integer.parseInt(foeInfo[4]),
					Integer.parseInt(foeInfo[5]), Integer.parseInt(foeInfo[6]), Boolean.parseBoolean(foeInfo[7]),
					supers, ults);
			if (Boolean.parseBoolean(foeInfo[7]))
				strongFoes.add(foe);
			else
				weakFoes.add(foe);
		}
	}

	private void loadDragons(String filePath) throws IOException {
		ArrayList<String> dataInput = loadCSV(filePath);
		for (int i = 0; i < dataInput.size(); i = i + 3) {
			String[] dragonInfo = dataInput.get(i).split(",");
			String[] dragonSupers = dataInput.get(i + 1).split(",");
			String[] dragonUlts = dataInput.get(i + 2).split(",");
			ArrayList<SuperAttack> supers = new ArrayList<SuperAttack>();
			for (int j = 0; j < dragonSupers.length; j++)
				for (int j2 = 0; j2 < attacks.size(); j2++)
					if (attacks.get(j2).getName().equals(dragonSupers[j]) && attacks.get(j2) instanceof SuperAttack) {
						supers.add((SuperAttack) attacks.get(j2));
						break;
					}
			ArrayList<UltimateAttack> ults = new ArrayList<UltimateAttack>();
			for (int j = 0; j < dragonUlts.length; j++)
				for (int j2 = 0; j2 < attacks.size(); j2++)
					if (attacks.get(j2).getName().equals(dragonUlts[j]) && attacks.get(j2) instanceof UltimateAttack) {
						ults.add((UltimateAttack) attacks.get(j2));
						break;
					}
			dragons.add(new Dragon(dragonInfo[0], supers, ults, Integer.parseInt(dragonInfo[1]),
					Integer.parseInt(dragonInfo[2])));
		}
	}

}
