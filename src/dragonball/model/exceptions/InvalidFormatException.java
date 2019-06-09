package dragonball.model.exceptions;

import java.io.IOException;

abstract public class InvalidFormatException extends IOException {
	private String sourceFile;
	private int sourceLine;

	public String getSourceFile() {
		return sourceFile;
	}

	public int getSourceLine() {
		return sourceLine;
	}

	public InvalidFormatException(String sourceFile, int sourceLine) {
		super("Generic invalid format exception @ file: "+ sourceFile + " @line: "+ sourceLine +".");
		this.sourceFile = sourceFile;
		this.sourceLine = sourceLine;
	}

	public InvalidFormatException(String message, String sourceFile, int sourceLine) {

		super(message);

		this.sourceFile = sourceFile;
		this.sourceLine = sourceLine;

	}

}
