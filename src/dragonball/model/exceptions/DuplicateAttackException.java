package dragonball.model.exceptions;

import dragonball.model.attack.Attack;

public class DuplicateAttackException extends InvalidAssignAttackException {

	private Attack attack;

	public Attack getAttack() {
		return attack;
	}

	public DuplicateAttackException(Attack attack) {
		super(attack.getName() + " already exists in this fighters list of assigned attacks");
		this.attack = attack;
	}

}
