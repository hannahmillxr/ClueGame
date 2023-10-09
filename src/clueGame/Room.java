package clueGame;

public class Room {
	private String name;
	BoardCell centerCell;
	BoardCell labelCell;
	
	public Room(String name, BoardCell centerCell, BoardCell labelCell) {
		super();
		this.name = name;
		this.centerCell = centerCell;
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
