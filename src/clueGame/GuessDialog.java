package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;


public class GuessDialog extends JDialog {
	private JComboBox<String> person, room, weapon;
	GuessDialog guessDialog;
	
	public GuessDialog(String roomName) {
		guessDialog = this;
		
		JLabel roomLabel = new JLabel("Room");
		this.add(roomLabel);
		room = new JComboBox<String>();
		for(Card r: Board.getInstance().getRoomCards()) {
			room.addItem(r.getCardName());
		}
		add(room);
		
		JLabel playerLabel = new JLabel("Person");
		this.add(playerLabel);
		person = new JComboBox<String>();
		for(Card p: Board.getInstance().getPersonCards()) {
			person.addItem(p.getCardName());;
		}
		add(person);
		
		JLabel weaponLabel = new JLabel("Weapon");
		this.add(weaponLabel);
		weapon = new JComboBox<String>();
		for(Card w: Board.getInstance().getWeaponCards()) {
			weapon.addItem(w.getCardName());
		}
		add(weapon);
		
		
		//if suggestion then the room needs to be the room passed in 
		this.setTitle("Make a suggestion");
		room.setSelectedItem(roomName);
		//cannot modify the room
		room.setEnabled(false);
		
		
		this.setLayout(new GridLayout(0, 2));
		this.setSize(450, 300);
	
		//submit and cancel buttons
		JButton submitButton = new JButton("Submit");
		JButton cancelButton = new JButton("Cancel");

		submitButton.addActionListener(new submitButtonListener());
		cancelButton.addActionListener(new cancelButtonListener());

		this.add(submitButton);
		this.add(cancelButton);

	}
	
	private class submitButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Board.getInstance().getGuessDialogBox().setVisible(false);
			Solution solution = new Solution(getRoom(), getPerson(), getWeapon());
			Player player = Board.getInstance().getActivePlayer();
			
			
			Card disproveCard = Board.getInstance().handleSuggestion(solution, player);
			player.addDisprove(disproveCard);
			
			BoardCell currentLocation = Board.getInstance().getCell(player.getRow(), player.getCol());
			
			Board.getInstance().movePlayer(currentLocation, Board.getInstance().getPlayer(getPerson()));
		
			
		}
	}

	private class cancelButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//end turn?
			Board.getInstance().getGuessDialogBox().setVisible(false);
			Board.getInstance().setFinishedTurn(true);
		}
	}




	
	public Card getRoom() {
		String selectedRoom = room.getSelectedItem().toString();
		for (Card roomCard : Board.getInstance().getRoomCards()) {
			if (roomCard.getCardName().equals(selectedRoom)) {
				return roomCard;
			}
		}
		
		return null;
	}

	public Card getPerson() {
		
		String selectedPerson = person.getSelectedItem().toString();
		for (Card personCard : Board.getInstance().getPersonCards()) {
			if (personCard.getCardName().equals(selectedPerson)) {
				return personCard;
			}
		}
		
		return null;
	}
	
	public Card getWeapon() {
		String selectedWeapon =  weapon.getSelectedItem().toString();
		for (Card weaponCard : Board.getInstance().getWeaponCards()) {
			if (weaponCard.getCardName().equals(selectedWeapon)) {
				return weaponCard;
			}
		}
		
		return null;
	}
	


}
