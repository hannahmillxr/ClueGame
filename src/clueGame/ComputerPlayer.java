/*
 * Author: Hannah Miller and Gillian Culberson
 * Description: Child class of player, reflects computer behavior
 */

package clueGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	public ComputerPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
	}
	
	@Override
	public Solution createSuggestion() {
		Random random = new Random();
		//get the room player is in
		int row = this.getRow();
		int col = this.getCol();
		BoardCell thisCell = Board.getInstance().getCell(row, col);
		Card roomCard = Board.getInstance().getCard(Board.getInstance().getRoom(thisCell).getName());
		Card personCard;
		Card weaponCard;
		
		ArrayList<Card> weaponList = new ArrayList<Card>();
		ArrayList<Card> personList = new ArrayList<Card>();

		ArrayList<Card> deck = Board.getInstance().getDeck();
		for (Card card: deck) {
			CardType cardType = card.getType();
			if(cardType == CardType.PERSON) {
				boolean contains = false;
				for (Card seenCard: super.getSeenCards()) {
					if (card.equals(seenCard)) {
						contains = true;
						break;
					}
				}
				if (contains == false) {
					personList.add(card);
				}
				
			}
			if(cardType == CardType.WEAPON) {
				boolean contains = false;
				for (Card seenCard: super.getSeenCards()) {
					if (card.equals(seenCard)) {
						contains = true;
						break;
					}
				}
				if (contains == false) {
					weaponList.add(card);
				}
				
			}
		}
		
		personCard = personList.get(random.nextInt(personList.size()));
		weaponCard = weaponList.get(random.nextInt(weaponList.size()));                     

		return new Solution(roomCard, personCard, weaponCard); 
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
