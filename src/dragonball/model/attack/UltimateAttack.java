package dragonball.model.attack;

import dragonball.model.battle.BattleOpponent;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.exceptions.NotEnoughKiException;

public class UltimateAttack extends Attack  {
	public UltimateAttack(String name, int damage) {
		super(name, damage);
	}

	@Override
	public int getAppliedDamage(BattleOpponent attacker) {
		return getDamage() + ((Fighter) attacker).getBlastDamage();
	}

	@Override
	public void onUse(BattleOpponent attacker, BattleOpponent defender, boolean defenderBlocking)
			throws NotEnoughKiException {
		/*
		 * Fighter attackerFighter = (Fighter) attacker;
		 * 
		 * // only decrement ki by 1 if fighter is not a transformed saiyan if
		 * (!(attacker instanceof Saiyan && ((Saiyan)
		 * attacker).isTransformed())) { if (attackerFighter.getKi() >= 3) {
		 * attackerFighter.setKi( attackerFighter.getKi() - 3); } else throw new
		 * NotEnoughKiException( attackerFighter.getKi(), 3);
		 * 
		 * } super.onUse(attacker, defender, defenderBlocking);
		 */

		if (((Fighter) attacker).getKi() >= 3 || (attacker instanceof Saiyan && ((Saiyan) attacker).isTransformed())) {
			super.onUse(attacker, defender, defenderBlocking);
			if (!(attacker instanceof Saiyan && ((Saiyan) attacker).isTransformed()))
				((Fighter) attacker).setKi(((Fighter) attacker).getKi() - 3);
		} else
			throw new NotEnoughKiException(3,((Fighter) attacker).getKi());

	}
}
