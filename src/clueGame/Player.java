/*
 * Author: Hannah Miller and Gillian Culberson
 * Description: Creates a player by storing a name, color, row, color, and whether in hand. 
 */
package clueGame;

import java.util.ArrayList;

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
