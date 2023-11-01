package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;




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
		
		//All players are loaded in
		assertEquals(6, board.getPlayers().size());
	}


	@Test
	public void testCards() {
		
		//Make sure the correct number of room cards, person cards and weapon cards are present
		assertEquals(9, board.getRoomCards().size());
		assertEquals(6, board.getPersonCards().size());
		assertEquals(6, board.getWeaponCards().size());
		assertEquals(21, board.getDeck().size());
		
		//make sure we dealt the entire deck
		assertEquals(board.getDealt().size(), board.getDeck().size());
		
		
		//Make sure no players have the same two cards
		ArrayList<Player> players = board.getPlayers();
		for (int i =0; i< players.size()-1; i++) {
			for (int j =1; j< players.size(); j++) {
				ArrayList<Card> playerOneHand = players.get(i).getHand();
				ArrayList<Card> playerTwoHand = players.get(j).getHand();
				for (int card = 0; card<3; card++) {
					if (i!=j) {
						assertFalse(playerOneHand.get(card).equals(playerTwoHand.get(card)));
					}
					
				}
			}
		}
	}


	//solution test, make sure solution has size of three and that all three types of cards are present
	@Test
	public void testSolutionCards() {
		assertTrue(board.getSolution().getSolutionWeapon().getType()== CardType.WEAPON);
		assertTrue(board.getSolution().getSolutionPerson().getType()== CardType.PERSON);
		assertTrue(board.getSolution().getSolutionRoom().getType()== CardType.ROOM);
	}

}
