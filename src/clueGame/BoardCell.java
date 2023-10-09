package clueGame;
import java.util.Set;

import experiment.TestBoardCell;

public class BoardCell {
	private int row;
	private int col;
	private char initial;
	Set<DoorDirection> doorDirection;
	private Boolean roomLabel;
	private Boolean roomCenter;
	private char secretPassage;
	Set<TestBoardCell> adjacencyList;
	private Boolean isRoom;
	
	public void addAdjacency(TestBoardCell cell) {
		//adjacencyList.add(cell);
	}

	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRoom() {
		return isRoom;
	}
	public void setRoom(boolean isRoom) {
		this.isRoom=isRoom;
	}
	public Object getDoorDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isLabel() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRoomCenter() {
		// TODO Auto-generated method stub
		return false;
	}

	public char getSecretPassage() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
