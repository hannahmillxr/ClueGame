/*
 * Author: Hannah Miller and Gillian Culberson
 * 
 */
package experiment;
import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	private int row;
	private int col;
	private boolean room;
	private boolean occupied;
	Set<TestBoardCell> adjacencyList;
	
	
	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		this.adjacencyList = new HashSet<TestBoardCell>();
	}

	public void addAdjacency(TestBoardCell cell) {
		adjacencyList.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList(){
		return this.adjacencyList;
	}
	
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	
	public boolean getOccupied() {
		return this.occupied;
	}
	
	public void setIsRoom(boolean room) {
		this.room = room;
	}
	
	public boolean isRoom() {
		return this.room;
	}
	
	public int getRow(){
		return this.row;
	}
	
	public int getCol(){
		return this.col;
	}
}
