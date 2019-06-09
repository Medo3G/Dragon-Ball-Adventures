package dragonball.model.cell;

public class EmptyCell extends Cell {
	@Override
	public String toString() {
		return "[ ]";
	}

	public void onStep() {
		// Do Nothing
	}
}
