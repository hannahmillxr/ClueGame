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
		super();
		TestBoardCell [][] grid= new TestBoardCell[ROWS][COLS];
		
		for(int i = 0; i<ROWS;i++) {
			for(int j = 0; j<COLS;j++) {
				grid[i][j] = new TestBoardCell(i,j);
			}
		}
		for (int i = 0; i < ROWS;i++) {
			for(int j = 0; j< COLS;j++) {
			
				//create an if statement that will go through each up,down,left,right of the statement if not then will add cell to adjectency list.	
			//get the cls and rows and chenck -1 if it is in bounds if it is set ad to the location.
				if() {
				//Send cell
			}else if(){
				
			}else if(){
				
			}else if(){
				
			}else {
				
			}
			}
		
		
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
