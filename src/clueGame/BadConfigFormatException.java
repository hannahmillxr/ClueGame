package clueGame;

public class BadConfigFormatException extends Exception {
	
	public BadConfigFormatException() {
		super("Error: Proper is configured improperly");
	}
	
	public BadConfigFormatException(String message)  {
		super(message);
			
	}
	
	
}
