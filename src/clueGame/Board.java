/*
 /*
 * Author: Hannah Miller and Gillian Culberson
 * Description: Board class will set, input, and inizalize the Board for the user
 */
package clueGame;



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel{

	private Map <Character, Room> roomMap;
	private Map <BoardCell, Set<BoardCell>> adjMtx;
	private BoardCell [][] grid;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private ArrayList<Player> players;
	private ArrayList<Card> deck;
	private ArrayList<Card> roomCards;
	private ArrayList<Card> personCards;
	private ArrayList<Card> weaponCards;
	private String layoutConfigFile;
	private String setupConfigFile;
	private int numColumns;
	private int numRows;
	public Solution solution;
	private Set<Card> dealt;
	private Player activePlayer;
	private int roll;
	private Boolean gameOver;
	private Boolean finishedTurn;
	private int cellsize;
	private GuessDialog guessDialogBox;
	
	
    /*
    * variable and methods used for singleton pattern
    */
    private static Board theInstance = new Board();
    
    

	// constructor is private to ensure only one can be created
    private Board() {
           super() ;
           this.addMouseListener(new cellMouseListener());
           this.gameOver = false;
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
    	deal();
    	//human player starts
    	activePlayer = players.get(0);
    }
    
 

    /*
     * calcTargets: Will call the calculateTargets to use the starting cell and path length
     */
    public void calcTargets(BoardCell startCell, int pathlength) { 
    	
    	targets = new HashSet <BoardCell>();
    	if (activePlayer.getSuggestionPull()) {
    		targets.add(startCell);
    	}
    	
    	visited = new HashSet <BoardCell>();
    	calculateTargets (startCell, pathlength);

    }
    
    //When the player hits next, this should be called for active player
    public void singleTurn() {
    	// turn is not over until this is true
    	finishedTurn = false;
    	
		repaint();
		
		//roll dice
		Random rand = new Random();
		roll = rand.nextInt(6)+1;
		
		ClueGame.getControlPanel().setTurn(activePlayer, roll);
		
		//calculate targets for the current location
		BoardCell currentLocation = getCell(activePlayer.getRow(), activePlayer.getCol());
		calcTargets(currentLocation, roll);
		
		if (activePlayer instanceof HumanPlayer) {
		
			highlight(true);
			
			// if no moves available then targets is size 0
			if(targets.size() == 0) {
				JOptionPane.showMessageDialog(null, "There are no moves available, turn skipped");
			}
		}
		else {	
			BoardCell targetCell = activePlayer.selectTarget();
			//computer selects a target and moves to it
			movePlayer(targetCell, activePlayer);
			// if player moves into a room, they need to make a suggestion
			if(targetCell.isRoom()) {
				Solution suggestion = activePlayer.createSuggestion();
				
				//display solution
				String setGuessText = suggestion.getSolutionPerson().getCardName() + ", " + suggestion.getSolutionWeapon().getCardName()  + ", " + suggestion.getSolutionRoom().getCardName();
				ClueGame.getControlPanel().setGuess(setGuessText);
				
				
				Card disproveCard = handleSuggestion(suggestion, activePlayer);
				
	
				
				String suggestedPlayerName = suggestion.getSolutionPerson().getCardName();
				for (Player player : players) {
					if (player.getName().equals(suggestedPlayerName)) {
						movePlayer(targetCell, player);
						player.setSuggestionPull(true);
						break;
					}
				}
				
				activePlayer.updateSeen(disproveCard);
			}
			
			//turn is over, repaint
			finishedTurn = true;
		}	
		
		
		repaint();
	}
    
    /**
     * nextTurn: the next player will activate after previous players turn
     * 
     */
    public void nextTurn() {
    	for (int i = 0; i<players.size(); i++) {
    		if (players.get(i) == activePlayer) {
    			activePlayer = players.get((i+1)%players.size());
    	break;
    		}
    	}
    }
    
    /**
     * movePlayer: Will not allow current paper to occupied spot and will set the new location of the player
     * @param moveToCell
     */
    public void movePlayer(BoardCell moveToCell, Player player) {
    	BoardCell moveFromCell = getCell(player.getRow(), player.getCol());
    	if (moveFromCell.isRoomCenter()) {
    		moveFromCell.removePlayerFromRoomCenter();
    	}    	
    	
    	moveFromCell.setOccupied(false);
    	moveToCell.setOccupied(true);
    	
    	
    	//if two players are on a room center, they need to be displayed side by side
    	player.setRow(moveToCell.getRow());
    	player.setCol(moveToCell.getCol());
    	
    }
   
    /*
     * Deal: Giving out the person, weapon, and room card to the player
     */
    public void deal() {
    	dealt = new HashSet<Card>();
    	solution = new Solution();
    	
    	//build a temporary deck to remove from as you deal cards
    	ArrayList<Card>tempDeck = new ArrayList<>();
    	for(Card card : deck) {
    		tempDeck.add(card);
    	}
    	
    	//build a solution of random cards from the deck
    	Random rand = new Random();
    	Card roomSolution = roomCards.get(rand.nextInt(roomCards.size()));
		Card personSolution = personCards.get(rand.nextInt(personCards.size()));
		Card weaponSolution = weaponCards.get(rand.nextInt(weaponCards.size()));
    	
		//set solutions for Weapon, Person, and Room
    	solution.setWeapon(weaponSolution);
    	solution.setPerson(personSolution);
    	solution.setRoom(roomSolution);
    	
    	//add the weapons to the deck of delt cards and remove if from the temp deck of undealt cards
    	dealt.add(weaponSolution);
		dealt.add(personSolution);
		dealt.add(roomSolution);
		tempDeck.remove(weaponSolution);
		tempDeck.remove(personSolution);
		tempDeck.remove(roomSolution);
		
    	//distribute the rest of the cards in temp deck and remove them as you go
    	for (Player player : players) {
    		for (int i = 0; i<3; i++) {
    			Card dealing = tempDeck.get(rand.nextInt(tempDeck.size()));
        		dealt.add(dealing);
    			player.updateHand(dealing);
    			player.updateSeen(dealing);
    			tempDeck.remove(dealing);
    		}
    		    		
    	}
    	
    }
    
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    	//calculate cell size
    	int width = this.getWidth();
		int height = this.getHeight();
		
		//use minimum so cells don't overflow off board
		cellsize = Math.min(height/numRows, width/numColumns);
		
		//have the boardcell's draw themselves
    	for(int row = 0;row < grid.length;row++) {
    		for(int col = 0; col < grid[0].length; col++) {
    			BoardCell cell = getCell(row,col);
    			cell.draw(g, cellsize);

    		}
    	}

    	//have the players draw themselves
    	
    	for(Player player : players) {
    		player.draw(g, cellsize);
    	}
    	
    	//have the boardcell's draw themselves
    	for(int row = 0;row < grid.length;row++) {
    		for(int col = 0; col < grid[0].length; col++) {
    			grid[row][col].setOffset(0);

    		}
    	}
    	
    	
    	//write the initials on the secret passages 
    	for(int row = 0; row < grid.length; row++) {
    		for(int col = 0; col < grid[0].length; col++) {
    			
    			//if the cells a secret passage we want to write an initial in black on the cell
    			if(grid[row][col].getIsSecretPassage()) {
    				g.setColor(Color.BLACK);
    				String initial = Character.toString((grid[row][col].getSecretPassage()));
    				
    				//this centers the initial in the secret tunnel cell
    				g.drawString(initial, col*cellsize+cellsize/4, row*cellsize+cellsize/2);
    			}
    		}
    	}
    	
    	//write the name on the center cell
    	for(int row = 0; row < grid.length; row++) {
    		for(int col = 0; col < grid[0].length; col++) {
    			if(grid[row][col].isLabel()) {
    				String roomName = this.getRoom(grid[row][col]).getName();
    				String[] words = roomName.split(" ");
    				//Room names with multiple words stack
    				for (int i =0; i<words.length; i++) {
    					g.setColor(Color.BLACK);
    					g.setFont(new Font("Ink Free", Font.BOLD, 16));
        				g.drawString(words[i], col*cellsize, row*cellsize+(i*cellsize));
    				}
    			}
    		}
    	}
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
		roomCards = new ArrayList<>();
		personCards = new ArrayList<>();
		weaponCards = new ArrayList<>();
		players = new ArrayList<>();
		deck = new ArrayList<>(); 
		
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

    /**
     * buildBoardCell: Based on the letter will put a cell, and set as either walkway and room.
     * @param row
     * @param squares
     * @param col
     * @param cell
     */
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
	
	
	/*
	 * Return the true if solution input matches the correct solution
	 */
	public boolean checkAccusation(Solution solution2) {

		return solution2.person.equals(solution.person)&&
				solution2.weapon.equals(solution.weapon)&&
				solution2.room.equals(solution.room);
	}

	/*
	 * handles the suggestion through each of the player
	 */
	public Card handleSuggestion(Solution suggestion, Player Playeraccuse) {
		return handleSuggestion(suggestion,Playeraccuse, false);
	}
	public Card handleSuggestion(Solution suggestion, Player Playeraccuse,Boolean skip_panel) {
		
		int currentplayer = players.indexOf(Playeraccuse);
		
		BoardCell currentLocation = getCell(Playeraccuse.getRow(), Playeraccuse.getCol());
		if (Playeraccuse instanceof HumanPlayer && currentLocation.isRoomCenter()) {
			movePlayer(currentLocation, getPlayer(guessDialogBox.getPerson()));
			Board.getInstance().repaint();
		}
		
	

		for (int i =0; i< players.size(); i++) {
			currentplayer = (currentplayer+1) % players.size();
			Player player_ind = players.get(currentplayer);

			if(player_ind == Playeraccuse) {
				break;
			}

			Card pullcard = player_ind.disproveSuggestion(suggestion);
			
			if(pullcard != null) {
				if(skip_panel == true) {
					return pullcard;
				}
				if (Playeraccuse instanceof HumanPlayer) {
					String playerName = player_ind.getName();
					String setGuessText = getGuessDialogBox().getRoom().getCardName() + ", " + getGuessDialogBox().getPerson().getCardName() + ", " + getGuessDialogBox().getWeapon().getCardName();
					
					ClueGame.getControlPanel().setGuess(setGuessText);
					ClueGame.getControlPanel().setGuessResult(playerName + " shows you: " + pullcard.getCardName());
					
				}
				else {
					String playerName = player_ind.getName();
					ClueGame.getControlPanel().setGuessResult("Suggestion disproved by: " + playerName);
				}
				
				return pullcard;
			}
			else {
				ClueGame.getControlPanel().setGuessResult("No new clue");
				
			}
		}
		
		return null;

	}

	
	private class cellMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			
		}
		@Override
		public void mouseExited(MouseEvent e) {
			
		}
		
		
		@Override
		public void mousePressed(MouseEvent e) {
			// if it is the human players turn, then they can click on the target cells
			
			if(Board.getInstance().getActivePlayer() instanceof HumanPlayer) {
				//get the clicked cell
				int row = e.getY()/cellsize;
				int col = e.getX()/cellsize;
				BoardCell cellClicked = grid[row][col];
				
				
				//can't click on a cell if it is not highlighted
				if(cellClicked.getHighlight()) {
					
					//if the cell clicked on is a room, move to center of the room
					if(cellClicked.isRoom()) {
						cellClicked = roomMap.get(cellClicked.getInitial()).getCenterCell();
					}
					
					//human player moves to the clicked cell
					((HumanPlayer) Board.getInstance().getActivePlayer()).setBegin_turn(true);
					movePlayer(cellClicked, Board.getInstance().getActivePlayer());
					
					//if cell clicked is a room
					if (cellClicked.isRoom()) {
			    		String currRoom = getRoom(cellClicked).getName();
			    		guessDialogBox = new GuessDialog(currRoom);
			    		guessDialogBox.setVisible(true);
			    		
			    	}

					
					setFinishedTurn(true);
					clearHighlightedCells();
					Board.getInstance().repaint();
				} 
				
				//try to click on a cell thats not highlighted 
				else {
					JOptionPane.showMessageDialog(null, "You can not move here");
				}
				
			//if it is not the players turn and they try to click, do nothing!	
			} else return;
		}
		
		
		public void clearHighlightedCells() {
			//loop through grid and set highlight to false
			for(int row = 0; row < grid.length; row++) {
				for(int col = 0; col < grid[0].length; col++) {
					grid[row][col].setHighlight(false);
				}
			}
		}
	}
	/**
	 * Creates a Highlight on the cell or room that the player can move to
	 * @param highlight
	 */
	
	public void highlight(boolean highlight) {
		if(targets != null) {//When the target is being activated
			for(BoardCell cell: targets) { // Iterating through each cell in targets
				if(cell.isRoom()) { // Check if cell room is room. Highlights all room board cell
					for(int row = 0; row < numRows; row++) {
						for(int col = 0; col < numColumns; col++ ) {
							if(grid[row][col].getInitial() == cell.getInitial()) {// Checking if current grid's board cell at row and col's initial is same as your current target cell's initial
								grid[row][col].setHighlight(highlight);
								grid[row][col].setRoom(true);
							}
						}
					}
				}
				else {// If cell room is not a room, then highlight this boardcell.
					cell.setHighlight(highlight);
				}
			} 
		}
		System.out.println();
	}
	
	public Player getActivePlayer() {
		return activePlayer;
	}

	public boolean getFinishedTurn() {

		return finishedTurn;
	}
	
	public void setFinishedTurn(Boolean finishedTurn) {
		this.finishedTurn = finishedTurn;
	}

	public boolean getGameOver() {

		return gameOver;
	}

	public void setConfigFiles(String csv, String txt) {
		setupConfigFile = "data/" + txt;
		layoutConfigFile = "data/" + csv;
	}
	
	public Set<BoardCell> getAdjList(int row, int col) {
		return grid[row][col].getAdjList();
		//return adj at sep board cell
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public Map<Character, Room> getRoomMap() {
		return roomMap;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public Color getPlayerColor(String playerName) {
		ArrayList<Player> players = getPlayers();
		Color color = null;
		for (Player player: players) {
			if (playerName.equals(player.getName())){
				color = player.getColorJavaType();
			}
		}
		return color;
	}

	public ArrayList<Card> getDeck() {
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

	public Card getCard(String cardName) {
		for(Card card: deck) {
			if(card.getCardName().equals(cardName)) {
				return card;
			}
		}
		return null;
	}
	
	public GuessDialog getGuessDialogBox() {
		return guessDialogBox;
	}

	public void setGuessDialogBox(GuessDialog guessDialogBox) {
		this.guessDialogBox = guessDialogBox;
	}
	

	
	public Player getPlayer(Card card) {
		for (Player player : players) {
			if (card.getCardName().equals(player.getName())) {
				return player;
			}
		}

		return null;
	}






}