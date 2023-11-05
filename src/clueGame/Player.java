/*
 * Author: Hannah Miller and Gillian Culberson
 * Description: Creates a player by storing a name, color, row, color, and whether in hand. 
 */
package clueGame;

import java.util.ArrayList;

public abstract class Player {
	String name;
	String color;
	int row;
	int col;
	private ArrayList<Card> hand;
	private ArrayList<Card> seenCards;
	
	public Player(String name, String color, int row, int col) {
		super();
		this.name = name;
		this.color = color;
		this.row = row;
		this.col = col;
		hand = new ArrayList<Card>();
		seenCards = new ArrayList<Card>();
	}

	public abstract Solution createSuggestion();
	public abstract BoardCell selectTarget();

	public void setRow(int row) {
		this.row = row;
	}


	public void setCol(int col) {
		this.col = col;
	}


	public ArrayList<Card> getHand() {
		return hand;
	}


	public void updateHand(Card card) {
		hand.add(card);
	}

	public Card disproveSuggestion(Solution suggestion){
		ArrayList<Card> pullcard =new ArrayList<Card>();
		
		for(int i=0; i< hand.size();i++){
			if(hand.get(i).equals(suggestion.person) || hand.get(i).equals(suggestion.weapon)|| hand.get(i).equals(suggestion.room)) {
				pullcard.add(hand.get(i));
		}
	}
		Card suggestcard = null;
		
		if(pullcard.size()>0) {
			int rand = (int)(Math.random() * pullcard.size());
			suggestcard = pullcard.get(rand);
			
		}
		return suggestcard;
	}
	
	public void updateSeen(Card seenCard) {
		seenCards.add(seenCard);
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
	
	public void clearHand() {
		hand.clear();
	}

	public void clearSeen() {
		seenCards.clear();
	}


	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}
}

