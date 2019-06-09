package dragonball.model.cell;

import java.util.EventListener;

import dragonball.model.character.fighter.NonPlayableFighter;

public interface CellListener extends EventListener, java.io.Serializable {
	void onFoeEncountered(NonPlayableFighter foe);

	void onCollectibleFound(Collectible collectible);
}
