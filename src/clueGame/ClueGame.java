/*
 * Author: Hannah Miller and Gillian Culberson
 */
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
	
	/*
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		switch(Color.color)) {
		case("Po"):
			g.fillOval(7, 0, WIDTH_DOT, HEIGHT_DOT);
			g.setColor(Color.lightGray);
		case("Tigress"):
			g.fillOval(6, 29, WIDTH_DOT, HEIGHT_DOT);
			g.setColor(Color.red);
		case("Mantis"):
			g.fillOval(13, 29, WIDTH_DOT, HEIGHT_DOT);
			g.setColor(Color.green);
		case("Monkey"):
			g.fillOval(13, 0, WIDTH_DOT, HEIGHT_DOT);
			g.setColor(Color.magenta);
		case("Crane"):
			g.fillOval(1, 16, WIDTH_DOT, HEIGHT_DOT);
			g.setColor(Color.white);
		case("Viper"):
			g.fillOval(24, 25, WIDTH_DOT, HEIGHT_DOT);
			g.setColor(Color.yellow);	
	}
	*/
	
	
	
	public static void main(String[] args) {
	
		ClueGame clueGame = new ClueGame();		
		
		clueGame.setLayout(new BorderLayout());
		clueGame.add(board, BorderLayout.CENTER);
		clueGame.add(controlPanel, BorderLayout.SOUTH);
		clueGame.add(cardPanel, BorderLayout.EAST);
		
		
		controlPanel.setTurn(new ComputerPlayer( "Po", "Black", 3, 2), 5);
		controlPanel.setGuess( "I have no guess!");
		controlPanel.setGuessResult( "So you have nothing?");
		
		clueGame.setVisible(true);
		
	}
	
	public static GameControlPanel getControlPanel() {
		return controlPanel;
	}

	
}

