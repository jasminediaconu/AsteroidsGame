package controllers;

import java.io.File;
import java.util.Random;
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
     * Stops current audio playback, resetting the pause timer to 0.
     */
    public void stop() {
        if (clip.isActive()) {
            clip.stop();
            pausePoint = 0;
        }
    }

    /**
     * Getter for the Clip.
     * @return the Clip used by this controller
     */
    public Clip getClip() {
        return clip;
    }

    /**
     * Method that plays the laser sound.
     */
    public void playLaser() {
        playSound("src/main/resources/audio/laser_lo.wav");
    }

    /**
     * Plays a random explosion sound.
     */
    public void playExplosion() {
        Random random = new Random();
        int track = random.nextInt(4) + 1;
        playSound("src/main/resources/audio/exp_" + track + ".wav");
    }

    /**
     * Plays thruster sound.
     */
    public void playThrusterSound() {
        if (getClip() == null) {
            playSound("src/main/resources/audio/thrust.wav");
        } else if (getClip() != null && !getClip().isActive()) {
            playSound("src/main/resources/audio/thrust.wav");
        }
    }

    /**
     * Stops thruster sound if playing.
     */
    public void stopThrusterSound() {
        if (getClip() != null && getClip().isActive()) {
            stop();
        }
    }

    /**
     * Plays rotation sound.
     */
    public void playRotatingSound() {
        if (getClip() == null) {
            playSound("src/main/resources/audio/rotate.wav");
        } else if (getClip() != null && !getClip().isActive()) {
            playSound("src/main/resources/audio/rotate.wav");
        }
    }

    /**
     * Stops rotation sound if playing.
     */
    public void stopRotatingSound() {
        if (getClip() != null && getClip().isActive()) {
            stop();
        }
    }
    
}
