package dragonball.model.exceptions;

public class NotEnoughAbilityPointsException extends NotEnoughResourcesException{
	
	public NotEnoughAbilityPointsException(){
		super("You must have at least 1 ability point in order to be able to level up.");
	}

}
