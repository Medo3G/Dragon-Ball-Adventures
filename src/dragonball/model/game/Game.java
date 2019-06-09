package dragonball.model.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import dragonball.model.attack.Attack;
import dragonball.model.attack.MaximumCharge;
import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.SuperSaiyan;
import dragonball.model.attack.UltimateAttack;
import dragonball.model.battle.Battle;
import dragonball.model.battle.BattleEvent;
import dragonball.model.battle.BattleEventType;
import dragonball.model.battle.BattleListener;
import dragonball.model.cell.Cell;
import dragonball.model.cell.Collectible;
import dragonball.model.cell.FoeCell;
import dragonball.model.character.fighter.NonPlayableFighter;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.dragon.Dragon;
import dragonball.model.dragon.DragonWish;
import dragonball.model.exceptions.InvalidFormatException;
import dragonball.model.exceptions.MissingFieldException;
import dragonball.model.exceptions.NotEnoughKiException;
import dragonball.model.exceptions.UnknownAttackTypeException;
import dragonball.model.player.Player;
import dragonball.model.player.PlayerListener;
import dragonball.model.world.World;
import dragonball.model.world.WorldListener;

public class Game implements PlayerListener, WorldListener, BattleListener, java.io.Serializable {
	private Player player;
	private World world;
	private GameState state;
	private ArrayList<NonPlayableFighter> weakFoes;
	private ArrayList<NonPlayableFighter> strongFoes;
	private ArrayList<Attack> attacks;
	private ArrayList<Dragon> dragons;
	private GameListener listener;
	private String lastSave;

	public Game() {
		player = new Player("Player");
		world = new World();
		state = GameState.WORLD;
		weakFoes = new ArrayList<>();
		strongFoes = new ArrayList<>();
		attacks = new ArrayList<>();
		dragons = new ArrayList<>();

		try {
			loadAttacks("Database-Attacks.csv");
		} catch (InvalidFormatException e) {
			try {
				attacks.clear();
				loadAttacks("Database-Attacks-aux.csv");
			} catch (InvalidFormatException e1) {

			}
		}
		try {
			loadFoes("Database-Foes-Range1.csv");
		} catch (InvalidFormatException e) {
			strongFoes.clear();
			weakFoes.clear();
			try {
				loadFoes("Database-Foes-aux.csv");
			} catch (InvalidFormatException e1) {

			}
		}
		try {
			loadDragons("Database-Dragons.csv");
		} catch (InvalidFormatException e) {
			dragons.clear();
			try {
				loadDragons("Database-Dragons-aux.csv");
			} catch (InvalidFormatException e1) {

			}
		}

		world.generateMap(weakFoes, strongFoes);

		player.setListener(this);
		world.setListener(this);
	}

	public Player getPlayer() {
		return player;
	}

	public World getWorld() {
		return world;
	}

