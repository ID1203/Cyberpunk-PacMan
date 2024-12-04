package idi135;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {
    // Method to load and play a sound
    public static void playSound(String filePath) {
        try {
            // Create a File object for the audio file
            File soundFile = new File(filePath);
            
            // Get the audio input stream from the file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            
            // Get the clip to play the sound
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            
            // Play the sound
            clip.start();
            
            // Optional: Loop background music
            if (filePath.equals("java-man/src/main/resources/pacman_beginning.wav")) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);  // Loop forever
            }
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to stop any sound (useful for stopping background music if needed)
    public static void stopSound(Clip clip) {
        clip.stop();
    }
}
