package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ClueGame extends JFrame{
	private static Board board;
	private static GameCardPanel cardPanel;
	private static GameControlPanel controlPanel;

	public ClueGame(){
		this.setTitle("Kung-Fu Panda Clue Game");
		this.setSize(850,675);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("newClueBoardcsv.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();

		cardPanel = new GameCardPanel();
		controlPanel = new GameControlPanel();
	}
	
	public static void main(String[] args) {
	
		ClueGame clueGame = new ClueGame();		
		clueGame.setVisible(true);
		clueGame.setLayout(new BorderLayout());
		clueGame.add(board, BorderLayout.CENTER);
		clueGame.add(controlPanel, BorderLayout.SOUTH);
		clueGame.add(cardPanel, BorderLayout.EAST);
	}
}

