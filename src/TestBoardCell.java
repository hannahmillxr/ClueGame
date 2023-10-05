import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	private int row;
	private int col;
	private boolean room;
	private boolean occupied;
	private Set<TestBoardCell> adjacencyList = new HashSet<TestBoardCell>();
	
	
	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}

	void addAdjacency(TestBoardCell cell) {
		
	}
	
	Set <TestBoardCell> getAdjList(TestBoardCell cell){
		return adjacencyList;
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
}