	public GameState getState() {
		return state;
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

	public String getLastSave() {
		return lastSave;
	}

	private String[][] loadCSV(String filePath) {
		ArrayList<String[]> lines = new ArrayList<>();

		BufferedReader reader = null;
		String line = null;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			while ((line = reader.readLine()) != null) {
				lines.add(line.split(","));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return lines.toArray(new String[][] {});
	}

	public void loadAttacks(String filePath) throws InvalidFormatException {
		String[][] lines = loadCSV(filePath);

		for (int i = 0; i < lines.length; i++) {
			Attack attack = null;
			if (lines[i].length < 3)
				throw new MissingFieldException(filePath, i + 1, lines[i].length - 3);

			String attackType = lines[i][0];
			String name = lines[i][1];
			int damage = Integer.parseInt(lines[i][2]);

			if (attackType.equalsIgnoreCase("SA")) {
				attack = new SuperAttack(name, damage);
			} else if (attackType.equalsIgnoreCase("UA")) {
				attack = new UltimateAttack(name, damage);
			} else if (attackType.equalsIgnoreCase("MC")) {
				attack = new MaximumCharge();
			} else if (attackType.equalsIgnoreCase("SS")) {
				attack = new SuperSaiyan();
			} else
				throw new UnknownAttackTypeException(filePath, i, attackType);

			if (attack != null) {
				attacks.add(attack);
			}
		}
	}

	public void loadFoes(String filePath) throws InvalidFormatException {
		String[][] lines = loadCSV(filePath);

		for (int i = 0; i < lines.length; i += 3) {
			String name = lines[i][0];
			if (lines[i].length < 8)
				throw new MissingFieldException(filePath, i + 1, lines[i].length - 8);
			int level = Integer.parseInt(lines[i][1]);
			int maxHealthPoints = Integer.parseInt(lines[i][2]);
			int blastDamage = Integer.parseInt(lines[i][3]);
			int physicalDamage = Integer.parseInt(lines[i][4]);
			int maxKi = Integer.parseInt(lines[i][5]);
			int maxStamina = Integer.parseInt(lines[i][6]);
			boolean strong = Boolean.parseBoolean(lines[i][7]);
			ArrayList<SuperAttack> superAttacks = new ArrayList<>();
			ArrayList<UltimateAttack> ultimateAttacks = new ArrayList<>();

			for (int j = 0; j < lines[i + 1].length; j++) {
				String attackName = lines[i + 1][j];
				for (Attack attack : attacks) {
					if (attack instanceof SuperAttack && attack.getName().equalsIgnoreCase(attackName)) {
						superAttacks.add((SuperAttack) attack);
						break;
					}
				}
			}

			for (int j = 0; j < lines[i + 2].length; j++) {
				String attackName = lines[i + 2][j];
				for (Attack attack : attacks) {
					if (attack instanceof UltimateAttack && attack.getName().equalsIgnoreCase(attackName)) {
						ultimateAttacks.add((UltimateAttack) attack);
						break;
					}
				}
			}

			NonPlayableFighter foe = new NonPlayableFighter(name, level, maxHealthPoints, blastDamage, physicalDamage,
					maxKi, maxStamina, strong, superAttacks, ultimateAttacks);
			if (strong) {
				strongFoes.add(foe);
			} else {
				weakFoes.add(foe);
			}
		}
	}

	public void loadDragons(String filePath) throws InvalidFormatException {
		String[][] lines = loadCSV(filePath);

		for (int i = 0; i < lines.length; i += 3) {
			if (lines[i].length < 3)
				throw new MissingFieldException(filePath, i + 1, lines[i].length - 3);
			String name = lines[i][0];
			int senzuBeans = Integer.parseInt(lines[i][1]);
			int dragonsBalls = Integer.parseInt(lines[i][2]);
			ArrayList<SuperAttack> superAttacks = new ArrayList<>();
			ArrayList<UltimateAttack> ultimateAttacks = new ArrayList<>();

			for (int j = 0; j < lines[i + 1].length; j++) {
				String attackName = lines[i + 1][j];
				for (Attack attack : attacks) {
					if (attack instanceof SuperAttack && attack.getName().equalsIgnoreCase(attackName)) {
						superAttacks.add((SuperAttack) attack);
						break;
					}
				}
			}

			for (int j = 0; j < lines[i + 2].length; j++) {
				String attackName = lines[i + 2][j];
				for (Attack attack : attacks) {
					if (attack instanceof UltimateAttack && attack.getName().equalsIgnoreCase(attackName)) {
						ultimateAttacks.add((UltimateAttack) attack);
						break;
					}
				}
			}

			Dragon dragon = new Dragon(name, superAttacks, ultimateAttacks, senzuBeans, dragonsBalls);
			dragons.add(dragon);
		}
	}

	@Override
	public void onDragonCalled() {
		state = GameState.DRAGON;

		Dragon dragon = dragons.get(new Random().nextInt(dragons.size()));

		notifyOnDragonCalled(dragon);
	}

	@Override
	public void onWishChosen(DragonWish wish) {
		state = GameState.WORLD;
	}

	@Override
	public void onFoeEncountered(final NonPlayableFighter foe) {
		state = GameState.BATTLE;

		Battle battle = new Battle(player.getActiveFighter(), foe);
		// handle winning and losing in a battle
		battle.setListener(this);
		// start the battle
		battle.start();
	}

	@Override
	public void onCollectibleFound(Collectible collectible) {
		switch (collectible) {
		case SENZU_BEAN:
			player.setSenzuBeans(player.getSenzuBeans() + 1);
			notifyOnCollectibleFound(collectible);
			break;
		case DRAGON_BALL:
			player.setDragonBalls(player.getDragonBalls() + 1);
			notifyOnCollectibleFound(collectible);

			player.callDragon();
			break;
		}
	}

	@Override
	public void onBattleEvent(BattleEvent e) {
		NonPlayableFighter foe = (NonPlayableFighter) ((Battle) e.getSource()).getFoe();

		if (e.getType() == BattleEventType.ENDED) {
			PlayableFighter me = player.getActiveFighter();
			// if i won
			if (e.getWinner() == me) {
				
				state = GameState.WORLD;
				// gain xp
				me.setXp(me.getXp() + foe.getLevel() * 5);

				// learn opponents super and ultimate attacks
				for (SuperAttack superAttack : foe.getSuperAttacks()) {
					if (!player.getSuperAttacks().contains(superAttack)) {
						player.getSuperAttacks().add(superAttack);
					}
				}
				for (UltimateAttack ultimateAttack : foe.getUltimateAttacks()) {
					if (!player.getUltimateAttacks().contains(ultimateAttack)) {
						player.getUltimateAttacks().add(ultimateAttack);
					}
				}

				// if opponent is boss
				if (foe.isStrong()) {
					// increment explored maps by 1
					player.setExploredMaps(player.getExploredMaps() + 1);

					// reload foes in case range changed
					int foesRange = (player.getMaxFighterLevel() - 1) / 10 + 1;
					try {
						loadFoes("." + File.separator + "Database-Foes-Range" + foesRange + ".csv");
					} catch (InvalidFormatException e1) {
						strongFoes.clear();
						weakFoes.clear();
						try {
							loadFoes("Database-Foes-aux.csv");
						} catch (InvalidFormatException e2) {

						}
					}

					// regenerate map
					world.generateMap(weakFoes, strongFoes);
				}
				// if my opponent won
			} else if (e.getWinner() == foe) {
				// undo removing the foe from the cell
				Cell foeCell = new FoeCell(foe);
				foeCell.setListener(world);
				world.getMap()[world.getPlayerRow()][world.getPlayerColumn()] = foeCell;

				// reset player position to starting cell
				if (lastSave != null)
					load(lastSave);
				else {
					Game g = new Game();

					player = g.player;
					world = g.world;
					state = g.state;
					weakFoes = g.weakFoes;
					strongFoes = g.strongFoes;
					attacks = g.attacks;
					dragons = g.dragons;
					listener = g.listener;
					lastSave = g.lastSave;
					world.setListener(this);
					player.setListener(this);

				}

			}

			
		}

		notifyOnBattleEvent(e);
	}

	public void setListener(GameListener listener) {
		this.listener = listener;
	}

	public void notifyOnCollectibleFound(Collectible collectible) {
		if (listener != null) {
			listener.onCollectibleFound(collectible);
		}
	}

	public void notifyOnBattleEvent(BattleEvent e) {
		if (listener != null) {
			listener.onBattleEvent(e);
		}
	}

	public void notifyOnDragonCalled(Dragon dragon) {
		if (listener != null) {
			listener.onDragonCalled(dragon);
		}
	}

	public void save(String fileName) {
		try {
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
			lastSave = fileName;

		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public void load(String fileName) {
		try {
			Game e = null;
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			e = (Game) in.readObject();
			in.close();
			fileIn.close();
			player = e.player;
			world = e.world;
			state = e.state;
			weakFoes = e.weakFoes;
			strongFoes = e.strongFoes;
			attacks = e.attacks;
			dragons = e.dragons;
			listener = e.listener;
			lastSave = e.lastSave;
			world.setListener(this);
			player.setListener(this);

		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {

			c.printStackTrace();
			return;
		}
	}

	public static void main(String[] args) throws Exception {
		Game g = new Game();
		MaximumCharge mc = new MaximumCharge();
		ArrayList<SuperAttack> su = new ArrayList<>();
		su.add(mc);
		SuperSaiyan ss = new SuperSaiyan();
		ArrayList<UltimateAttack> ul = new ArrayList<>();
		ul.add(ss);
		PlayableFighter pl = new Saiyan("Sss", 2, 20, 1, 10000, 20, 30, 12, 5, 7, su, ul);
		ArrayList<PlayableFighter> ap = new ArrayList<>();
		ap.add(pl);
		g.player = new Player("TEst", ap, su, ul, 0, 9, pl, 7);
		PlayableFighter pl2 = new Saiyan("Sss", 2, 20, 1, 10000, 20, 30, 12, 5, 7, su, ul);
		Battle b = new Battle(pl, pl2);
		b.attack(mc);
		b.block();
		b.attack(ss);
		System.out.println(g.player.getActiveFighter().getKi());
		System.out.println(((Saiyan) g.player.getActiveFighter()).isTransformed());
		System.out.println(g.player.getActiveFighter().getName());
		System.out.println(g.player.getActiveFighter().getXp());
		System.out.println(g.player.getActiveFighter().getSuperAttacks().get(0).getName());
		System.out.println(g.player.getActiveFighter().getUltimateAttacks().get(0).getName());
		System.out.println(((FoeCell) g.world.getMap()[0][0]).getFoe().getName());
		g.save("test");
		Game s = new Game();
		s.load(g.lastSave);
		System.out.println(s.player.getActiveFighter().getKi());
		System.out.println(((Saiyan) s.player.getActiveFighter()).isTransformed());
		System.out.println(s.player.getActiveFighter().getName());
		System.out.println(s.player.getActiveFighter().getXp());
		System.out.println(s.player.getActiveFighter().getSuperAttacks().get(0).getName());
		System.out.println(s.player.getActiveFighter().getUltimateAttacks().get(0).getName());
		System.out.println(((FoeCell) s.world.getMap()[0][0]).getFoe().getName());

	}
}
