package clueGame;

public class Solution {
	Card room;
	Card person;
	Card weapon;
	
	
	public void setRoom(Card room) {
		this.room = room;
	}


	public void setPerson(Card person) {
		this.person = person;
	}


	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}


	public Card getSolutionRoom() {
		return room;
	}


	public Card getSolutionPerson() {
		return person;
	}


	public Card getSolutionWeapon() {
		return weapon;
	}


	public Solution() {
		super();
	}
}