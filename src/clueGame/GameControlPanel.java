package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;



public class GameControlPanel extends JPanel {

	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	
	private JTextField whoseTurn;
	private JTextField guess;
	private JTextField guessResult;
	private JTextField numRolls;
	private GameControlPanel gui;


	
	public GameControlPanel()  {
		gui = this;
		setSize(300, 150);		
		JPanel bigPanel = new JPanel();
		bigPanel.setLayout(new GridLayout(2, 0));
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 4));
		bigPanel.add(panel, BorderLayout.NORTH);
		
		JPanel whosNextPanel = new JPanel();
		whosNextPanel.setLayout(new GridLayout(2, 0));
		JLabel nameLabel = new JLabel("Whose Turn?");
		whoseTurn = new JTextField(10);
		add(nameLabel, BorderLayout.NORTH);
		add(whoseTurn, BorderLayout.SOUTH);
		
		JPanel createRollPanel = new JPanel();
		createRollPanel.setLayout(new GridLayout(2, 0));
		JLabel rollNameLabel = new JLabel("Roll:");
		numRolls = new JTextField(10);
		add(rollNameLabel, BorderLayout.NORTH);
		add(numRolls, BorderLayout.CENTER);
		
		JButton makeAccusationButton = new JButton();
		JButton NextButton = new JButton();
		
		JPanel guessPanel = new JPanel();
		panel.add(guessPanel, BorderLayout.SOUTH);
		guessPanel.setLayout(new GridLayout(0, 2));
		bigPanel.add(guessPanel, BorderLayout.SOUTH);
		
		JPanel makeGuessPanel = new JPanel();
		guessPanel.add(makeGuessPanel, BorderLayout.WEST);
		makeGuessPanel.setLayout(new GridLayout(1,0));
		
		guess = new JTextField(10);
		makeGuessPanel.add(guess, BorderLayout.CENTER);
		
		JPanel guessResultPanel = new JPanel();
		guessPanel.add(guessResultPanel, BorderLayout.EAST);
		guessResultPanel.setLayout(new GridLayout(1,0));
		
		guessResult = new JTextField(10);
		guessResultPanel.add(guessResult, BorderLayout.CENTER);
	

	}

	
	
	private void setTurn(ComputerPlayer computerPlayer, int i) {

		
	}
	
	private void setGuessResult(String string) {

		
	}

	private void setGuess(String string) {
				
	}
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		// test filling in the data
		//panel.setTurn(new ComputerPlayer( "Col. Mustard", 0, 0, "orange"), 5);
		//panel.setGuess( "I have no guess!");
		//panel.setGuessResult( "So you have nothing?");
	}




}
		