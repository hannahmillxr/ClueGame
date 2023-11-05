/*
 * Author: Hannah Miller and Gillian Culberson
 * Description: Child class of player, reflects human behavior
 */

package clueGame;

public class HumanPlayer extends Player {

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

}
