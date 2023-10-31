package clueGame;

public class Solution {
	Card room;
	Card person;
	Card weapon;
	
	
	public Card getSolutionRoom() {
		return room;
	}


	public Card getSolutionPerson() {
		return person;
	}


	public Card getSolutionWeapon() {
		return weapon;
	}


	public Solution(Card room, Card person, Card weapon) {
		super();
		this.room = room;
		this.person = person;
		this.weapon = weapon;
	}
}
