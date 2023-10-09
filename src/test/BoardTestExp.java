package test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import experiment.TestBoard;
import experiment.TestBoardCell;

import experiment.*;
class BoardTestExp {
	TestBoard board;
	
	@BeforeEach
	public void setUp() {
		board = new TestBoard();
	}
	
	@Test
	public void testTopLeftCornerAdjacency() {
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertEquals(2, testList.size());
		Assert.assertTrue(testList.contains(board.getCell(1,0)));
		Assert.assertTrue(testList.contains(board.getCell(0,1)));
		
	}
	
	@Test
	public void testBottomRightCornerAdjacency() {
		TestBoardCell cell = board.getCell(3, 3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(3,2)));
		Assert.assertTrue(testList.contains(board.getCell(2,3)));
		Assert.assertEquals(2, testList.size());
		
	}
	
	@Test
	public void testRightEdgeAdjacency() {
		TestBoardCell cell = board.getCell(1, 3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(0,3)));
		Assert.assertTrue(testList.contains(board.getCell(1,2)));
		Assert.assertTrue(testList.contains(board.getCell(2,3)));
		Assert.assertEquals(3, testList.size());
		
	}
	
	
	@Test
	public void testBottomLeftEdgeAdjacency() {
		TestBoardCell cell = board.getCell(3, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2,0)));
		Assert.assertTrue(testList.contains(board.getCell(3,1)));
		Assert.assertEquals(2, testList.size());
		
	}
	
	
	@Test
	public void testLeftEdgeAdjacency() {
		TestBoardCell cell = board.getCell(2, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2,1)));
		Assert.assertTrue(testList.contains(board.getCell(1,0)));
		Assert.assertTrue(testList.contains(board.getCell(3,0)));
		Assert.assertEquals(3, testList.size());
		
	}
	
	@Test
	public void testTargetsNormalTopLeftCorner() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3,0)));
		Assert.assertTrue(targets.contains(board.getCell(2,1)));
		Assert.assertTrue(targets.contains(board.getCell(0,1)));
		Assert.assertTrue(targets.contains(board.getCell(1,2)));
		Assert.assertTrue(targets.contains(board.getCell(0,3)));
		Assert.assertTrue(targets.contains(board.getCell(1,0)));
		
	}
	
	
	@Test
	public void testTargetsNormalBottomRightCorner() {
		TestBoardCell cell = board.getCell(3, 3);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
	
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1,3)));
		Assert.assertTrue(targets.contains(board.getCell(2,2)));
		Assert.assertTrue(targets.contains(board.getCell(3,1)));
		
	}
	
	@Test
	public void testTargetsNormalBottomRightCornerThreeMoves() {
		TestBoardCell cell = board.getCell(3, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		
		
		for (TestBoardCell currentCell: targets ) {
			System.out.println(currentCell.getRow());
			System.out.println(currentCell.getCol());
			System.out.println();
		}		
		
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0,3)));
		Assert.assertTrue(targets.contains(board.getCell(1,2)));
		Assert.assertTrue(targets.contains(board.getCell(2,1)));
		Assert.assertTrue(targets.contains(board.getCell(2,3)));
		Assert.assertTrue(targets.contains(board.getCell(3,0)));
		Assert.assertTrue(targets.contains(board.getCell(3,2)));
		
	}
	
	@Test
	public void testTargetsNormalBottomLeftCornerTwoMoves() {
		TestBoardCell cell = board.getCell(3, 0);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1,0)));
		Assert.assertTrue(targets.contains(board.getCell(2,1)));
		Assert.assertTrue(targets.contains(board.getCell(3,2)));
		
	}
	
	@Test
	public void testTargetsNormalBottomLeftCornerThreeMoves() {
		TestBoardCell cell = board.getCell(3, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0,0)));
		Assert.assertTrue(targets.contains(board.getCell(1,1)));
		Assert.assertTrue(targets.contains(board.getCell(2,2)));
		Assert.assertTrue(targets.contains(board.getCell(3,3)));
		Assert.assertTrue(targets.contains(board.getCell(2,0)));
		Assert.assertTrue(targets.contains(board.getCell(3,1)));
		
	}
	
	@Test
	public void testTargetsNormalMiddle() {
		TestBoardCell cell = board.getCell(1, 1);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0,0)));
		Assert.assertTrue(targets.contains(board.getCell(0,2)));
		Assert.assertTrue(targets.contains(board.getCell(2,0)));
		Assert.assertTrue(targets.contains(board.getCell(2,2)));
		Assert.assertTrue(targets.contains(board.getCell(1,3)));
		Assert.assertTrue(targets.contains(board.getCell(3,1)));
		
	}
	
	@Test
	public void testTargetsNormalMiddleOneMove() {
		TestBoardCell cell = board.getCell(1, 2);
		board.calcTargets(cell, 1);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0,2)));
		Assert.assertTrue(targets.contains(board.getCell(1,1)));
		Assert.assertTrue(targets.contains(board.getCell(1,3)));
		Assert.assertTrue(targets.contains(board.getCell(2,2)));
		
	}
	
	@Test
	public void testTargetsRoomOccupied() {
		board.getCell(0, 2).setOccupied(true);
		TestBoardCell cell = board.getCell(0, 3);
		
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1,1)));
		Assert.assertTrue(targets.contains(board.getCell(2,2)));
		Assert.assertTrue(targets.contains(board.getCell(3,3)));
		
	}
	
	@Test
	public void testTargetsRoomOccupiedBottomLeft() {
		board.getCell(2, 0).setOccupied(true);
		TestBoardCell cell = board.getCell(3, 0);
		board.calcTargets(cell, 1);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3,1)));
		
	}
	
	@Test
	public void testTargetsRoomIsRoom() {
		board.getCell(0, 2).setIsRoom(true);
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();

		
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1,1)));
		Assert.assertTrue(targets.contains(board.getCell(0,2)));
		Assert.assertTrue(targets.contains(board.getCell(2,2)));
		Assert.assertTrue(targets.contains(board.getCell(3,3)));
		
	}
	
	@Test
	public void testTargetsRoomIsRoomBottomLeft() {
		board.getCell(2, 0).setIsRoom(true);
		TestBoardCell cell = board.getCell(3, 0);
		board.calcTargets(cell, 1);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3,1)));
		Assert.assertTrue(targets.contains(board.getCell(2,0)));
		
	}
	
	@Test
	public void testTargetsRoomMixed() {
		board.getCell(1, 2).setIsRoom(true);
		board.getCell(0, 2).setOccupied(true);
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1,2)));
		Assert.assertTrue(targets.contains(board.getCell(2,2)));
		Assert.assertTrue(targets.contains(board.getCell(3,3)));
		
	}
	
	@Test
	public void testTargetsRoomMixedMiddle() {
		board.getCell(1, 0).setIsRoom(true);
		board.getCell(0, 1).setOccupied(true);
		TestBoardCell cell = board.getCell(1, 1);
		board.calcTargets(cell, 1);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1,2)));
		Assert.assertTrue(targets.contains(board.getCell(1,0)));
		Assert.assertTrue(targets.contains(board.getCell(2,1)));
		
	}
	


}
