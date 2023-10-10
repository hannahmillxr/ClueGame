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

import experiment.TestBoardCell;

public class Board {

	Map <Character, Room> roomMap= new HashMap<Character, Room>();
	private BoardCell [][] grid;
	String layoutConfigFile;
	String setupConfigFile;
	private static int numColumns = 25;
	private static int numRows = 30;
	
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
    
    public void initialize()
    {
    	
    }
    
    public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}
	
	public Room getRoom(char symbol) {
		Room room = new Room();
		return room;
	}
    
    public BoardCell getCell(int row, int col) {
    	BoardCell cell = new BoardCell(row, col);
		return cell;
	}
    
    public Room getRoom(BoardCell cell) {
    	Room room = new Room();
		return room;
	}
    
    public void setConfigFiles(String csv, String txt) {
    	setupConfigFile = "data/" + txt;
    	layoutConfigFile = "data/" + csv;
	}
    
    
    //reads in text file 
    public void loadSetupConfig() throws BadConfigFormatException {
		ArrayList<String> textFile = new ArrayList<String>();
		
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
		
		//text file has structure: [Room, name, initial] can be room or space 
		for (String line : textFile) {
			String[] word = line.split(", ");
			
			//make sure you are not adding comment line from txt file
			if (word[0].equals("Room") || word[0].equals("Space")) {
				Room room = new Room(word[1]);
				Character initial = word[2].charAt(0);
				roomMap.put(initial, room);
			}
		}
    	
    }
    
    
    //reads in csv file
    public void loadLayoutConfig() throws BadConfigFormatException{
    	ArrayList<String> csvFile = new ArrayList<String>();
    	grid = new BoardCell[numRows][numColumns];
    	try {
    		FileReader reader = new FileReader(layoutConfigFile);
			Scanner in = new Scanner(reader);
			while (in.hasNextLine()) {
				String readInNext = in.nextLine();
				csvFile.add(readInNext);
			}
			in.close();
			
			//if there is a room that is not in our legend, throw throw new BadConfigFormatException("Room found that is not in legend")
			
			
			//if rows are different lengths each time, throw new BadConfigFormatException("Rows have varying lengths")
			
			
			//make sure format is correct, if there are two characters in one index, then pass in the second character in the paramaterized constructer
			
			
			
			
			
			
		} catch(FileNotFoundException e) {
			System.out.println(e);
			System.out.println(e.getMessage());
		}
    }
    
    
    
    
}
