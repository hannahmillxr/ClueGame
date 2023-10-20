/*
 * Author: Hannah Miller and Gillian Culberson
 * Description: BadConfigFormatExcption Class is to defualt to a print or message when exception occurs
 */
package clueGame;
import java.io.FileNotFoundException;

public class BadConfigFormatException extends Exception {
	
	public BadConfigFormatException() {
		super("Error: Proper is configured improperly");
	}
	
	public BadConfigFormatException(String message)  {
		super(message);	
	}
	
	
}
