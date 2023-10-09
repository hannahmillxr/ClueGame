package clueGame;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import experiment.TestBoardCell;

public class Board {

	Map <BoardCell, Set<BoardCell>> roomMap;//wrong type other side should be room, cell or char
	private BoardCell [][] grid;
	String layoutConfigFile;
	String setupConfigFile;
	static int numColumns = 4;
	static int numRows = 4;
	
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
    
    public void loadSetupConfig() throws BadConfigFormatException {
    	
    }
    
    public void loadLayoutConfig() throws BadConfigFormatException{
    	
    }

	public void setConfigFiles(String string, String string2) {
		// TODO Auto-generated method stub
		
	}

	public Room getRoom(char c) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getNumRows() {
		// TODO Auto-generated method stub
		return numRows;
	}

	public int getNumColumns() {
		// TODO Auto-generated method stub
		return numColumns;
	}

	public BoardCell getCell(int i, int j) {
		// TODO Auto-generated method stub
		return grid[i][j];
	}

	public Room getRoom(BoardCell cell) {
		// TODO Auto-generated method stub
		return cell.isRoom();
	}
    
    
    
}
