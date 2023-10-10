package clueGame;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import experiment.TestBoardCell;

public class Board {

	Map <BoardCell, Set<BoardCell>> roomMap;
	private BoardCell [][] grid;
	String layoutConfigFile;
	String setupConfigFile;
	private static int numColumns = 4;
	private static int numRows = 4;
	
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
    	BoardCell cell = new BoardCell();
		return cell;
	}
    
    public Room getRoom(BoardCell cell) {
    	Room room = new Room();
		return room;
	}
    
    public void setConfigFiles(String csvFile, String txtFile) {

	}
    
    public void loadSetupConfig() throws BadConfigFormatException {
    	
    }
    
    public void loadLayoutConfig() throws BadConfigFormatException{
    	
    }
    
    
    
    
}
