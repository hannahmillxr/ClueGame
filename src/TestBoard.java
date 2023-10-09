import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;


public class TestBoard {
	Map <TestBoardCell, Set<TestBoardCell>> adjMtx;
	private TestBoardCell [][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
	final static int COLS = 4;
	final static int ROWS = 4;
	
	
	public TestBoard() {
		super();
		this.grid= new TestBoardCell[ROWS][COLS];
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
				TestBoardCell cell = this.getCell(i, j);

				if (i-1 >= 0) {
					cell.addAdjacency(this.getCell(i-1, j));
				}
				if (j-1 >= 0) {
					cell.addAdjacency(this.getCell(i, j-1));
				}
				if (i+1 <COLS) {
					cell.addAdjacency(this.getCell(i+1, j));
					//cell.addAdjacency(grid[i+1][j]);
				}
				if (j+1 < ROWS) {
					cell.addAdjacency(this.getCell(i, j+1));
					//cell.addAdjacency(grid[i][j+1]);
				}
				
				Set<TestBoardCell> currentAdjacencyList =  cell.getAdjList();		
				adjMtx.put(cell, currentAdjacencyList);
			}
		}
			
		
	}

	void calcTargets(TestBoardCell startCell, int pathlength) {
		TestBoardCell [] visited;
		
	}
	
	TestBoardCell getCell(int row, int col) {
		return grid[row][col];
	}
	
	Set<TestBoardCell> getTargets(){
		return targets;
		
	}

	
	
	public static void main(String[] args) {
        TestBoard board = new TestBoard();
        TestBoardCell cell = board.getCell(1, 3);
		Set<TestBoardCell> testList = cell.getAdjList();
		System.out.println(testList.size());
    }
}
