package main;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	Clip clip;
	URL soundURL[] = new URL[30];
	
	public Sound() {
		
		soundURL[0] = getClass().getResource("/sound/Main_Theme.WAV");
		soundURL[1] = getClass().getResource("/sound/Castle_Theme.WAV");
		soundURL[2] = getClass().getResource("/sound/Shop_Theme.WAV");
		soundURL[3] = getClass().getResource("/sound/Shop_Theme_2.WAV");
		soundURL[4] = getClass().getResource("/sound/Door_Open.WAV");
		soundURL[5] = getClass().getResource("/sound/Pickup.WAV");
		soundURL[6] = getClass().getResource("/sound/Fanfare.WAV");
		soundURL[7] = getClass().getResource("/sound/Pickup2.WAV");
		soundURL[8] = getClass().getResource("/sound/Damage.WAV");
	}
	
	public void setFile(int i) {
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void play() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
	
}
