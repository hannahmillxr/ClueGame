import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TestBoardCell {
	private int row;
	private int col;
	private boolean room;
	private boolean occupied;
	private Map <TestBoardCell, Set<TestBoardCell>> adjacencyList;
	
	
	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		this.adjacencyList = new HashMap <TestBoardCell, Set<TestBoardCell>>();
	}

	void addAdjacency(TestBoardCell cell) {
		Set<TestBoardCell> val = adjacencyList.get(cell);
		val.add(this);
	}
	
	Set <TestBoardCell> getAdjList(TestBoardCell cell){
		return adjacencyList.get(cell);
	}
	
	void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	
	boolean getOccupied() {
		return this.occupied;
	}
	
	void setRoom(boolean room) {
		this.room = room;
	}
	
	boolean isRoom() {
		return this.room;
	}
}
