package dragonball.model.cell;

public enum Collectible {
	SENZU_BEAN, DRAGON_BALL;
	
	public String toString(){
		if(this == DRAGON_BALL)
			return "Dragon Ball";
		else
			return "Senzu Bean";
	}
}
