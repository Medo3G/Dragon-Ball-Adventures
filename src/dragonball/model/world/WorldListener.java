package dragonball.model.world;

import java.util.EventListener;

import dragonball.model.cell.Collectible;
import dragonball.model.character.fighter.NonPlayableFighter;

public interface WorldListener extends EventListener, java.io.Serializable {
	void onFoeEncountered(NonPlayableFighter foe);

	void onCollectibleFound(Collectible collectible);
}
