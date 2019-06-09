package dragonball.model.cell;

import dragonball.model.character.fighter.NonPlayableFighter;

public class FoeCell extends Cell {
	private NonPlayableFighter foe;

	public FoeCell(NonPlayableFighter foe) {
		this.foe = foe;
	}

	public NonPlayableFighter getFoe() {
		return foe;
	}

	public void setFoe(NonPlayableFighter foe) {
		this.foe = foe;
	}

	@Override
	public String toString() {
		if (foe.isStrong()) {
			return "[b]";
		} else {
			return "[w]";
		}
	}

	public void onStep() {
		if (getListener() != null)
			getListener().onFoeEncountered(foe);
	}
}
