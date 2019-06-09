package dragonball.model.cell;

public abstract class Cell {

	private CellListener listener;

	@Override
	public abstract String toString();

	abstract public void onStep();

	public CellListener getListener() {
		return listener;
	}

	public void setListener(CellListener listener) {
		this.listener = listener;
	}
}
