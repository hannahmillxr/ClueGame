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
	
	void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	
	boolean getOccupied() {
		return this.occupied;
	}
	
	void setIsRoom(boolean room) {
		this.room = room;
	}
	
	boolean isRoom() {
		return this.room;
	}
	
	int getRow(){
		return this.row;
	}
	
	int getCol(){
		return this.col;
	}
}
