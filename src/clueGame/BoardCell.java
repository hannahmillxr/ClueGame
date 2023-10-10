package clueGame;
import java.util.HashSet;
import java.util.Set;

import experiment.TestBoardCell;

public class BoardCell {
	private int row;
	private int col;
	private char initial;
	Set<DoorDirection> doorDirection;
	private Boolean roomLabel;
	private Boolean roomCenter;
	private Boolean doorway;
	private char secretPassage;
	Set<TestBoardCell> adjacencyList;
	private Boolean isRoom;
	
	
	
	public BoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		this.roomLabel = false;
		this.roomCenter = false;
		this.adjacencyList = new HashSet<TestBoardCell>();
	}
	
	public BoardCell(int row, int col, char secondCharacter) {
		super();
		this.row = row;
		this.col = col;
		
		//check what second character is 
		this.roomLabel = false;
		this.roomCenter = false;
		this.adjacencyList = new HashSet<TestBoardCell>();
	}

	
	public void addAdjacency(TestBoardCell cell) {
		//adjacencyList.add(cell);
	}

	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return doorway;
	}

	public boolean isRoom() {
		return isRoom;
	}
	
	public void setRoom(boolean isRoom) {
		this.isRoom=isRoom;
	}
	
	public Object getDoorDirection() {
		// TODO Auto-generated method stub
		return doorDirection;
	}

	public boolean isLabel() {
		// TODO Auto-generated method stub
		return roomLabel;
	}

	public boolean isRoomCenter() {
		// TODO Auto-generated method stub
		return roomCenter;
	}

	public char getSecretPassage() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
