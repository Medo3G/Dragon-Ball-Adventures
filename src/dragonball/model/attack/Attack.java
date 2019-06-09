package dragonball.model.attack;

import dragonball.model.battle.BattleOpponent;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.Saiyan;

public abstract class Attack {
	private String name;
	private int damage;

	public Attack(String name, int damage) {
		this.name = name;
		this.damage = damage;
	}

	public String getName() {
		return name;
	}

	public int getDamage() {
		return damage;
	}

	abstract public int getAppliedDamage(BattleOpponent attacker);

	public void onUse(BattleOpponent attacker, BattleOpponent defender, boolean defenderBlocking) {
		int damage = getAppliedDamage(attacker);
		if (attacker instanceof Saiyan && ((Saiyan) attacker).isTransformed())
			damage = damage + (damage /4);
		if (defenderBlocking)
			while (((Fighter) defender).getStamina() > 0 && damage > 0) {
				damage -= 100;
				((Fighter) defender).setStamina(((Fighter) defender).getStamina() - 1);
			}
		if (damage < 0)
			damage = 0;
		((Fighter) defender).setHealthPoints(((Fighter) defender).getHealthPoints() - damage);
	}
}
