/*
 * Author: Hannah Miller and Gillian Culberson
 * 
 */
package experiment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;


public class TestBoard {
	Map <TestBoardCell, Set<TestBoardCell>> adjMtx;
	private TestBoardCell [][] grid;
	private Set<TestBoardCell> targets = new HashSet <TestBoardCell>();
	private Set<TestBoardCell> visited = new HashSet <TestBoardCell>();
	
	final static int COLS = 4;
	final static int ROWS = 4;
	
	
	public TestBoard() {
		super();
		this.grid= new TestBoardCell[ROWS][COLS];
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
				}
				if (j+1 < ROWS) {
					cell.addAdjacency(this.getCell(i, j+1));
				}
				
				Set<TestBoardCell> currentAdjacencyList =  cell.getAdjList();		
				adjMtx.put(cell, currentAdjacencyList);
			}
		}
			
		
	}

	public void calcTargets(TestBoardCell startCell, int pathlength) { 
		int numSteps = pathlength;
		
		visited.add(startCell);
		
		for (TestBoardCell cell : startCell.getAdjList()) {
			if (!visited.contains(cell)) {
				
				visited.add(cell);
				
				if (cell.getOccupied()) {
					continue;
				}
				
				else if (cell.isRoom()){
					targets.add(cell);
				}
				
				else if (numSteps == 1) {
					targets.add(cell);
				}	
				
				else {
					calcTargets(cell, numSteps-1);
				}	
				
				visited.remove(cell);
			}
			
			

			
		}
		
	}
	
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
	}
	
	public Set<TestBoardCell> getTargets(){
		return targets;
		
	}

}
