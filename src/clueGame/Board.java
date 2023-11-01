/*
 * Author: Hannah Miller and Gillian Culberson
 * Description: Board class will set, input, and inizalize the Board for the user
 */
package clueGame;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;



public class Board {

	private Map <Character, Room> roomMap;
	private Map <BoardCell, Set<BoardCell>> adjMtx;
	private BoardCell [][] grid;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private Set<Player> players = new HashSet<>();
	private Set<Card> deck = new HashSet<>(); //we might not use this
	private ArrayList<Card> roomCards = new ArrayList<>();
	private ArrayList<Card> personCards = new ArrayList<>();
	private ArrayList<Card> weaponCards = new ArrayList<>();
	private String layoutConfigFile;
	private String setupConfigFile;
	private int numColumns;
	private int numRows;
	private Solution solution = new Solution();
	private Set<Card> dealt;
	
    /*
    * variable and methods used for singleton pattern
    */
    private static Board theInstance = new Board();
    
    // constructor is private to ensure only one can be created
    private Board() {
           super() ;
    }
    
    // this method returns the only Board
    public static Board getInstance() {
           return theInstance;
    }
    
    /*
     * initialize the board (since we are using singleton pattern)
     */
    public void initialize () {
    	try {
    		loadSetupConfig();
    		loadLayoutConfig();
    		buildAdjacencyList();
    		
    	} catch(BadConfigFormatException e)  {
    		System.out.println(e);
    		System.out.println(e.getMessage());
    	}	
    }
    
 
    public void setConfigFiles(String csv, String txt) {
    	setupConfigFile = "data/" + txt;
    	layoutConfigFile = "data/" + csv;
	}
    
    public void calcTargets(BoardCell startCell, int pathlength) { 
    	
    	targets = new HashSet <BoardCell>();
    	visited = new HashSet <BoardCell>();
    	calculateTargets (startCell, pathlength);

    }
   
    
    public void deal() {
    	dealt = new HashSet<Card>();
    	Random rand = new Random();
    	Card roomSolution = roomCards.get(rand.nextInt(roomCards.size()));
		Card personSolution = personCards.get(rand.nextInt(personCards.size()));
		Card weaponSolution = weaponCards.get(rand.nextInt(weaponCards.size()));
    	
    	solution.setWeapon(weaponSolution);
    	solution.setPerson(personSolution);
    	solution.setRoom(roomSolution);
    	
    	for (Player player : players) {
    		Card room = roomCards.get(rand.nextInt(roomCards.size()));
    		Card person = personCards.get(rand.nextInt(personCards.size()));
    		Card weapon = weaponCards.get(rand.nextInt(weaponCards.size()));
    		
    		while (dealt.contains(room)) {
    			room = roomCards.get(rand.nextInt(roomCards.size()));
    		}
    		while (dealt.contains(person)) {
    			person = personCards.get(rand.nextInt(personCards.size()));
    		}
    		while (dealt.contains(weapon)) {
    			weapon = weaponCards.get(rand.nextInt(weaponCards.size()));
    		}
    		
    		dealt.add(room);
    		dealt.add(person);
    		dealt.add(weapon);
			player.updateHand(room);
			player.updateHand(person);
			player.updateHand(weapon);
    	}
    	
    }
    
    public Map<Character, Room> getRoomMap() {
		return roomMap;
	}

	public Set<Player> getPlayers() {
		return players;
	}

	public Set<Card> getDeck() {
		return deck;
	}

	public ArrayList<Card> getRoomCards() {
		return roomCards;
	}

	public Solution getSolution() {
		return solution;
	}

	public ArrayList<Card> getWeaponCards() {
		return weaponCards;
	}


	public ArrayList<Card> getPersonCards() {
		return personCards;
	}

	public Set<Card> getDealt() {
		return dealt;
	}

