package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("newClueBoardcsv.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		// First, the kitchen that has two doors and a secret room
		Set<BoardCell> testList = board.getAdjList(3, 2);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(6, 3)));
		assertTrue(testList.contains(board.getCell(1, 6)));
		assertTrue(testList.contains(board.getCell(21, 10)));
		
		// now test the Tea Room which only has one door (note not marked since multiple test here)
		testList = board.getAdjList(4, 8);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(7, 9)));
		
		// one more room, the Cherry Blossom Room, which has two doors
		testList = board.getAdjList(9, 24);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(9, 18)));
		assertTrue(testList.contains(board.getCell(13, 26)));
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		//door into bedrooms
		Set<BoardCell> testList = board.getAdjList(8, 6);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(9, 6)));
		assertTrue(testList.contains(board.getCell(8, 7)));
		assertTrue(testList.contains(board.getCell(7, 6)));
		assertTrue(testList.contains(board.getCell(10, 3)));//room center
		
		//door into meditation room, single file hallway
		testList = board.getAdjList(6, 26);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(6, 27)));
		assertTrue(testList.contains(board.getCell(6, 25)));
		assertTrue(testList.contains(board.getCell(2, 24))); //room center
		
		
		//door down into the scroll room, next to unused space
		testList = board.getAdjList(14, 1);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(20, 2)));//room center
		assertTrue(testList.contains(board.getCell(13, 1)));
		assertTrue(testList.contains(board.getCell(14, 2)));
	}
	
	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on bottom edge of board, next to two unused spaces, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(24, 5);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(23, 5)));
		
		// Test near a door but not adjacent
		testList = board.getAdjList(18, 5);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(17, 5)));
		assertTrue(testList.contains(board.getCell(19, 5)));
		assertTrue(testList.contains(board.getCell(18, 6)));
		assertTrue(testList.contains(board.getCell(18, 4)));

		// Test adjacent to walkways
		testList = board.getAdjList(9, 9);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(8, 9)));
		assertTrue(testList.contains(board.getCell(10, 9)));
		assertTrue(testList.contains(board.getCell(9, 8)));
		assertTrue(testList.contains(board.getCell(9, 10)));

		// Test next to closet
		testList = board.getAdjList(15,6);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(14, 6)));
		assertTrue(testList.contains(board.getCell(16, 6)));
		assertTrue(testList.contains(board.getCell(15, 5)));
	
	}
	
	
	// Tests out of room center, 1, 2 and 3
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInScrollRoom() {
		// test a roll of 1
		board.calcTargets(board.getCell(20, 2), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(14, 1)));
		assertTrue(targets.contains(board.getCell(19, 4)));	
		assertTrue(targets.contains(board.getCell(1, 21)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(12, 20), 3);
		targets= board.getTargets();
		assertEquals(16, targets.size());
		assertTrue(targets.contains(board.getCell(4, 17)));
		assertTrue(targets.contains(board.getCell(5, 16)));	
		assertTrue(targets.contains(board.getCell(6, 17)));
		assertTrue(targets.contains(board.getCell(6, 25)));	
		assertTrue(targets.contains(board.getCell(6, 27)));	
		assertTrue(targets.contains(board.getCell(17, 4)));	
		assertTrue(targets.contains(board.getCell(18, 5)));	
		assertTrue(targets.contains(board.getCell(19, 6)));	
		assertTrue(targets.contains(board.getCell(21, 4)));	
		assertTrue(targets.contains(board.getCell(20, 6)));	
		assertTrue(targets.contains(board.getCell(19, 6)));
		assertTrue(targets.contains(board.getCell(17, 4)));	
		assertTrue(targets.contains(board.getCell(14, 3)));	
		assertTrue(targets.contains(board.getCell(13, 0)));	
		assertTrue(targets.contains(board.getCell(13, 2)));	
		assertTrue(targets.contains(board.getCell(14, 3)));	
		
		// test a roll of 2
		board.calcTargets(board.getCell(20, 2), 2);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(18, 4)));
		assertTrue(targets.contains(board.getCell(19, 5)));	
		assertTrue(targets.contains(board.getCell(20, 4)));
		assertTrue(targets.contains(board.getCell(13, 1)));	
		assertTrue(targets.contains(board.getCell(14, 2)));	
		assertTrue(targets.contains(board.getCell(5, 17)));
		assertTrue(targets.contains(board.getCell(6, 26)));
	}
	
	@Test
	public void testTargetsInArmory() {
		// test a roll of 1
		board.calcTargets(board.getCell(4, 14), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(5, 11)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(4, 14), 3);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(4, 10)));
		assertTrue(targets.contains(board.getCell(6, 10)));	
		assertTrue(targets.contains(board.getCell(7, 11)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(20, 19), 4);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 10)));
		assertTrue(targets.contains(board.getCell(5, 10)));	
		assertTrue(targets.contains(board.getCell(7, 10)));
		assertTrue(targets.contains(board.getCell(8, 11)));	
		assertTrue(targets.contains(board.getCell(6, 11)));
		assertTrue(targets.contains(board.getCell(4, 11)));
	}

	// Tests out of room center, 1, 3 and 2
	// These are Dark orange on the planning spreadsheet
	//test door opening up into the kitchen
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(6, 3), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(3, 2)));
		assertTrue(targets.contains(board.getCell(6, 2)));	
		assertTrue(targets.contains(board.getCell(7, 3)));	
		assertTrue(targets.contains(board.getCell(6, 4)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(6, 3), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(7, 3)));
		assertTrue(targets.contains(board.getCell(6, 2)));
		assertTrue(targets.contains(board.getCell(7, 4)));	
		assertTrue(targets.contains(board.getCell(7, 1)));
		assertTrue(targets.contains(board.getCell(7, 5)));
		assertTrue(targets.contains(board.getCell(6, 6)));
		assertTrue(targets.contains(board.getCell(3, 2)));
		
		// test a roll of 2
		board.calcTargets(board.getCell(6, 3), 2);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(6, 1)));
		assertTrue(targets.contains(board.getCell(7, 2)));
		assertTrue(targets.contains(board.getCell(7, 4)));	
		assertTrue(targets.contains(board.getCell(6, 5)));
		assertTrue(targets.contains(board.getCell(3, 2)));
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(8, 9), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(7, 9)));
		assertTrue(targets.contains(board.getCell(8, 8)));	
		assertTrue(targets.contains(board.getCell(9, 9)));
		assertTrue(targets.contains(board.getCell(8, 10)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(8, 9), 3);
		targets= board.getTargets();
		assertEquals(14, targets.size());
		assertTrue(targets.contains(board.getCell(4, 8)));
		assertTrue(targets.contains(board.getCell(8, 6)));
		assertTrue(targets.contains(board.getCell(8, 8)));	
		assertTrue(targets.contains(board.getCell(7, 7)));
		assertTrue(targets.contains(board.getCell(8, 9)));
		assertTrue(targets.contains(board.getCell(8, 10)));
		assertTrue(targets.contains(board.getCell(7, 11)));
		assertTrue(targets.contains(board.getCell(6, 10)));
		assertTrue(targets.contains(board.getCell(9, 9)));
		assertTrue(targets.contains(board.getCell(10, 10)));
		assertTrue(targets.contains(board.getCell(9, 7)));
		assertTrue(targets.contains(board.getCell(9, 11)));
		assertTrue(targets.contains(board.getCell(10, 8)));
		assertTrue(targets.contains(board.getCell(7, 9)));
		
		// test a roll of 2
		board.calcTargets(board.getCell(8, 9), 2);
		targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(4, 8)));
		assertTrue(targets.contains(board.getCell(7, 8)));
		assertTrue(targets.contains(board.getCell(8, 7)));	
		assertTrue(targets.contains(board.getCell(9, 8)));
		assertTrue(targets.contains(board.getCell(10, 9)));
		assertTrue(targets.contains(board.getCell(9, 10)));
		assertTrue(targets.contains(board.getCell(8, 11)));
		assertTrue(targets.contains(board.getCell(7, 10)));
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(6, 23), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(6, 22)));
		assertTrue(targets.contains(board.getCell(6, 24)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(6, 23), 3);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(6, 20)));
		assertTrue(targets.contains(board.getCell(6, 26)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(6, 23), 4);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(2, 24)));
		assertTrue(targets.contains(board.getCell(6, 27)));
		assertTrue(targets.contains(board.getCell(6, 19)));	
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 4 
		board.getCell(7, 1).setOccupied(true);
		board.calcTargets(board.getCell(6, 2), 4);
		board.getCell(7, 1).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(6, 4)));
		assertTrue(targets.contains(board.getCell(7, 3)));
		assertTrue(targets.contains(board.getCell(7, 5)));	
		assertFalse( targets.contains( board.getCell(6, 6))) ;
		assertFalse( targets.contains( board.getCell(3, 2))) ;
	
		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(4, 14).setOccupied(true);
		board.getCell(6, 11).setOccupied(true);
		board.calcTargets(board.getCell(5, 11), 1);
		board.getCell(4, 14).setOccupied(false);
		board.getCell(6, 11).setOccupied(false);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(4, 14)));	
		assertTrue(targets.contains(board.getCell(4, 11)));	
		assertTrue(targets.contains(board.getCell(5, 10)));	
		
		// check leaving a room with a blocked doorway
		board.getCell(5, 17).setOccupied(true);
		board.calcTargets(board.getCell(2, 24), 3);
		board.getCell(5, 17).setOccupied(false);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(6, 24)));
		assertTrue(targets.contains(board.getCell(6, 28)));	
		assertTrue(targets.contains(board.getCell(18, 4)));
		assertTrue(targets.contains(board.getCell(20, 4)));
		assertTrue(targets.contains(board.getCell(19, 5)));
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(13, 1)));

	}
}
