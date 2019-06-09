package dragonball.model.exceptions;

abstract public class DragonBallException extends Exception {
	
	public DragonBallException(){
		super("A generic DragonBall game exception has occured.");
	}
	
	public DragonBallException(String message){
		super(message);
	}

}
