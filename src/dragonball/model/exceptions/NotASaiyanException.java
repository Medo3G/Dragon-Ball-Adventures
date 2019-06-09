package dragonball.model.exceptions;

public class NotASaiyanException extends InvalidAssignAttackException{
	
	public NotASaiyanException(){
		super("You cannot assign the attack Super Saiyan to a fighter whos class is not Saiyan");
		
	}

}
