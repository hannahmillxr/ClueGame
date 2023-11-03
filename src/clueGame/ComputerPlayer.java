/*
 * Author: Hannah Miller and Gillian Culberson
 * Description: Child class of player, reflects computer behavior
 */

package clueGame;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
	}
	
	
	public Solution createSuggestion(Solution solution) {
		return solution;
		
		/*While player was in the room
		 * 
		 */
		
		
	}
	public BoardCell selectTarget() {
		return null;
	}
	

}
