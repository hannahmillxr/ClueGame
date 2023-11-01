package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Player {
	String name;
	String color;
	int row;
	int col;
	private ArrayList<Card> hand;
	
	public Player(String name, String color, int row, int col) {
		super();
		this.name = name;
		this.color = color;
		this.row = row;
		this.col = col;
		hand = new ArrayList<Card>();
	}


	public ArrayList<Card> getHand() {
		return hand;
	}


	public void updateHand(Card card) {
		hand.add(card);
	}


	public String getName() {
		return name;
	}


	public String getColor() {
		return color;
	}


	public int getRow() {
		return row;
	}


	public int getCol() {
		return col;
	}
	
	
}
