package dragonball.model.exceptions;

public class UnknownAttackTypeException extends InvalidFormatException {

	private String unknownType;

	public String getUnknownType() {
		return unknownType;
	}

	public UnknownAttackTypeException(String sourceFile, int sourceLine, String type){
		super(type + " is not a valid attack type. @ file: "+ sourceFile + " @ line: " + sourceLine + ".",sourceFile,sourceLine);
		this.unknownType = type;
		
	}
	
	public UnknownAttackTypeException(String message,String sourceFile, int sourceLine, String type){
		super(message,sourceFile,sourceLine);
		this.unknownType = type;
		
	}
}
