package dragonball.model.exceptions;

public class NotEnoughSenzuBeansException extends NotEnoughResourcesException{
	
	public NotEnoughSenzuBeansException(){
		super("You must have at least 1 Senzu bean in order to be able to use it to restore your health and stamina.");
	}

}
