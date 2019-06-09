package dragonball.model.exceptions;

abstract public class NotEnoughResourcesException extends DragonBallException{
	
	public NotEnoughResourcesException(){
		super("There are not enough resources to perform this task.");
	}
	
	public NotEnoughResourcesException(String message){
		super(message);
	}

}
