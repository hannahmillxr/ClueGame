import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestBoard {
	Map <TestBoardCell, Set<TestBoardCell>> adjMtx;
	private Set<TestBoardCell> adjacencyList = new HashSet<TestBoardCell>();
	private TestBoardCell [][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
	final static int COLS = 4;
	final static int ROWS = 4;
	
	
	public TestBoard(Map<TestBoardCell, Set<TestBoardCell>> adjMtx, Set<TestBoardCell> adjacencyList) {
		
	}

	void calcTargets(TestBoardCell startCell, int pathlength) {
		TestBoardCell [] visited;
		
	}
	
	TestBoardCell getCell(int row, int col) {
		TestBoardCell cell = new TestBoardCell(row, col);
		return cell;
	}
	
	Set<TestBoardCell> getTargets(){
		return adjacencyList;
		
	}
}
