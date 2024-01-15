package lk.ijse.thehenhouse.Sound;

import javax.sound.sampled.*;
import java.io.File;

public class Notify {
    public static void playSound() {
        try {
            // specify the sound file to be played
            String soundFile = "D:\\Semeseter 1 Final Project\\theHenHouse\\src\\main\\resources\\sound\\notificationsoft_CuqIfJRM.wav";

            // create a new AudioInputStream from the sound file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFile));

            // get the format of the audio data
            AudioFormat audioFormat = audioInputStream.getFormat();

            // create a DataLine.Info object that describes the audio format
            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);

            // check if the audio format is supported
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Audio format not supported!");
                return;
            }

            // create a new clip object
            Clip clip = (Clip) AudioSystem.getLine(info);

            // open the audio input stream
            clip.open(audioInputStream);

            // start playing the clip
            clip.start();
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }
}
