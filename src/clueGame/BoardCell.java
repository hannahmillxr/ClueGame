package clueGame;
import java.util.HashSet;
import java.util.Set;

import experiment.TestBoardCell;

public class BoardCell {
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private Boolean roomLabel;
	private Boolean roomCenter;
	private Boolean doorway;
	private Boolean occupied;
	private char secretPassage;
	Set<BoardCell> adjacencyList;
	private Boolean isRoom;
	
	
	
	public BoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		this.roomLabel = false;
		this.roomCenter = false;
		this.doorway = false;
		this.adjacencyList = new HashSet<TestBoardCell>();
	}
	
	public BoardCell(int row, int col, char secondCharacter) {
		super();
		this.row = row;
		this.col = col;
		
		//check what second character is
		
		if (secondCharacter == '#') {
			this.roomLabel = true;
			this.roomCenter = false;
			this.doorway = false;
		}
		else if (secondCharacter == '*') {
			this.roomCenter = true;
			this.roomLabel = false;
			this.doorway = false;
		}
		else if(secondCharacter == '^'){
			this.doorway = true;
			this.doorDirection = DoorDirection.UP;
			this.roomLabel = false;
			this.roomCenter = false;
			
		}
		else if(secondCharacter == '>'){
			this.doorway = true;
			this.doorDirection = DoorDirection.RIGHT;
			this.roomLabel = false;
			this.roomCenter = false;
			
		}
		else if(secondCharacter == '<'){
			this.doorway = true;
			this.doorDirection = DoorDirection.LEFT;
			this.roomLabel = false;
			this.roomCenter = false;
			
		}
		else if(secondCharacter == 'v'){
			this.doorway = true;
			this.doorDirection = DoorDirection.DOWN;
			this.roomLabel = false;
			this.roomCenter = false;
			
		}
		//secret passage
		else {
			this.roomLabel = false;
			this.roomCenter = false;
			this.doorway = false;
			this.secretPassage = secondCharacter;
			
		}

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
		return secretPassage;
	}
	
	public char getInitial() {
		// TODO Auto-generated method stub
		return initial;
	}
	
	public void setInitial(char symbol) {
		// TODO Auto-generated method stub
		this.initial = symbol;
	}
	
	public Set<BoardCell> getAdjList(){
		return this.adjacencyList;
	}
	
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	
	public boolean getOccupied() {
		return this.occupied;
	}
}