	//Rename method different from calculations to calTarget
    public void calculateTargets (BoardCell startCell, int pathlength) { 
    	int numSteps = pathlength;

    	visited.add(startCell);

    	for (BoardCell cell : startCell.getAdjList()) {
    		if (!visited.contains(cell)) {

    			visited.add(cell);

    			if (!cell.isRoom() && cell.getOccupied()) {
    				continue;
    			}

    			else if (cell.isRoom()){
    				targets.add(cell);
    			}

    			else if (numSteps == 1) {
    				targets.add(cell);
    			}	

    			else {
    				calculateTargets(cell, numSteps-1);
    			}	

    			visited.remove(cell);
    		}
    	}
    }
    
    
    //reads in text file 
    public void loadSetupConfig() throws BadConfigFormatException {
		ArrayList<String> textFile = new ArrayList<String>();
		roomMap = new HashMap<Character, Room>();
		
		try {
			FileReader reader = new FileReader(setupConfigFile);
			Scanner in = new Scanner(reader);
			while (in.hasNextLine()) {
				String readInNext = in.nextLine();
				textFile.add(readInNext);
				
			}
			in.close();
			
		} catch(FileNotFoundException e) {
			System.out.println(e);
			System.out.println(e.getMessage());
		}
		
		
		//text file has structure: [Room, name, initial] can be room or space ELSE THROW EXCEPTION
		for (int line = 0; line < textFile.size(); line++) {
			String[] word = textFile.get(line).split(", ");
			
			
			//make sure you are not adding comment line from txt file
			if (word[0].equals("Room") || word[0].equals("Space")) {
				Room room = new Room(word[1]);
				char initial = word[2].charAt(0);				
				roomMap.put(initial, room);
				if (word[0].equals("Room")) {
					Card roomCard = new Card(word[1], CardType.ROOM);
					deck.add(roomCard);
					roomCards.add(roomCard);
				}
			}
			else if (word[0].equals("Player")) {
				Card playerCard = new Card(word[1], CardType.PERSON);
				deck.add(playerCard);
				personCards.add(playerCard);
				
				//load players
				
				if (word.length == 5) {
					//load player first
					if (players.size() == 0) {
						HumanPlayer player = new HumanPlayer(word[1], word[2], Integer.valueOf(word[3]), Integer.valueOf(word[4]));
						players.add(player);
					}
					else {
						ComputerPlayer player = new ComputerPlayer(word[1], word[2], Integer.valueOf(word[3]), Integer.valueOf(word[4]));
						players.add(player);
					}
				}
				else {
					throw new BadConfigFormatException("Text file is improperly formated");
				}
				
				
				
				
			}
			else if (word[0].equals("Weapon")) {
				Card weaponCard = new Card(word[1], CardType.WEAPON);
				deck.add(weaponCard);
				weaponCards.add(weaponCard);
			}
			else if (!word[0].startsWith("//")){
				throw new BadConfigFormatException("Text file is improperly formated");
			}
		}		
    	
    }
    
    
    //reads in csv file
    public void loadLayoutConfig() throws BadConfigFormatException{
    	ArrayList<String> csvFile = new ArrayList<String>();

    	try {
    		FileReader reader = new FileReader(layoutConfigFile);
    		Scanner in = new Scanner(reader);
    		while (in.hasNextLine()) {
    			String readInNext = in.nextLine();

    			csvFile.add(readInNext);
    		}
    		in.close();

    	} catch(FileNotFoundException e) {
    		System.out.println(e);
    		System.out.println(e.getMessage());
    	}


    	String[] firstList = csvFile.get(0).split(",");
    	int colNum = firstList.length;
    	int rowNum = csvFile.size();

    	numRows = rowNum;
    	numColumns = colNum;


    	grid = new BoardCell[rowNum][colNum];
    	//if rows are different lengths each time, throw new BadConfigFormatException("Rows have varying lengths")
    	for (int i =0; i< csvFile.size(); i++) {
    		String[] squares = csvFile.get(i).split(",");

    		if (squares.length != colNum) {
    			throw new BadConfigFormatException("Rows have varying lengths");
    		}
    		for (int j = 0; j< squares.length; j++) {
    			if (squares[j].length()> 1) {
    				BoardCell cell = new BoardCell(i, j, squares[j].charAt(1));


    				buildBoardCell(i, squares, j, cell);

    			}
    			else {
    				BoardCell cell = new BoardCell(i, j);
    				buildBoardCell(i, squares, j, cell);
    				
    			}

    			//if there is a room that is not in our legend, throw new BadConfigFormatException("Room found that is not in legend")
    			if (!roomMap.containsKey(squares[j].charAt(0))) {
    				throw new BadConfigFormatException("Room found that is not in legend");
    			}
    		}
    	}	
    }

