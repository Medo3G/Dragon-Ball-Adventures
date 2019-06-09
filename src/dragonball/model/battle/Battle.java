package dragonball.model.battle;

import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.player.Player;

import java.util.ArrayList;
import java.util.Random;

import dragonball.model.attack.*;
import dragonball.model.cell.Collectible;

public class Battle {
	private BattleOpponent me;
	private BattleOpponent foe;
	private BattleOpponent attacker;
	private boolean meBlocking;
	private boolean foeBlocking;
	private BattleListener listener;

	public Battle(BattleOpponent me, BattleOpponent foe) {
		this.me = me;
		this.foe = foe;
		this.attacker = me;

		// set current values appropriately
		Fighter meFighter = (Fighter) me;
		meFighter.setHealthPoints(meFighter.getMaxHealthPoints());
		meFighter.setKi(0);
		meFighter.setStamina(meFighter.getMaxStamina());

		Fighter foeFighter = (Fighter) foe;
		foeFighter.setHealthPoints(foeFighter.getMaxHealthPoints());
		foeFighter.setKi(0);
		foeFighter.setStamina(foeFighter.getMaxStamina());

		if (me instanceof Saiyan)
			((Saiyan) me).setTransformed(false);
	}

	public BattleOpponent getMe() {
		return me;
	}

	public BattleOpponent getFoe() {
		return foe;
	}

	public BattleOpponent getAttacker() {
		return attacker;
	}

	public boolean isMeBlocking() {
		return meBlocking;
	}

	public boolean isFoeBlocking() {
		return foeBlocking;
	}

	public BattleListener getListener() {
		return listener;
	}

	public void setListener(BattleListener listener) {
		this.listener = listener;
	}

	public ArrayList<Attack> getAssignedAttacks() {
		ArrayList<Attack> assigned = new ArrayList<>();
		assigned.add(new PhysicalAttack());
		assigned.addAll(((Fighter) attacker).getSuperAttacks());
		assigned.addAll(((Fighter) attacker).getUltimateAttacks());
		return assigned;

	}

	public void attack(Attack attack) {
		if (attacker == me)
			attack.onUse(me, foe, isFoeBlocking());
		else
			attack.onUse(foe, me, isMeBlocking());
		if (listener != null)
			listener.onBattleEvent(new BattleEvent(this, BattleEventType.ATTACK, attack));
		endTurn();
	}

	public void block() {
		if (attacker == me)
			meBlocking = true;
		else
			foeBlocking = true;
		if (listener != null)
			listener.onBattleEvent(new BattleEvent(this, BattleEventType.BLOCK));
		endTurn();
	}

	public void use(Player player, Collectible collectible) {
		if (player.getSenzuBeans() > 0) {
			player.getActiveFighter().setHealthPoints(player.getActiveFighter().getMaxHealthPoints());
			player.getActiveFighter().setStamina(player.getActiveFighter().getMaxStamina());
			player.setSenzuBeans(player.getSenzuBeans() - 1);
			if (listener != null)
				listener.onBattleEvent(new BattleEvent(this, BattleEventType.USE, Collectible.SENZU_BEAN));
			endTurn();

		}
	}

	public BattleOpponent getDefender() {
		if (attacker == me)
			return foe;
		else
			return me;
	}

	public void start() {
		attacker = me;
		// Check if onTurn methods should be invoked Answer:NO
		if (listener != null)
			listener.onBattleEvent(new BattleEvent(this, BattleEventType.STARTED));
	}

	public void switchTurn() {
		attacker = getDefender();
	}

	public void play() {
		/* 
		if (((50 + ((Fighter) foe).getPhysicalDamage()) >= ((Fighter) me).getHealthPoints() && !meBlocking)
				|| ((50 + ((Fighter) foe).getPhysicalDamage()) >= (((Fighter) me).getHealthPoints()
						+ ((Fighter) me).getStamina() * 100) && meBlocking)) {
			attack(getAssignedAttacks().get(0)); // Kill player if possible with
													// a physical attack
			return;
		}*/ //Disabled for now since it fails tests

		int defendOrAttack = new Random().nextInt(4); // 25% chance to block
		if (defendOrAttack == 0 /*&& ((Fighter) attacker).getStamina() > 0 */ )   { //Disabled for now since it fails tests
			block();
			return;
		}
		if (((Fighter) foe).getKi() == 0) {
			attack(getAssignedAttacks().get(0)); // If must attack but have no
													// Ki, do a physical attack
			return;
		}
		while (true) {
			int attackIndex = new Random().nextInt(getAssignedAttacks().size());
			Attack attackAI = getAssignedAttacks().get(attackIndex);
			if (attackAI instanceof PhysicalAttack || (attackAI instanceof SuperAttack && ((Fighter) foe).getKi() >= 1)
					|| attackAI instanceof UltimateAttack && ((Fighter) foe).getKi() >= 3) {
				attack(attackAI);
				break;
			}
		}

	}

	public void endTurn() {
		if (((Fighter) getDefender()).getHealthPoints() == 0) {
			if (listener != null)
				listener.onBattleEvent(new BattleEvent(this, BattleEventType.ENDED, attacker));
			return;
		}
		switchTurn();
		attacker.onAttackerTurn();
		getDefender().onDefenderTurn();
		if (attacker == me)
			meBlocking = false;
		else
			foeBlocking = false;
		/*
		if (attacker == foe) {
			play(); // Start AI turn if it's its turn
			return;
		} */
		if (listener != null)
			listener.onBattleEvent(new BattleEvent(this, BattleEventType.NEW_TURN)); //NEW_TURN denotes start of player turn only? Test says NO

	}

}
