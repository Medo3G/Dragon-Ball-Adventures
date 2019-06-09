package dragonball.model.exceptions;

public class MaximumAttacksLearnedException extends InvalidAssignAttackException{
	
	public MaximumAttacksLearnedException(){
		super("A fighter cannot learn more than 4 Super attacks and 2 Ultimate attacks.");
		
		
	}

}
