package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat.Field;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class GameCardPanel extends JPanel{
	private JTextField peopleInHand;
	private JTextField peopleSeen;
	private JTextField roomInHand;
	private JTextField roomSeen;
	private JTextField weaponInHand;
	private JTextField weaponSeen;
	private ArrayList<Card> cards;
	private ArrayList<Card> seenCards;
	
	public GameCardPanel()  {
		cards = Board.getInstance().getPlayers().get(0).getHand();
		seenCards = Board.getInstance().getPlayers().get(0).getSeenCards();
		this.setSize(200,675);
		
		this.setLayout(new GridLayout(3,1));
		this.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));	

		JLabel peopleHandLabel = new JLabel("In Hand");
		JLabel peopleSeenLabel = new JLabel("Seen");
		
		// People panel
		JPanel peoplePanel = new JPanel();
		peoplePanel.setLayout(new GridLayout(0, 1));
		peoplePanel.setBorder(new TitledBorder( new EtchedBorder(), "People"));
		peoplePanel.add(peopleHandLabel);
		
		
		setPeopleInHand(peoplePanel);
		
		peoplePanel.add(peopleSeenLabel);
		setPeopleSeen(peoplePanel);
		
		this.add(peoplePanel);
		
		//Room panel
		JLabel roomHandLabel = new JLabel("In Hand");
		JLabel roomSeenLabel = new JLabel("Seen");
		JPanel roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(0, 1));
		roomPanel.setBorder(new TitledBorder( new EtchedBorder(), "Rooms"));
		roomPanel.add(roomHandLabel);
		setRoomInHand(roomPanel);
		
		roomPanel.add(roomSeenLabel);
		setRoomSeen(roomPanel);
		
		this.add(roomPanel);
		

		//Weapon panel
		JLabel weaponHandLabel = new JLabel("In Hand");
		JLabel weaponSeenLabel = new JLabel("Seen");
		JPanel weaponPanel = new JPanel();
		weaponPanel.setLayout(new GridLayout(0, 1));
		weaponPanel.setBorder(new TitledBorder( new EtchedBorder(), "Weapons"));
		weaponPanel.add(weaponHandLabel);
		setWeaponInHand(weaponPanel);

		weaponPanel.add(weaponSeenLabel);
		setWeaponSeen(weaponPanel);
		
		this.add(weaponPanel);

	}
	
	private void setWeaponSeen(JPanel weaponPanel) {
		
		int count = 0;
		for (Card card: seenCards) {
			weaponSeen = new JTextField(15);
			if (card.getType() == CardType.WEAPON) {
				if (!cards.contains(card)) {
					weaponSeen.setText(card.getCardName());
					weaponPanel.add(weaponSeen);
					count++;
				}
			}
		}
		if (count == 0) {
			weaponSeen = new JTextField(15);
			weaponSeen.setText("None");
			weaponPanel.add(weaponSeen);
		}
		
	}

	private void setWeaponInHand(JPanel weaponPanel) {
		int count =0;
		for (Card card: cards) {
			weaponInHand = new JTextField(15);
			if (card.getType() == CardType.WEAPON) {
				weaponInHand.setText(card.getCardName());
				weaponPanel.add(weaponInHand);
				count++;
			}
		}
		if (count == 0) {
			weaponInHand = new JTextField(15);
			weaponInHand.setText("None");
			weaponPanel.add(weaponInHand);
		}
		
	}

	private void setRoomSeen(JPanel roomPanel) {
		int count = 0;
		for (Card card: seenCards) {
			roomSeen = new JTextField(15);
			if (card.getType() == CardType.ROOM) {
				if (!cards.contains(card)) {
					roomSeen.setText(card.getCardName());
					roomPanel.add(roomSeen);
					count++;
				}
			}
		}
		if (count == 0) {
			roomSeen = new JTextField(15);
			roomSeen.setText("None");
			roomPanel.add(roomSeen);
		}
		
	}

	private void setRoomInHand(JPanel roomPanel) {
		int count = 0;
		for (Card card: cards) {
			roomInHand = new JTextField(15);
			if (card.getType() == CardType.ROOM) {
				roomInHand.setText(card.getCardName());
				roomPanel.add(roomInHand);
				count++;
			}
		}
		if (count == 0) {
			roomInHand = new JTextField(15);
			roomInHand.setText("None");
			roomPanel.add(roomInHand);
		}
	}

	private void setPeopleInHand(JPanel peoplePanel) {
		int count = 0;
		for (Card card: cards) {
			peopleInHand = new JTextField(15);
			if (card.getType() == CardType.PERSON) {
				Color color = Board.getInstance().getPlayerColor(card.getCardName());
				peopleInHand.setBackground(color);
				peopleInHand.setText(card.getCardName());
				peoplePanel.add(peopleInHand);
				count++;
			}
		}
		if (count == 0) {
			peopleInHand = new JTextField(15);
			peopleInHand.setText("None");
			peoplePanel.add(peopleInHand);
		}
	}
	
	private void setPeopleSeen(JPanel peoplePanel) {
		int count = 0;
		for (Card card: seenCards) {
			peopleSeen = new JTextField(15);
			if (card.getType() == CardType.PERSON) {
				
				if (!cards.contains(card)) {
					peopleSeen.setText(card.getCardName());
					Color color = Board.getInstance().getPlayerColor(card.getCardName());
					peopleSeen.setBackground(color);
					
					peoplePanel.add(peopleSeen);
					count++;
				}
				
			}
		}
		if (count == 0) {
			peopleSeen = new JTextField(15);
			peopleSeen.setText("None");
			peoplePanel.add(peopleSeen);
		}
	}


	
	public static void main(String[] args) {
		Board board;
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("newClueBoardcsv.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
		
		//We want the panel to look good if everything is seen
		Player humanPlayer = Board.getInstance().getPlayers().get(0);
		humanPlayer.clearHand();
		humanPlayer.updateHand(new Card("Po", CardType.PERSON));
		humanPlayer.updateHand(new Card("Tigress", CardType.PERSON));
		humanPlayer.updateHand(new Card("Fist", CardType.WEAPON));
		
		humanPlayer.clearSeen();
		humanPlayer.updateSeen(new Card("Shen's Cannon", CardType.WEAPON));
		humanPlayer.updateSeen(new Card("Yin Yang Staff", CardType.WEAPON));
		humanPlayer.updateSeen(new Card ("Frying Pan", CardType.WEAPON));
		humanPlayer.updateSeen(new Card("Jade Dagger", CardType.WEAPON));
		humanPlayer.updateSeen(new Card ("Crane", CardType.PERSON));
		humanPlayer.updateSeen(new Card("Monkey", CardType.PERSON));
		humanPlayer.updateSeen(new Card ("Mantis", CardType.PERSON));
		humanPlayer.updateSeen(new Card("Kitchen", CardType.ROOM));
		humanPlayer.updateSeen( new Card("Armory", CardType.ROOM));
		humanPlayer.updateSeen(new Card ("Bedrooms", CardType.ROOM));
		humanPlayer.updateSeen(new Card("Meditation Room", CardType.ROOM));
		humanPlayer.updateSeen(new Card("Dojo", CardType.ROOM));
		humanPlayer.updateSeen(new Card ("Scroll Room", CardType.ROOM));
		humanPlayer.updateSeen(new Card ("Cherry Blossom Room", CardType.ROOM));
		humanPlayer.updateSeen(new Card ("Court Yard", CardType.ROOM));

		
		
		GameCardPanel panel = new GameCardPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(150, 690);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

	}

}
