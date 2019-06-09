package dragonball.model.cell;

import java.util.EventListener;

import dragonball.model.character.fighter.NonPlayableFighter;

public interface CellListener extends EventListener {

	public void onFoeEncountered(NonPlayableFighter foe);

	public void onCollectibleFound(Collectible collectible);

}
