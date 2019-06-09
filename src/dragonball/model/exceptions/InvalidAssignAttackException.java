package dragonball.model.exceptions;

abstract public class InvalidAssignAttackException extends DragonBallException{
	
	public InvalidAssignAttackException(){
		super("It is invalid to assign a specific attack to the fighter in question.");
		
	}
	
	public InvalidAssignAttackException(String message){
		super(message);
	}

}
