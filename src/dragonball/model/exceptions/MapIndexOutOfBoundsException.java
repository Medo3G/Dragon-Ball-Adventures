package dragonball.model.exceptions;

public class MapIndexOutOfBoundsException extends IndexOutOfBoundsException{
	
	private int row;
	private int column;
	
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	
	
	public MapIndexOutOfBoundsException(int row, int column){
		super("You cannot move the fighter to row: " + row + ", column: "+ column + "." );
		this.row = row;
		this.column = column;
	}
	

}
