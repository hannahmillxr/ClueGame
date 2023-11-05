/*
 * Author: Hannah Miller and Gillian Culberson
 * Description: Child class of player, reflects computer behavior
 */

package clueGame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	public ComputerPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
	}
	
	@Override
	public Solution createSuggestion(Board card, String guessWeapon, String guessPerson, String guessRoom) {
		/*storing in the guess of room, weapon, and person*/
		this.guessWeapon = guessWeapon;
		this.guessPerson = guessPerson;
		this.guessRoom = guessRoom;
		int randnum = 0;
		/*storing all of the room, weapon, and human*/
		ArrayList<Card> room = new ArrayList<Card>();//could not find the room the player is in
		ArrayList<Card> weapon = new ArrayList<Card>();
		ArrayList<Card> human = new ArrayList<Card>();
		
		for(int i=0; i<room.size();i++) {
			if(guessRoom.equals(room)) {
				for(Card card : inseenCard) {
					if(CardType.WEAPON == card.getCardType()) {
						weapon.add(card);
						if(weapon.size()>=1) {
							Random rand = new Random();
							Collection.shuffle(weapon);
							randnum = rand.nextInt();
							guessWeapon = weapon.get(randnum);
						}
						
					}else if(CardType.Person == card.getCardType()) {
						person.add(card);
						if(person.size()>=1) {
							Random rand = new Random();
							Collection.shuffle(person);
							randnum = rand.nextInt();
							guessPerson = person.get(randnum);
					}else {
						break;
					}
				}
			}
		}
			
			
		
	}
		Card roomInformation = Card(room, deck, roomMap);
		return new Solution(roomInformation, guessPerson, guessWeapon);
}
	
		
	
	
	@Override
	public BoardCell selectTarget() {
		Set<BoardCell> targets = Board.getInstance().getTargets();
		Boolean contains = false;
		//If cell is a room we haven't tested before, then we should select this room as a target
		for(BoardCell cell : targets) {
			if(cell.isRoom()){
				Card card = Board.getInstance().getCard(Board.getInstance().getRoom(cell).getName());
				for (Card seenCard: super.getSeenCards()) {
					if (card.equals(seenCard)) {
						contains = true;
						break;
					}
					
				}
				
				if(contains == false) {
					return cell;
				}
			}
		}
		
		//if we have seen the room before, or there is no room available as a target, select randomly
		int size = targets.size();
		int item = new Random().nextInt(size); 
		int i = 0;

		for(BoardCell cell : targets)
		{
			if (i == item)
				return cell;
			i++;
		}

		// If no targets exist, return current cell
		return Board.getInstance().getCell(this.getRow(), this.getCol());


	}



}
