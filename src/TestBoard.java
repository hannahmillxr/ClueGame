import java.util.Set;

public class TestBoard {
	private Set<TestBoardCell> adjacencyList;
	
	
	void calcTargets(TestBoardCell startCell, int pathlength) {
		
	}
	
	TestBoardCell getCell(int row, int col) {
		TestBoardCell cell = new TestBoardCell(row, col);
		return cell;
	}
	
	Set<TestBoardCell> getTargets(){
		return adjacencyList;
		
	}
}
