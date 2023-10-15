package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;




public class Board {

	Map <Character, Room> roomMap;
	Map <BoardCell, Set<BoardCell>> adjMtx;
	private BoardCell [][] grid;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	String layoutConfigFile;
	String setupConfigFile;
	private int numColumns;
	private int numRows;
	
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
    
    public void setConfigFiles(String csv, String txt) {
    	setupConfigFile = "data/" + txt;
    	layoutConfigFile = "data/" + csv;
	}
    
    public void calcTargets(BoardCell startCell, int pathlength) { 
    	
    	targets = new HashSet <BoardCell>();
    	visited = new HashSet <BoardCell>();
    	calculations (startCell, pathlength);


    }
    
    public void calculations (BoardCell startCell, int pathlength) { 
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
    				calculations(cell, numSteps-1);
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
						
						
						cell.setInitial(squares[j].charAt(0));
						
						if (squares[j].charAt(0) != 'W' && squares[j].charAt(0) != 'X') {
							cell.setRoom(true);
							cell.setWalkway(false);
						}
						else if (squares[j].charAt(0) == 'W'){
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
						grid[i][j] = cell;
							
					}
					else {
						BoardCell cell = new BoardCell(i, j);
						cell.setInitial(squares[j].charAt(0));
						
						if (squares[j].charAt(0) != 'W' && squares[j].charAt(0) != 'X') {
							cell.setRoom(true);
							cell.setWalkway(false);
						}
						else if (squares[j].charAt(0) == 'W'){
							cell.setRoom(false);
							cell.setWalkway(true);	
						}
						else {
							cell.setRoom(false);
							cell.setWalkway(false);
						}		
						
						if (cell.isRoomCenter()) {
							roomMap.get(cell.getInitial()).setCenterCell(cell);
						}
						if (cell.isLabel()) {
							roomMap.get(cell.getInitial()).setLabelCell(cell);
						}
						grid[i][j] = cell;
					}
										
					//if there is a room that is not in our legend, throw new BadConfigFormatException("Room found that is not in legend")
					
					if (!roomMap.containsKey(squares[j].charAt(0))) {
						throw new BadConfigFormatException("Room found that is not in legend");
					}
				}
					
				
			}	
			
		} catch(FileNotFoundException e) {
			System.out.println(e);
			System.out.println(e.getMessage());
		}
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


    
    
}
