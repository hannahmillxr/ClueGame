package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

class ComputerAITest {
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


	@BeforeEach
	public void setUp() {
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

	//if no rooms in list, select randomly
	@Test
	void selectTargetTestNoRooms() {
		board.getPlayers().get(1).setRow(14); 
		board.getPlayers().get(1).setCol(5);

		//if we have one move available, we should move to random walkway
		board.calcTargets(board.getCell(board.getPlayers().get(1).getRow(), board.getPlayers().get(1).getCol()), 1);
		
		
		BoardCell up =  board.getCell(13, 5);
		BoardCell down =  board.getCell(15, 5);
		BoardCell left =  board.getCell(14, 4);
		BoardCell right =  board.getCell(14, 6);
		
		int upCount = 0;
		int downCount = 0;
		int leftCount = 0;
		int rightCount = 0;
		
		for (int i = 0; i<1000; i++) {
			BoardCell target = board.getPlayers().get(1).selectTarget();
			if (target.equals(up)){
				upCount++;
			}
			if (target.equals(down)){
				downCount++;
			}
			if (target.equals(left)){
				leftCount++;
			}
			if (target.equals(right)){
				rightCount++;
			}
			
		}
		
		assertTrue(upCount>1);
		assertTrue(downCount>1);
		assertTrue(leftCount>1);
		assertTrue(rightCount>1);

	}

	//if room in list that has not been seen, select it
	@Test
	void selectTargetTestRoomUnseen() {
		//Place a computer character in the doorway to the kitchen
		board.getPlayers().get(1).setRow(6); 
		board.getPlayers().get(1).setCol(3);
		board.getPlayers().get(1).clearSeen();
		board.getPlayers().get(1).updateSeen(monkeyCard);
		board.getPlayers().get(1).updateSeen(craneCard);
		board.getPlayers().get(1).updateSeen(jadeDaggerCard);
		
		//if we have one move available, we should move into the unseen room
		board.calcTargets(board.getCell(board.getPlayers().get(1).getRow(), board.getPlayers().get(1).getCol()), 1);
		BoardCell target = board.getPlayers().get(1).selectTarget();
		assertTrue(target.getRow() == 4);
		assertTrue(target.getCol() == 2);

	}


	//if room in list that has been seen, each target (including room) selected randomly
	@Test
	void selectTargetTestSeenRoom() {
		//Place a computer character in the doorway to the kitchen, which is already in seen deck
		board.getPlayers().get(1).setRow(6); 
		board.getPlayers().get(1).setCol(3);
		
		board.getPlayers().get(1).updateSeen(kitchenCard);

		//if we have one move available, we should move randomly
		board.calcTargets(board.getCell(board.getPlayers().get(1).getRow(), board.getPlayers().get(1).getCol()), 1);


		BoardCell room =  board.getCell(4, 2);
		BoardCell down =  board.getCell(7, 3);
		BoardCell left =  board.getCell(6, 2);
		BoardCell right =  board.getCell(6, 4);

		int roomCount = 0;
		int downCount = 0;
		int leftCount = 0;
		int rightCount = 0;

		for (int i = 0; i<1000; i++) {
			BoardCell target = board.getPlayers().get(1).selectTarget();
			if (target.equals(room)){
				roomCount++;
			}
			if (target.equals(down)){
				downCount++;
			}
			if (target.equals(left)){
				leftCount++;
			}
			if (target.equals(right)){
				rightCount++;
			}

		}

		assertTrue(roomCount>1);
		assertTrue(downCount>1);
		assertTrue(leftCount>1);
		assertTrue(rightCount>1);

	}

	
	
	//Room matches current location
	@Test
	void createSuggestionCurrentLocation() {
		//put a player into the kitchen
		board.getPlayers().get(1).setRow(3); 
		board.getPlayers().get(1).setCol(2);
		Solution answer =  board.getPlayers().get(1).createSuggestion();
		assertTrue(answer.getSolutionRoom().equals(kitchenCard));
	}
	
	
	//If only one weapon not seen, it's selected
	@Test
	void createSuggestionOnlyOneUnseenWeapon() {
		board.getPlayers().get(1).setRow(3); 
		board.getPlayers().get(1).setCol(2);
		board.getPlayers().get(1).clearSeen();
		//only unseen is the jade dagger
		board.getPlayers().get(1).updateSeen(shensCannonCard); 
		board.getPlayers().get(1).updateSeen(yinYangStaffCard); 
		board.getPlayers().get(1).updateSeen(fryPanCard); 
		board.getPlayers().get(1).updateSeen(fistCard); 
		board.getPlayers().get(1).updateSeen(chopStickCard); 
		
		Solution answer =  board.getPlayers().get(1).createSuggestion();
		assertTrue(answer.getSolutionWeapon().equals(jadeDaggerCard));

	}
	
	
	
	//If only one person not seen, it's selected (can be same test as weapon)
	@Test
	void createSuggestionOnlyOneUnseenPerson() {
		board.getPlayers().get(1).setRow(3); 
		board.getPlayers().get(1).setCol(2);
		board.getPlayers().get(1).clearSeen();
		
		//only unseen is Crane
		board.getPlayers().get(1).updateSeen(poCard); 
		board.getPlayers().get(1).updateSeen(mantisCard); 
		board.getPlayers().get(1).updateSeen(viperCard); 
		board.getPlayers().get(1).updateSeen(monkeyCard); 
		board.getPlayers().get(1).updateSeen(tigressCard); 
		
		Solution answer =  board.getPlayers().get(1).createSuggestion();
		assertTrue(answer.getSolutionPerson().equals(craneCard));
		
	}
	
	
	//If multiple weapons not seen, one of them is randomly selected
	@Test
	void createSuggestionMultipleUnseenWeapons() {
		board.getPlayers().get(1).setRow(3); 
		board.getPlayers().get(1).setCol(2);
		board.getPlayers().get(1).clearSeen();
		//only unseen is the jade dagger and chopstick
		board.getPlayers().get(1).updateSeen(shensCannonCard); 
		board.getPlayers().get(1).updateSeen(yinYangStaffCard); 
		board.getPlayers().get(1).updateSeen(fryPanCard); 
		board.getPlayers().get(1).updateSeen(fistCard);

		int jadeDaggerCount = 0;
		int chopStickCount = 0;


		for (int i = 0; i<1000; i++) {
			Solution answer =  board.getPlayers().get(1).createSuggestion();
			if (jadeDaggerCard.equals(answer.getSolutionWeapon())){
				 jadeDaggerCount++;
			}
			if (chopStickCard.equals(answer.getSolutionWeapon())){
				 chopStickCount++;
			}
		}

		assertTrue(jadeDaggerCount>1);
		assertTrue(chopStickCount>1);
		
	}
	
	
	//If multiple persons not seen, one of them is randomly selected
	@Test
	void createSuggestionMultipleUnseenPeople() {
		board.getPlayers().get(1).setRow(3); 
		board.getPlayers().get(1).setCol(2);
		board.getPlayers().get(1).clearSeen();
		
		//only unseen is Crane and tigress
		board.getPlayers().get(1).updateSeen(poCard); 
		board.getPlayers().get(1).updateSeen(mantisCard); 
		board.getPlayers().get(1).updateSeen(viperCard); 
		board.getPlayers().get(1).updateSeen(monkeyCard); 
		
		int craneCount = 0;
		int tigressCount = 0;


		for (int i = 0; i<1000; i++) {
			Solution answer =  board.getPlayers().get(1).createSuggestion();
			if (tigressCard.equals(answer.getSolutionPerson())){
				 tigressCount++;
			}
			if (craneCard.equals(answer.getSolutionPerson())){
				 craneCount++;
			}
		}

		assertTrue(craneCount>1);
		assertTrue(tigressCount>1);

		
	}
	

}