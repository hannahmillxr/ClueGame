package clueGame;

import javax.swing.JComboBox;
import javax.swing.JDialog;

public class GuessDialog extends JDialog {
	private JComboBox<String> person, room, weapon;
	
	public GuessDialog(Board board) {
		person = new JComboBox<String>();
		for(Card p: board.getPersonCards()) {
			person.addItem(p.getCardName());;
		}
		add(person);
		room = new JComboBox<String>();
		for(Card r: board.getRoomCards()) {
			room.addItem(r.getCardName());
		}
		add(room);
		
		weapon = new JComboBox<String>();
		for(Card w: board.getWeaponCards()) {
			person.addItem(w.getCardName());
		}
		add(weapon);
		
		
	}
	
	
	

}
