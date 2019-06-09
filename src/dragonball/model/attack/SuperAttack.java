package dragonball.model.attack;

import dragonball.model.battle.BattleOpponent;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.exceptions.NotEnoughKiException;

public class SuperAttack extends Attack implements java.io.Serializable {
	public SuperAttack(String name, int damage) {
		super(name, damage);
	}

	@Override
	public int getAppliedDamage(BattleOpponent attacker) {
		return getDamage() + ((Fighter) attacker).getBlastDamage();
	}

	@Override
	public void onUse(BattleOpponent attacker, BattleOpponent defender, boolean defenderBlocking)
			throws NotEnoughKiException {
		if (((Fighter) attacker).getKi() >= 1 || (attacker instanceof Saiyan && ((Saiyan) attacker).isTransformed())) {
			super.onUse(attacker, defender, defenderBlocking);
			if (!(attacker instanceof Saiyan && ((Saiyan) attacker).isTransformed()))
				((Fighter) attacker).setKi(((Fighter) attacker).getKi() - 1);
		} else
			throw new NotEnoughKiException(1, ((Fighter) attacker).getKi());
	}
}
