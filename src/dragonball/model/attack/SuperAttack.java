package dragonball.model.attack;

import dragonball.model.battle.BattleOpponent;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.Saiyan;

public class SuperAttack extends Attack {
	public SuperAttack(String name, int damage) {
		super(name, damage);
	}

	public int getAppliedDamage(BattleOpponent attacker) {
		return this.getDamage() + ((Fighter) attacker).getBlastDamage();
	}

	public void onUse(BattleOpponent attacker, BattleOpponent defender, boolean defenderBlocking) {
		if (((Fighter) attacker).getKi() >= 1 || (attacker instanceof Saiyan && ((Saiyan) attacker).isTransformed()))
			super.onUse(attacker, defender, defenderBlocking);
		if (!(attacker instanceof Saiyan && ((Saiyan) attacker).isTransformed()))
			((Fighter) attacker).setKi(((Fighter) attacker).getKi() - 1);

	}
}
