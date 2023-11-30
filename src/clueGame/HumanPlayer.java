/*
 * Author: Hannah Miller and Gillian Culberson
 * Description: Child class of player, reflects human behavior
 */

package clueGame;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class HumanPlayer extends Player {

	public HumanPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
	}
	
	
	@Override
	public Solution createSuggestion() {
		Solution suggestion = new Solution (null,null,null);
		
		for(Card r: Board.getInstance().getRoomCards()) {
			if(r.getCardName().equals(Board.getInstance().getGuessDialogBox().getRoom())) {
				suggestion.setPerson(r);
			}
		}

		for(Card p: Board.getInstance().getPersonCards()) {
			if(p.getCardName().equals(Board.getInstance().getGuessDialogBox().getPerson())) {
				suggestion.setPerson(p);
			}
		}
		
		for(Card w: Board.getInstance().getWeaponCards()) {
			if(w.getCardName().equals(Board.getInstance().getGuessDialogBox().getWeapon())) {
				suggestion.setPerson(w);
			}
		}
		
		return suggestion;
	}


	@Override
	public BoardCell selectTarget() {
		return null;
	}
	
}
