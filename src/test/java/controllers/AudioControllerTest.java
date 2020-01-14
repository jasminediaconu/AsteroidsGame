package controllers;

import org.junit.jupiter.api.Test;

import javax.sound.sampled.Clip;
import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AudioControllerTest {
    @Test
    void playExistingFileTest() {
        AudioController ac = new AudioController();
        ac.playMusic("src/test/resources/bgm.wav");
        Clip clip = ac.getClip();
        assertFalse(clip.isActive());
    }
}