	private void buildBoardCell(int row, String[] squares, int col, BoardCell cell) {
		cell.setInitial(squares[col].charAt(0));

		//Using the sqaure letter to determine if these is a room or walkway
		if (squares[col].charAt(0) != 'W' && squares[col].charAt(0) != 'X') {
			cell.setRoom(true);
			cell.setWalkway(false);
		}
		else if (squares[col].charAt(0) == 'W'){
			cell.setRoom(false);
			cell.setWalkway(true);	
		}
		else {
			cell.setRoom(false);
			cell.setWalkway(false);
		}	

		if (cell.isRoomCenter()) {
			Room temproom = roomMap.get(cell.getInitial());
			temproom.setCenterCell(cell);
		}
		if (cell.isLabel()) {
			roomMap.get(cell.getInitial()).setLabelCell(cell);
		}
		grid[row][col] = cell;
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		return grid[row][col].getAdjList();
		//return adj at sep board cell
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public void buildAdjacencyList() {
		this.adjMtx = new HashMap<BoardCell, Set<BoardCell>>();

		//build adjacency list, look up down left right and make sure its within the bounds
		for (int i = 0; i < numRows;i++) {
			for(int j = 0; j< numColumns;j++) {
				BoardCell cell = this.getCell(i, j);
				//walkways connect to adjacent walkways
				if (cell.isWalkway()==true) {
					if (i-1 >= 0 && this.getCell(i-1, j).isWalkway()) {
						cell.addAdjacency(this.getCell(i-1, j));
					}
					if (j-1 >= 0 && this.getCell(i, j-1).isWalkway()) {
						cell.addAdjacency(this.getCell(i, j-1));
					}
					if (i+1 <numRows && this.getCell(i+1, j).isWalkway()) {
						cell.addAdjacency(this.getCell(i+1, j));
					}
					if (j+1 < numColumns && this.getCell(i, j+1).isWalkway()) {
						cell.addAdjacency(this.getCell(i, j+1));
					}
				}
				
				
				//walkways with doors connect to the room center
				if (cell.isDoorway()) {
					
					if (cell.getDoorDirection() == DoorDirection.UP) {
						//get the initial of the square above current cell
						char symbol = this.getCell(i-1, j).getInitial();
						
						//get the room center of that initial and add the room center to the doors adjacency
						cell.addAdjacency(this.getRoom(symbol).getCenterCell());
						this.getRoom(symbol).getCenterCell().addAdjacency(cell);
					}
					if (cell.getDoorDirection() == DoorDirection.DOWN) {
						char symbol = this.getCell(i+1, j).getInitial();
						
						//get the room center of that initial and add the room center to the doors adjacency
						cell.addAdjacency(this.getRoom(symbol).getCenterCell());
						this.getRoom(symbol).getCenterCell().addAdjacency(cell);
						
					}
					if (cell.getDoorDirection() == DoorDirection.LEFT) {
						char symbol = this.getCell(i, j-1).getInitial();
						
						//get the room center of that initial and add the room center to the doors adjacency
						cell.addAdjacency(this.getRoom(symbol).getCenterCell());
						this.getRoom(symbol).getCenterCell().addAdjacency(cell);
						
					}
					if (cell.getDoorDirection() == DoorDirection.RIGHT) {
						char symbol = this.getCell(i, j+1).getInitial();
						
						//get the room center of that initial and add the room center to the doors adjacency
						cell.addAdjacency(this.getRoom(symbol).getCenterCell());
						this.getRoom(symbol).getCenterCell().addAdjacency(cell);
					}
				}

				//if there is a secrete passage, connect the appropriate room centers
				if (cell.isSecretPass()) {
					char otherRoomSymbol = cell.getSecretPassage();
					char thisCellSymbol = cell.getInitial();
					//get the room center of that initial and add the room center to the doors adjacency
					Room otherRoom = this.getRoom(otherRoomSymbol);
					Room thisRoom = this.getRoom(thisCellSymbol);
					thisRoom.getCenterCell().addAdjacency(otherRoom.getCenterCell());
				}


				Set<BoardCell> currentAdjacencyList =  cell.getAdjList();		
				adjMtx.put(cell, currentAdjacencyList);
			}
		}

	}
	   public int getNumRows() {
			return numRows;
		}

		public int getNumColumns() {
			return numColumns;
		}
		
		public Room getRoom(char initial) {
			Room room = roomMap.get(initial);
			return room;
		}
		
		public Room getRoom(BoardCell cell) {
			char initial = cell.getInitial();
			Room room = roomMap.get(initial);
			return room;
		}
	    
	    public BoardCell getCell(int row, int col) {
			return grid[row][col];
		}
	    

    
    
}
