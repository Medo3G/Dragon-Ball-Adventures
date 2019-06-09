package dragonball.model.attack;

import dragonball.model.battle.BattleOpponent;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.Saiyan;

public class SuperSaiyan extends UltimateAttack {
	public SuperSaiyan() {
		super("Super Saiyan", 0);
	}

	public int getAppliedDamage(BattleOpponent attacker) {
		return 0;
	}

	public void onUse(BattleOpponent attacker, BattleOpponent defender, boolean defenderBlocking) {

		if (((Fighter) attacker).getKi() >= 3 && attacker instanceof Saiyan)
			((Saiyan) attacker).setTransformed(true);

	}
}
