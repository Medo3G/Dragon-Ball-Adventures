package dragonball.model.cell;

public class CollectibleCell extends Cell {

	private Collectible collectible;

	public CollectibleCell(Collectible collectible) {
		this.collectible = collectible;
	}

	public Collectible getCollectible() {
		return collectible;
	}

	public String toString() {
		if (collectible.equals(Collectible.DRAGON_BALL))
			return "[d]";
		else
			return "[s]";
	}

}
