package game;

public class Player {
	
	private String name;
	protected boolean KI=false;
	private int spielSteineSpieler = 0;
	
	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setSpielSteineSpieler(int spielSteineSpieler) {
		this.spielSteineSpieler = spielSteineSpieler;
	}

	public int getSpielSteineSpieler() {
		return spielSteineSpieler;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name + ": " + spielSteineSpieler;
	}
	
	public boolean getKI(){
		return KI;
	}
}
