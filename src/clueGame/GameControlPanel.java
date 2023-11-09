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
		setLayout(new GridLayout(2, 0));
		
		// First panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 4));
		
		// Creates and adds whoPanel
		JPanel whosNextPanel = new JPanel();
		JLabel nameLabel = new JLabel("Whose Turn?");
		whoseTurn = new JTextField(10);
		whosNextPanel.add(nameLabel);
		whosNextPanel.add(whoseTurn);
		panel.add(whosNextPanel);
		
		// Creates and adds roll panel
		JPanel createRollPanel = new JPanel();
		JLabel rollNameLabel = new JLabel("Roll:");
		numRolls = new JTextField(10);
		createRollPanel.add(rollNameLabel);
		createRollPanel.add(numRolls);
		panel.add(createRollPanel);
		
		//Creates the buttons
		JButton makeAccusationButton = new JButton("Accusation: ");
		JButton NextButton = new JButton("Next!");
		panel.add(makeAccusationButton);
		panel.add(NextButton);
		
		//Creates the guess second panal
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(0, 2));
		panel.add(guessPanel);
		
		//Creates the guess text box and outline of the left side guess
		JPanel makeGuessPanel = new JPanel();
		makeGuessPanel.setLayout(new GridLayout(1,0));
		makeGuessPanel.setBorder(new TitledBorder( new EtchedBorder(), "Guess"));
		guess = new JTextField(10);
		makeGuessPanel.add(guess);
		guessPanel.add(makeGuessPanel);
		
		//Creates the guess result text box and outline of the left side guess
		JPanel guessResultPanel = new JPanel();
		guessResultPanel.setLayout(new GridLayout(1,0));
		guessResultPanel.setBorder(new TitledBorder( new EtchedBorder(), "Guess Result"));
		guessResult = new JTextField(10);
		guessResultPanel.add(guessResult);
		guessPanel.add(guessResultPanel);
	
		// Adds all your panels to big panel!
		add(panel);
		add(guessPanel);

	}

	
	
	private void setTurn(ComputerPlayer computerPlayer, int roll) {
		whoseTurn.setText(computerPlayer.getName());
		numRolls.setText(Integer.toString(roll));
		
	}
	
	private void setGuessResult(String guess) {
		guessResult.setText(guess);
		
	}

	private void setGuess(String thisGuess) {
		guess.setText(thisGuess);	
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
		frame.setSize(650, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Po", "Black", 3, 2), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}




}
		