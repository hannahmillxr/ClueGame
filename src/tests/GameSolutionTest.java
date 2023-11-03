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

		assertTrue(board.checkAccusation(new Solution(kitchenCard, viperCard, chopStickCard)));

		assertFalse(board.checkAccusation(new Solution(kitchenCard, viperCard, shensCannonCard)));
		assertFalse(board.checkAccusation(new Solution(kitchenCard, craneCard, chopStickCard)));
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
		checkWeapon = solutionWeapon;
		checkPerson = solutionPerson;
		checkRoom = solutionRoom;
		Card firstChangedCard = null;
		Card secondChangedCard = null;
		
		for (Player player :players) {
			Card firstCard = player.getHand().get(0);
			Card secondCard = player.getHand().get(1);
			Card thirdCard = player.getHand().get(2);
			
			if (firstCard.getType() != secondCard.getType()) {
				if (firstCard.getType()== CardType.ROOM){
					checkRoom = firstCard;
					firstChangedCard = checkRoom;
				}
				if (firstCard.getType()== CardType.PERSON){
					checkPerson = firstCard;
					firstChangedCard = checkPerson;
				}
				if (firstCard.getType()== CardType.WEAPON){
					checkWeapon = firstCard;
					firstChangedCard = checkWeapon;
				}
				
				if (secondCard.getType()== CardType.ROOM){
					checkRoom = secondCard;
					secondChangedCard = checkRoom;
				}
				if (secondCard.getType()== CardType.PERSON){
					checkPerson = secondCard;
					secondChangedCard = checkPerson;
				}
				if (secondCard.getType()== CardType.WEAPON){
					checkWeapon = secondCard;
					secondChangedCard = checkWeapon;
				}
				break;
			}
			else if (secondCard.getType() != thirdCard.getType()) {
				if (thirdCard.getType()== CardType.ROOM){
					checkRoom = thirdCard;
					 firstChangedCard = checkRoom;
				}
				if (thirdCard.getType()== CardType.PERSON){
					checkPerson = thirdCard;
					 firstChangedCard = checkPerson;
				}
				if (thirdCard.getType()== CardType.WEAPON){
					checkWeapon = thirdCard;
					 firstChangedCard = checkWeapon;
				}
				
				if (secondCard.getType()== CardType.ROOM){
					checkRoom = secondCard;
					secondChangedCard = checkRoom;
				}
				if (secondCard.getType()== CardType.PERSON){
					checkPerson = secondCard;
					secondChangedCard = checkPerson;
				}
				if (secondCard.getType()== CardType.WEAPON){
					checkWeapon = secondCard;
					secondChangedCard = checkWeapon;
				}
				break;
			}
			else if (thirdCard.getType() != firstCard.getType()) {
				if (firstCard.getType()== CardType.ROOM){
					checkRoom = firstCard;
					 firstChangedCard = checkRoom;
				}
				if (firstCard.getType()== CardType.PERSON){
					checkPerson = firstCard;
					 firstChangedCard = checkPerson;
				}
				if (firstCard.getType()== CardType.WEAPON){
					checkWeapon = firstCard;
					 firstChangedCard = checkWeapon;
				}
				
				if (thirdCard.getType()== CardType.ROOM){
					checkRoom = thirdCard;
					secondChangedCard = checkRoom;
				}
				if (thirdCard.getType()== CardType.PERSON){
					checkPerson = thirdCard;
					secondChangedCard = checkPerson;
				}
				if (thirdCard.getType()== CardType.WEAPON){
					checkWeapon = thirdCard;
					secondChangedCard = checkWeapon;
				}
				break;
			}
		}
		Solution answer = new Solution(checkRoom, checkPerson, checkWeapon);
		assertTrue((board.handleSuggestion(answer, players.get(3))== firstChangedCard) || board.handleSuggestion(answer, players.get(3))== secondChangedCard);
		
		
		
	}
	
	
	@Test
	public void handleSuggestion() {
		Card solutionWeapon = board.solution.getSolutionWeapon();
		Card solutionPerson = board.solution.getSolutionPerson();
		Card solutionRoom = board.solution.getSolutionRoom();
		ArrayList<Player> players = board.getPlayers();
		
		//No player should be holding solution cards, so handle suggestion should return null
		assertTrue(board.handleSuggestion(new Solution(solutionRoom, solutionPerson, solutionWeapon), players.get(0))== null);
		
		
		
		ArrayList<Card> rooms = board.getRoomCards();
		ArrayList<Card> persons = board.getPersonCards();
		ArrayList<Card> weapons= board.getWeaponCards();
		
		//No player should be holding weapon or person solution cards, so handle suggestion should return the room that must be somewhere amongst players
		Card otherRoom;
		if (rooms.get(0).equals(solutionRoom)) {
			otherRoom = rooms.get(1);
		}
		else {
			otherRoom = rooms.get(0);
		}
		assertTrue(board.handleSuggestion(new Solution(otherRoom, solutionPerson, solutionWeapon), players.get(3))== otherRoom);
		
		
		
		//No player should be holding weapon or room solution cards, so handle suggestion should return the person that must be somewhere amongst players
		Card otherPerson;
		if (persons.get(0).equals(solutionPerson)) {
			otherPerson = persons.get(1);
		}
		else {
			otherPerson = persons.get(0);
		}
		assertTrue(board.handleSuggestion(new Solution(solutionRoom, otherPerson, solutionWeapon), players.get(3))== otherPerson);


		
		
		//No player should be holding person or room solution cards, so handle suggestion should return the weapon that must be somewhere amongst players
		Card otherWeapon;
		if (weapons.get(0).equals(solutionWeapon)) {
			otherWeapon = weapons.get(1);
		}
		else {
			otherWeapon = weapons.get(0);
		}
		assertTrue(board.handleSuggestion(new Solution(solutionRoom, solutionPerson, otherWeapon), players.get(3))== otherWeapon);
		
		
		Solution answer = new Solution(otherRoom, solutionPerson, otherWeapon);
		assertTrue((board.handleSuggestion(answer, players.get(3))== otherRoom) || board.handleSuggestion(answer, players.get(3))== otherWeapon);
		
		
		//solution only a human can disprove
		
		Card checkWeapon = solutionWeapon;
		Card checkPerson = solutionPerson;
		Card checkRoom = solutionRoom;
		Card changed = null;
		int humanIndex = 0;
		
		
		for (int i = 0; i<players.size(); i++) {
			if (players.get(i) instanceof HumanPlayer) {
				Card humanCheck = players.get(i).getHand().get(0);
				if (humanCheck.getType()== CardType.ROOM){
					checkRoom = humanCheck;
					changed = checkRoom;
					humanIndex = i;
				}
				if (humanCheck.getType()== CardType.PERSON){
					checkPerson = humanCheck;
					changed = checkPerson;
					humanIndex = i;
				}
				if (humanCheck.getType()== CardType.WEAPON){
					checkWeapon = humanCheck;
					changed = checkWeapon;
					humanIndex = i;
				}
				
				break;
			}
		}
		
		assertTrue(board.handleSuggestion(new Solution(checkRoom, checkPerson, checkWeapon), players.get(3))== changed);
		
		
		//Solution two players can disprove correct player (based on starting with next player in list) returns answer
		Card changed2 = null;
		int index = 0;
		
		for (int j = (humanIndex +1)%players.size(); j<players.size(); j++) {
			if (players.get(j) instanceof ComputerPlayer) {
				for (int i =0; i<3; i++) {
					Card check = players.get(j).getHand().get(i);
					
					if ((check.getType() != changed.getType())){
						if (check.getType()== CardType.ROOM){
							checkRoom = check;
							changed2 = checkRoom;
							index = j;
						}
						if (check.getType()== CardType.PERSON){
							checkPerson = check;
							changed2 = checkPerson;
							index = j;
						}
						if (check.getType()== CardType.WEAPON){
							checkWeapon = check;
							changed2 = checkWeapon;
							index = j;
						}
					}
				}	
				
			}
		}
		assertTrue(board.handleSuggestion(new Solution(checkRoom, checkPerson, checkWeapon), players.get(index-1))== changed2);

	}
	
	
}
