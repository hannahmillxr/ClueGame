import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestBoard {
	Map <TestBoardCell, Set<TestBoardCell>> adjMtx;
	private Set<TestBoardCell> adjacencyList= new HashSet<TestBoardCell>();
	/**private TestBoardCell [][] boardArray;**/
	
	
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
