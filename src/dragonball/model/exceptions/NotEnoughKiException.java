package dragonball.model.exceptions;

public class NotEnoughKiException extends NotEnoughResourcesException {
	
	private int availableKi;
	private int requiredKi;
	
	public NotEnoughKiException(int requiredKi, int availableKi){
		super("You need "+ requiredKi +" Ki to perform this attack. You only have " + availableKi+ ".");
		this.requiredKi = requiredKi;
		this.availableKi = availableKi;
	}

	public int getAvailableKi() {
		return availableKi;
	}

	public int getRequiredKi() {
		return requiredKi;
	}
	
	
}
