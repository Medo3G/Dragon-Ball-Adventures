package dragonball.model.player;

import java.util.ArrayList;

import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.UltimateAttack;
import dragonball.model.character.fighter.Earthling;
import dragonball.model.character.fighter.Frieza;
import dragonball.model.character.fighter.Majin;
import dragonball.model.character.fighter.Namekian;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.dragon.DragonWish;
import dragonball.model.dragon.DragonWishType;

public class Player {
	private String name;
	private ArrayList<PlayableFighter> fighters;
	private ArrayList<SuperAttack> superAttacks;
	private ArrayList<UltimateAttack> ultimateAttacks;
	private int senzuBeans;
	private int dragonBalls;
	private PlayableFighter activeFighter;
	private int exploredMaps;
	private PlayerListener listener;

	public Player(String name) {
		this(name, new ArrayList<PlayableFighter>(), new ArrayList<SuperAttack>(), new ArrayList<UltimateAttack>(), 0,
				0, null, 0);
	}

	public Player(String name, ArrayList<PlayableFighter> fighters, ArrayList<SuperAttack> superAttacks,
			ArrayList<UltimateAttack> ultimateAttacks, int senzuBeans, int dragonBalls, PlayableFighter activeFighter,
			int exploredMaps) {
		this.name = name;
		this.fighters = fighters;
		this.superAttacks = superAttacks;
		this.ultimateAttacks = ultimateAttacks;
		this.senzuBeans = senzuBeans;
		this.dragonBalls = dragonBalls;
		this.activeFighter = activeFighter;
		this.exploredMaps = exploredMaps;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<PlayableFighter> getFighters() {
		return fighters;
	}

	public void setFighters(ArrayList<PlayableFighter> fighters) {
		this.fighters = fighters;
	}

	public ArrayList<SuperAttack> getSuperAttacks() {
		return superAttacks;
	}

	public void setSuperAttacks(ArrayList<SuperAttack> superAttacks) {
		this.superAttacks = superAttacks;
	}

	public ArrayList<UltimateAttack> getUltimateAttacks() {
		return ultimateAttacks;
	}

	public void setUltimateAttacks(ArrayList<UltimateAttack> ultimateAttacks) {
		this.ultimateAttacks = ultimateAttacks;
	}

	public int getSenzuBeans() {
		return senzuBeans;
	}

	public void setSenzuBeans(int senzuBeans) {
		this.senzuBeans = senzuBeans;
	}

	public int getDragonBalls() {
		return dragonBalls;
	}

	public void setDragonBalls(int dragonBalls) {
		this.dragonBalls = dragonBalls;
	}

	public PlayableFighter getActiveFighter() {
		return activeFighter;
	}

	public void setActiveFighter(PlayableFighter activeFighter) {
		this.activeFighter = activeFighter;
	}

	public int getExploredMaps() {
		return exploredMaps;
	}

	public void setExploredMaps(int exploredMaps) {
		this.exploredMaps = exploredMaps;
	}

	public PlayerListener getListener() {
		return listener;
	}

	public void setListener(PlayerListener listener) {
		this.listener = listener;
	}

	public int getMaxFighterLevel() {

		int max = 0;
		for (int i = 0; i < fighters.size(); i++) {
			if (fighters.get(i).getLevel() > max)
				max = fighters.get(i).getLevel();

		}
		return max;
	}

	public void callDragon() {
		if (listener != null)
			listener.onDragonCalled();

	}

	public void chooseWish(DragonWish wish) {
		if (wish.getType() == DragonWishType.SUPER_ATTACK)
			superAttacks.add(wish.getSuperAttack());
		else if (wish.getType() == DragonWishType.ULTIMATE_ATTACK)
			ultimateAttacks.add(wish.getUltimateAttack());
		else if (wish.getType() == DragonWishType.SENZU_BEANS)
			senzuBeans += wish.getSenzuBeans();
		else
			activeFighter.setAbilityPoints(activeFighter.getAbilityPoints() + wish.getAbilityPoints());
		if (listener != null)
			listener.onWishChosen(wish);
	}

	public void createFighter(char race, String name) {
		if (race == 'E')
			fighters.add(new Earthling(name));
		else if (race == 'S')
			fighters.add(new Saiyan(name));
		else if (race == 'N')
			fighters.add(new Namekian(name));
		else if (race == 'F')
			fighters.add(new Frieza(name));
		else if (race == 'M')
			fighters.add(new Majin(name));

		if (fighters.size() == 1)
			activeFighter = fighters.get(0);

	}

	public void upgradeFighter(PlayableFighter fighter, char fighterAttribute) {
		if (fighter.getAbilityPoints() > 0) {
			fighter.setAbilityPoints(fighter.getAbilityPoints() - 1);
			if (fighterAttribute == 'H')
				fighter.setMaxHealthPoints(fighter.getMaxHealthPoints() + 50);
			else if (fighterAttribute == 'B')
				fighter.setBlastDamage(fighter.getBlastDamage() + 50);
			else if (fighterAttribute == 'P')
				fighter.setPhysicalDamage(fighter.getPhysicalDamage() + 50);
			else if (fighterAttribute == 'K')
				fighter.setMaxKi(fighter.getMaxKi() + 1);
			else if (fighterAttribute == 'S')
				fighter.setMaxStamina(fighter.getMaxStamina() + 1);
		}

	}

	public void assignAttack(PlayableFighter fighter, SuperAttack newAttack, SuperAttack oldAttack) {
		if (fighter.getSuperAttacks().size() < 4)
			if (oldAttack != null)
				fighter.getSuperAttacks().set(fighter.getSuperAttacks().indexOf(oldAttack), newAttack);
			else
				fighter.getSuperAttacks().add(newAttack);

	}

	public void assignAttack(PlayableFighter fighter, UltimateAttack newAttack, UltimateAttack oldAttack) {
		if (fighter.getUltimateAttacks().size() < 2)
			if (oldAttack != null)
				fighter.getUltimateAttacks().set(fighter.getUltimateAttacks().indexOf(oldAttack), newAttack);
			else
				fighter.getUltimateAttacks().add(newAttack);

	}
}
