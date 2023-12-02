package clueGame;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;


public class Music {

	public static void playMusic(String sound) {
		try {
		AudioInputStream play = AudioSystem.getAudioInputStream(new File(sound));
		Clip clip = AudioSystem.getClip();
		clip.open(play);
     	clip.loop(0);
		}catch(UnsupportedAudioFileException e){
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}catch(LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}


//
//Music.RunMusic("https://www.youtube.com/watch?v=QspjKVTMlL8");