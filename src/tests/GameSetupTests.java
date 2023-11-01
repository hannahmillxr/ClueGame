package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;

class GameSetupTests {

	// Constants that I will use to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 25;
	public static final int NUM_COLUMNS = 30;

	// NOTE: I made Board static because I only want to set it up one
	// time (using @BeforeAll), no need to do setup before each test.
	private static Board board;


	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("newClueBoardcsv.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}

	@Test
	public void loadInPlayers() {

		assertEquals(6, board.getPlayers().size());
	}


	@Test
	public void testCards() {
		assertEquals(21, board.getDeck().size());
		assertEquals(9, board.getRoomCards().size());
		assertEquals(6, board.getPersonCards().size());
		assertEquals(6, board.getWeaponCards().size());
	}


	//solution test, make sure solution has size of three and that all three types of cards are present
	@Test
	public void testSolutionCards() {
		assertTrue(board.getSolution().getSolutionWeapon()!=null);
		assertTrue(board.getSolution().getSolutionPerson()!=null);
		assertTrue(board.getSolution().getSolutionRoom()!=null);
	}

}
