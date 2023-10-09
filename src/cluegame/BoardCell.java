package cluegame;
import java.util.Set;

import experiment.TestBoardCell;

public class BoardCell {
	private int row;
	private int col;
	private char initial;
	Set<DoorDirection> doorDirection;
	private Boolean roomLabel;
	private Boolean roomCenter;
	private char secretPassage;
	Set<TestBoardCell> adjacencyList;
	
	public void addAdjacency(TestBoardCell cell) {
		adjacencyList.add(cell);
	}
}
