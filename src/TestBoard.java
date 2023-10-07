import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class TestBoard {
	Map <TestBoardCell, Set<TestBoardCell>> adjMtx;
	private TestBoardCell [][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
	final static int COLS = 4;
	final static int ROWS = 4;
	
	
	public TestBoard() {
		super();
		TestBoardCell [][] grid= new TestBoardCell[ROWS][COLS];
		this.targets = new HashSet<TestBoardCell>();
		this.adjMtx = new HashMap<TestBoardCell, Set<TestBoardCell>>();
		
		
		//build board
		for(int i = 0; i<ROWS;i++) {
			for(int j = 0; j<COLS;j++) {
				grid[i][j] = new TestBoardCell(i,j);
			}
		}
		
		//build adjacency list, look up down left right and make sure its within the bounds
		for (int i = 0; i < ROWS;i++) {
			for(int j = 0; j< COLS;j++) {
				TestBoardCell cell = grid[i][j];

				if (i-1 >= 0) {
					cell.addAdjacency(grid[i-1][j]);
				}
				if (j-1 >= 0) {
					cell.addAdjacency(grid[i][j-1]);
				}
				if (i+1 <COLS) {
					cell.addAdjacency(grid[i+1][j]);
				}
				if (j+1 < ROWS) {
					cell.addAdjacency(grid[i][j+1]);
				}
				
				
				//getAdjList cant be accessed and I'm not sure why
				Set<TestBoardCell>adjacencyList =  cell.getAdjList();
				adjMtx.put(cell, adjacencyList);
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
		return targets;
		
	}
}
