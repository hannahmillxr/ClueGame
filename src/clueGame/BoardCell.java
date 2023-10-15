package clueGame;
import java.util.HashSet;
import java.util.Set;


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
	private Boolean isWalkway;
	private Boolean isSecretPassage;
	
	
	
	public BoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		this.roomLabel = false;
		this.roomCenter = false;
		this.doorway = false;
		this.isSecretPassage = false;
		this.occupied = false;
		this.adjacencyList = new HashSet<BoardCell>();
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
			this.isSecretPassage = false;
		}
		else if (secondCharacter == '*') {
			this.roomCenter = true;
			this.roomLabel = false;
			this.doorway = false;
			this.isSecretPassage = false;
		}
		else if(secondCharacter == '^'){
			this.doorway = true;
			this.doorDirection = DoorDirection.UP;
			this.roomLabel = false;
			this.roomCenter = false;
			this.isSecretPassage = false;
			
		}
		else if(secondCharacter == '>'){
			this.doorway = true;
			this.doorDirection = DoorDirection.RIGHT;
			this.roomLabel = false;
			this.roomCenter = false;
			this.isSecretPassage = false;
			
		}
		else if(secondCharacter == '<'){
			this.doorway = true;
			this.doorDirection = DoorDirection.LEFT;
			this.roomLabel = false;
			this.roomCenter = false;
			this.isSecretPassage = false;
			
		}
		else if(secondCharacter == 'v'){
			this.doorway = true;
			this.doorDirection = DoorDirection.DOWN;
			this.roomLabel = false;
			this.roomCenter = false;
			this.isSecretPassage = false;
			
		}
		//secret passage
		else {
			this.roomLabel = false;
			this.roomCenter = false;
			this.doorway = false;
			this.isSecretPassage = true;
			this.secretPassage = secondCharacter;
			
		}

		this.adjacencyList = new HashSet<BoardCell>();

		this.occupied = false;
	}

	public int getRow(){
		return this.row;
	}
	
	public int getCol(){
		return this.col;
	}
	
	public void addAdjacency(BoardCell cell) {
		adjacencyList.add(cell);
	}

	public boolean isDoorway() {
		return this.doorway;
	}

	public boolean isRoom() {
		return this.isRoom;
	}
	
	public void setRoom(boolean isRoom) {
		this.isRoom=isRoom;
	}
	
	public boolean isSecretPass() {
		return isSecretPassage;
	}
	
	public void setIsSecretPassage(boolean pass) {
		this.isSecretPassage=pass;
	}
	
	public Object getDoorDirection() {
	
		return doorDirection;
	}

	public boolean isLabel() {
		
		return roomLabel;
	}

	public boolean isRoomCenter() {
		// TODO Auto-generated method stub
		return roomCenter;
	}

	public char getSecretPassage() {
		
		return this.secretPassage;
	}
	
	public char getInitial() {
		return initial;
	}
	
	public void setInitial(char symbol) {
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
	
	public boolean isWalkway() {
		return this.isWalkway;
	}
	
	public void setWalkway(boolean walkway) {
		this.isWalkway=walkway;
	}
}
