package dragonball.model.battle;

import dragonball.model.character.fighter.*;

public class Battle {
	private BattleOpponent me;
	private BattleOpponent foe;
	private BattleOpponent currentOpponent;

	public BattleOpponent getMe() {
		return me;
	}

	public BattleOpponent getFoe() {
		return foe;
	}

	public BattleOpponent getCurrentOpponent() {
		return currentOpponent;
	}

	public Battle(BattleOpponent me, BattleOpponent foe) {
		this.me = me;
		((Fighter) me).setHealthPoints(((Fighter) me).getMaxHealthPoints());
		((Fighter) me).setStamina(((Fighter) me).getMaxStamina());
		((Fighter) me).setKi(0);
		this.foe = foe;
		((Fighter) foe).setHealthPoints(((Fighter) foe).getMaxHealthPoints());
		((Fighter) foe).setStamina(((Fighter) foe).getMaxStamina());
		((Fighter) foe).setKi(0);
	}

}
