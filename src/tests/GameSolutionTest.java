package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameSolutionTest {

	// Constants that I will use to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 25;
	public static final int NUM_COLUMNS = 30;

	// NOTE: I made Board static because I only want to set it up one
	// time (using @BeforeAll), no need to do setup before each test.
	private static Board board;
	private static Card poCard, tigressCard, craneCard, monkeyCard, viperCard, mantisCard, shensCannonCard, 
	yinYangStaffCard, fryPanCard, jadeDaggerCard, fistCard, chopStickCard, armoryCard, bedroomsCard, courtYardCard, meditationCard,
	dojoCard, kitchenCard, scrollRoomCard, cherryBlossomCard, teaRoomCard;


	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("newClueBoardcsv.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();

		//six people
		poCard = new Card("Po", CardType.PERSON);
		tigressCard = new Card("Tigress", CardType.PERSON);
		craneCard = new Card ("Crane", CardType.PERSON);
		monkeyCard = new Card("Monkey", CardType.PERSON);
		viperCard = new Card("Viper", CardType.PERSON);
		mantisCard = new Card ("Mantis", CardType.PERSON);
		
		//weapons
		shensCannonCard = new Card("Shen's Cannon", CardType.WEAPON);
		yinYangStaffCard = new Card("Yin Yang Staff", CardType.WEAPON);
		fryPanCard = new Card ("Frying Pan", CardType.WEAPON);
		jadeDaggerCard = new Card("Jade Dagger", CardType.WEAPON);
		fistCard = new Card("Fist", CardType.WEAPON);
		chopStickCard = new Card ("Chop Sticks", CardType.WEAPON);
		
		
		//rooms
		kitchenCard = new Card("Kitchen", CardType.ROOM);
		armoryCard = new Card("Armory", CardType.ROOM);
		bedroomsCard = new Card ("Bedrooms", CardType.ROOM);
		meditationCard = new Card("Meditation Room", CardType.ROOM);
		dojoCard = new Card("Dojo", CardType.ROOM);
		scrollRoomCard = new Card ("Scroll Room", CardType.ROOM);
		cherryBlossomCard = new Card ("Cherry Blossom Room", CardType.ROOM);
		teaRoomCard = new Card ("Tea Room", CardType.ROOM);
		courtYardCard = new Card ("Court Yard", CardType.ROOM);
	}

	@Test
	public void testAccusation() {
		board.solution.setWeapon(chopStickCard);
		board.solution.setPerson(viperCard);
		board.solution.setRoom(kitchenCard);
		
		//solution is correct
		assertTrue(board.checkAccusation(new Solution(kitchenCard, viperCard, chopStickCard)));
		
		//solution with wrong person
		assertFalse(board.checkAccusation(new Solution(kitchenCard, craneCard, chopStickCard)));
		
		//solution with wrong weapon
		assertFalse(board.checkAccusation(new Solution(kitchenCard, viperCard, shensCannonCard)));
		
		//solution with wrong room
		assertFalse(board.checkAccusation(new Solution(dojoCard, viperCard, chopStickCard)));
		
	}
	
	@Test
	public void disproveSuggestion() {
		Card solutionWeapon = board.solution.getSolutionWeapon();
		Card solutionPerson = board.solution.getSolutionPerson();
		Card solutionRoom = board.solution.getSolutionRoom();
		ArrayList<Player> players = board.getPlayers();
		//If player has only one matching card it should be returned
		
		Card checkWeapon = solutionWeapon;
		Card checkPerson = solutionPerson;
		Card checkRoom = solutionRoom;
		Card changed = null;

		

		Player playerOneMatchingCard = players.get(0);
		Card suggestedCard = playerOneMatchingCard.getHand().get(0);
					
		if (suggestedCard.getType()== CardType.ROOM){
			checkRoom = suggestedCard;
			changed = checkRoom;
		}
		if (suggestedCard.getType()== CardType.PERSON){
			checkPerson = suggestedCard;
			changed = checkPerson;
		}
		if (suggestedCard.getType()== CardType.WEAPON){
			checkWeapon = suggestedCard;
			changed = checkWeapon;          	
		}
		
		
		assertTrue(playerOneMatchingCard.disproveSuggestion(new Solution(checkRoom, checkPerson, checkWeapon))== changed);
		
		//If player has no matching cards, null is returned
		assertTrue(playerOneMatchingCard.disproveSuggestion(new Solution(solutionRoom, solutionPerson, solutionWeapon))== null);
		
		
		//If players has >1 matching card, returned card should be chosen randomly
		board.solution.setWeapon(chopStickCard);
		board.solution.setPerson(viperCard);
		board.solution.setRoom(kitchenCard);

		board.getPlayers().get(0).clearHand();
		board.getPlayers().get(0).updateHand(meditationCard);
		board.getPlayers().get(0).updateHand(jadeDaggerCard);
		board.getPlayers().get(0).updateHand(teaRoomCard);
		
		Solution answer = new Solution(meditationCard, solutionPerson, jadeDaggerCard);
		assertTrue(isMeditationRoomOrJadeDagger(board.getPlayers().get(0).disproveSuggestion(answer)));
		
		
		
	}
	
	
	@Test
	public void handleSuggestion() {
		
		/**
		 * set up cards in player
		 * set up cards in solution
		 * 
		 */
		ArrayList<Player> players = board.getPlayers();

		
		Card solutionWeapon = board.solution.getSolutionWeapon();
		Card solutionPerson = board.solution.getSolutionPerson();
		Card solutionRoom = board.solution.getSolutionRoom();
		
		//Suggestion no one can disprove returns null
		assertTrue(board.handleSuggestion(new Solution(solutionRoom, solutionPerson, solutionWeapon), players.get(0))== null);
		
		
		board.solution.setWeapon(chopStickCard);
		board.solution.setPerson(viperCard);
		board.solution.setRoom(kitchenCard);
		/***
		 * What needs to be done
		 * Set up situation: create player, add cards to each players hand, test suggestion, and then assert.
		 * Make assert/tests
		 * 
		 */
		//build player one
		board.getPlayers().get(0).clearHand();
		board.getPlayers().get(0).updateHand(meditationCard);
		board.getPlayers().get(0).updateHand(jadeDaggerCard);
		board.getPlayers().get(0).updateHand(teaRoomCard);
		
		//build player two
		board.getPlayers().get(1).clearHand();
		board.getPlayers().get(1).updateHand(dojoCard);
		board.getPlayers().get(1).updateHand(fistCard);
		board.getPlayers().get(1).updateHand(craneCard);
		
		board.getPlayers().get(2).clearHand();
		board.getPlayers().get(2).updateHand(armoryCard);
		board.getPlayers().get(2).updateHand(courtYardCard);
		board.getPlayers().get(2).updateHand(mantisCard);
		
		//other hands are empty
		board.getPlayers().get(3).clearHand();
		board.getPlayers().get(4).clearHand();
		board.getPlayers().get(5).clearHand();
		
		players = board.getPlayers();
		
	
		solutionWeapon = chopStickCard;
		solutionPerson = viperCard;
		solutionRoom = kitchenCard;
		
		//Suggestion only suggesting player can disprove returns null
		assertTrue(board.handleSuggestion(new Solution(dojoCard, solutionPerson, solutionWeapon), players.get(1))== null);
		
		//Suggestion only human can disprove returns answer (i.e., card that disproves suggestion)
		assertTrue(players.get(0) instanceof HumanPlayer);
		assertTrue(board.handleSuggestion(new Solution(meditationCard, solutionPerson, solutionWeapon), players.get(2))== meditationCard);
		
		//Suggestion that two players can disprove, correct player (based on starting with next player in list) returns answer
		assertTrue((board.handleSuggestion(new Solution(dojoCard, mantisCard, solutionWeapon), players.get(0))== dojoCard));


	}
	
	private Boolean isMeditationRoomOrJadeDagger(Card card) {
		if (card.equals(meditationCard)){
			return true;
		}
		if (card.equals(jadeDaggerCard)){
			return true;
		}
		return false;
	}
	
}
