package controllers;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioController {

    private transient Clip clip;
    private transient long pausePoint;

    /**
     * Method plays music in a separate thread using a .wav file loaded from a path.
     * @param path path to the wav file to play
     */
    public void playMusic(String path) {
        try {
            File music = new File(path);

            if (music.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(music);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("Can't find bgm file");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method plays music in a separate thread using a .wav file loaded from a path.
     * @param path path to the wav file to play
     */
    public void playSound(String path) {
        try {
            File sound = new File(path);

            if (sound.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(sound);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } else {
                System.out.println("Can't find bgm file");
            }
        } catch (Exception ex) {
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
     * Stops current audio playback, resetting the pause timer to 0.
     */
    public void stop() {
        if (clip.isActive()) {
            clip.stop();
            pausePoint = 0;
        }
    }

    public Clip getClip() {
        return clip;
    }
}
