package controllers;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;

public class AudioController extends Thread{

    private Clip clip;
    private long pausePoint;

    /**
     * Method plays music in a separate thread using a .wav file loaded from a path.
     * @param path path to the wav file to play
     */
    public void playMusic(String path) {
        try {
            File music = new File(path);

            if(music.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(music);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            else {
                System.out.println("Can't find bgm file");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method pauses music at current point if it is playing.
     */
    public void pause() {
        if (clip.isActive()) {
            pausePoint = clip.getMicrosecondPosition();
            clip.stop();
        }
    }

    /**
     * Method plays music at the point it was paused.
     */
    public void resumePlayback() {
        if (!clip.isActive()) {
            clip.setMicrosecondPosition(pausePoint);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Getter for Clip object.
     * @return Clip the current audio clip
     */
    public Clip getClip() {
        return clip;
    }

    /**
     * Getter for pause point in milliseconds.
     * @return long Latest point the clip was paused at
     */
    public long getPausePoint() {
        return pausePoint;
    }
}
