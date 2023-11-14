/*
 * Author: Hannah Miller and Gillian Culberson
 * Description: BoardCell class will set, input, and inizalize the Board different cells for the user

 */
package clueGame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Set;


public class BoardCell {
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private Boolean roomLabel = false;
	private Boolean roomCenter = false;
	private Boolean doorway = false;
	private Boolean occupied = false;
	private char secretPassage;
	public Set<BoardCell> adjacencyList;
	private Boolean isRoom;
	private Boolean isWalkway;
	private Boolean isSecretPassage = false;
	
	
	
	public BoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		this.adjacencyList = new HashSet<BoardCell>();
	}
	
	public BoardCell(int row, int col, char secondCharacter) {
		super();
		this.row = row;
		this.col = col;
		this.adjacencyList = new HashSet<BoardCell>();
		
		//check what second character is
		
		if (secondCharacter == '#') {
			this.roomLabel = true;

		}
		else if (secondCharacter == '*') {
			this.roomCenter = true;

		}
		else if(secondCharacter == '^'){
			this.doorway = true;
			this.doorDirection = DoorDirection.UP;	
		}
		else if(secondCharacter == '>'){
			this.doorway = true;
			this.doorDirection = DoorDirection.RIGHT;			
		}
		else if(secondCharacter == '<'){
			this.doorway = true;
			this.doorDirection = DoorDirection.LEFT;
		}
		else if(secondCharacter == 'v'){
			this.doorway = true;
			this.doorDirection = DoorDirection.DOWN;
		}
		//secret passage
		else {
			this.isSecretPassage = true;
			this.secretPassage = secondCharacter;			
		}
	}
	
	public boolean equals(BoardCell target) {
		if (this.row == target.getRow()) {
			if (this.col == target.getCol()) {
				return true;
			}
		}
		return false;
		
	}
	
	public void draw(Graphics g, int cellsize) {
		if (this.isSecretPassage) {
			g.setColor(Color.CYAN);
		}
		else if (this.isWalkway) {
			g.setColor(Color.GRAY);

		}
		else if (this.isDoorway()) {
			g.setColor(Color.GRAY);
			
		}
		else if (this.isRoom) {
			g.setColor(Color.MAGENTA.darker());
		}
		//unused set black
		else {
			g.setColor(Color.BLACK);
		}
		g.fillRect( this.getCol()*cellsize, this.getRow()* cellsize , cellsize, cellsize);

		if(this.isWalkway) {
			g.setColor(Color.BLACK);
			g.drawRect( this.getCol()*cellsize, this.getRow()* cellsize , cellsize, cellsize);
		}
		if(this.isSecretPassage) {
			g.setColor(Color.BLACK);
			g.drawRect( this.getCol()*cellsize, this.getRow()* cellsize , cellsize, cellsize);
		}
		

		g.setColor(Color.BLUE);
		if(this.isDoorway()) {
			if (this.getDoorDirection() == DoorDirection.UP) {
				g.fillRect(col * cellsize, row * cellsize, cellsize, cellsize / 5);
			}
			if (this.getDoorDirection() == DoorDirection.DOWN) {
				g.fillRect(col * cellsize, row * cellsize + (4 * (cellsize / 5)), cellsize, cellsize / 5);
	     		
			}
			if (this.getDoorDirection() == DoorDirection.LEFT) {
				g.fillRect(col * cellsize, row * cellsize, cellsize / 5, cellsize);
	            
			}
			if (this.getDoorDirection() == DoorDirection.RIGHT) {
				g.fillRect(col * cellsize + (4 * (cellsize / 5)), row * cellsize, cellsize / 5, cellsize);
			}
		}		

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
		return roomCenter;
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
	
	public boolean isWalkway() {
		return this.isWalkway;
	}
	
	public void setWalkway(boolean walkway) {
		this.isWalkway=walkway;
	}
	public int getRow(){
		return this.row;
	}
	
	public int getCol(){
		return this.col;
	}
	public boolean getOccupied() {
		return this.occupied;
	}

	public char getSecretPassage() {
		
		return this.secretPassage;
	}
	
	public Boolean getIsSecretPassage() {
		return isSecretPassage;
	}

	public char getInitial() {
		return initial;
	}
	
}
