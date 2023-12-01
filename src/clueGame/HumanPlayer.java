/*
 * Author: Hannah Miller and Gillian Culberson
 * Description: Child class of player, reflects human behavior
 */

package clueGame;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class HumanPlayer extends Player {
	private boolean begin_turn = false;
	
	public HumanPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
	}
	
	
	@Override
	public Solution createSuggestion() {
		return null;
	}


	@Override
	public BoardCell selectTarget() {
		return null;
	}


	public boolean isBegin_turn() {
		return begin_turn;
	}


	public void setBegin_turn(boolean begin_turn) {
		this.begin_turn = begin_turn;
	}
	
	
	
}
