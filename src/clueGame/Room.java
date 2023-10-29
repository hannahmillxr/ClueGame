/*
 * Author: Hannah Miller and Gillian Culberson
 * Description: Room class will get and set the different labels that are defined in the cells

 */
package clueGame;

public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	
	
	public Room() {
		super();
	}
	
	public Room(String name) {
		super();
		this.name = name;

	}
	
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}

	public String getName() {
		return name;
	}
	public BoardCell getCenterCell() {
		return centerCell;
	}
	public BoardCell getLabelCell() {
		return labelCell;
	}
	
}
