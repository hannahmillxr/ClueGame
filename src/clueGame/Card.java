/*
 * Author: Hannah Miller and Gillian Culberson
 * Description: Card class will set, input, and inizalize the Board for the user
 */

package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	
	public boolean equals(Card target) {
		if (this.cardName == target.getCardName()) {
			return true;
		}
		return false;
		
	}

	public Card(String cardName, CardType type) {
		super();
		this.cardName = cardName;
		this.type = type;
	}

	public String getCardName() {
		return cardName;
	}

	public CardType getType() {
		return type;
	}
	
	
	
	
}
