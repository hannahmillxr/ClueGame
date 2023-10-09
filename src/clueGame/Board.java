package clueGame;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import experiment.TestBoardCell;

public class Board {

	Map <TestBoardCell, Set<TestBoardCell>> roomMap;
	private TestBoardCell [][] grid;
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
    
    
}
