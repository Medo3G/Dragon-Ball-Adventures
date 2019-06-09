package dragonball.model.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
import dragonball.model.cell.Collectible;
import dragonball.model.cell.EmptyCell;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.NonPlayableFighter;
import dragonball.model.dragon.Dragon;
import dragonball.model.dragon.DragonWish;
import dragonball.model.player.Player;
import dragonball.model.player.PlayerListener;
import dragonball.model.world.World;
import dragonball.model.world.WorldListener;

public class Game implements PlayerListener, WorldListener, BattleListener {
	private Player player;
	private World world;
	private ArrayList<NonPlayableFighter> weakFoes;
	private ArrayList<NonPlayableFighter> strongFoes;
	private ArrayList<Attack> attacks;
	private ArrayList<Dragon> dragons;
	private GameState state;
	private GameListener listener;

	public Game() {
		weakFoes = new ArrayList<>();
		strongFoes = new ArrayList<>();
		attacks = new ArrayList<>();
		dragons = new ArrayList<>();

		player = new Player("Test Player"); //For now
		player.setListener(this);

		loadAttacks("Database-Attacks.csv");
		loadFoes("Database-Foes-Range1.csv");
		loadDragons("Database-Dragons.csv");

		world = new World();
		world.generateMap(weakFoes, strongFoes);
		world.setListener(this); // MAYBE? YES
		
		state = GameState.WORLD;
	}

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

	public GameState getState() {
		return state;
	}

	public GameListener getListener() {
		return listener;
	}

	public void setListener(GameListener listener) {
		this.listener = listener;
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

	private void loadAttacks(String filePath) {
		String[][] lines = loadCSV(filePath);

		for (int i = 0; i < lines.length; i++) {
			Attack attack = null;

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
			}

			if (attack != null) {
				attacks.add(attack);
			}
		}
	}

	private void loadFoes(String filePath) {
		String[][] lines = loadCSV(filePath);

		for (int i = 0; i < lines.length; i += 3) {
			String name = lines[i][0];
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

	private void loadDragons(String filePath) {
		String[][] lines = loadCSV(filePath);

		for (int i = 0; i < lines.length; i += 3) {
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
	public void onBattleEvent(BattleEvent e) {
		if (e.getType() == BattleEventType.ENDED) {
			if (e.getWinner() == player.getActiveFighter()) {
				Fighter foe = (Fighter) ((Battle) e.getSource()).getFoe();
				player.getActiveFighter()
						.setXp(player.getActiveFighter().getXp() + 5 * (foe.getLevel()));
				player.getSuperAttacks().addAll(foe.getSuperAttacks());
				player.getUltimateAttacks().addAll(foe.getUltimateAttacks());
				if (((NonPlayableFighter) ((Battle) e.getSource()).getFoe()).isStrong()) {

					weakFoes.clear();
					strongFoes.clear();
					int playerRange = (player.getMaxFighterLevel() - 1) / 10 + 1;
					if (playerRange > 5)
						playerRange = 5;
					loadFoes("Database-Foes-Range" + playerRange + ".csv");
					player.setExploredMaps(player.getExploredMaps() + 1);
					world = new World();
					world.generateMap(weakFoes, strongFoes);
					world.setListener(this); // MAYBE? YES
				} else
					world.getMap()[world.getPlayerRow()][world.getPlayerColumn()] = new EmptyCell();
			} else {
				world.resetPlayerPosition();
			}
			state = GameState.WORLD;
		}
		if (listener != null)
			listener.onBattleEvent(e);

	}

	@Override
	public void onFoeEncountered(NonPlayableFighter foe) {
		Battle battle = new Battle(player.getActiveFighter(), foe);

		state = GameState.BATTLE;
		battle.setListener(this);
		battle.start();
		if (listener != null)
			listener.onBattleEvent(new BattleEvent(battle, BattleEventType.STARTED));

	}

	@Override
	public void onCollectibleFound(Collectible collectible) {

		if (collectible == Collectible.SENZU_BEAN)
			player.setSenzuBeans(player.getSenzuBeans() + 1);
		else if (collectible == Collectible.DRAGON_BALL)
			player.setDragonBalls(player.getDragonBalls() + 1);
		if (listener != null)
			listener.onCollectibleFound(collectible);
		if (player.getDragonBalls() == 7)
			player.callDragon();

	}

	@Override
	public void onDragonCalled() {
		player.setDragonBalls(0);
		int dragonIndex = new Random().nextInt(dragons.size());

		state = GameState.DRAGON;

		if (listener != null)
			listener.onDragonCalled(dragons.get(dragonIndex));

	}

	@Override
	public void onWishChosen(DragonWish wish) {
		state = GameState.WORLD;

	}
	
	public static void main(String[] args) {
		Game g = new Game();
		System.out.println(g.world);
	}

}
