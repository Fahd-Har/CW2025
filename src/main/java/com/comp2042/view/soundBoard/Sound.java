package com.comp2042.view.soundBoard;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    private final Clip[] clips = new Clip[30];
    private Clip clip;

    public Sound() {
        try {
            // Load all sound resources
            URL[] soundURL = new URL[30];
            soundURL[0] = getClass().getResource("/soundBackground/background.wav");
            soundURL[1] = getClass().getResource("/soundBackground/rotation.wav");
            soundURL[2] = getClass().getResource("/soundBackground/delete line.wav");
            soundURL[3] = getClass().getResource("/soundBackground/gameover.wav");

            // Preload clips into memory
            for (int i = 0; i < soundURL.length; i++) {
                if (soundURL[i] != null) {
                    AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
                    clips[i] = AudioSystem.getClip();
                    clips[i].open(ais);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void soundEffects(int i) {
        setSound(i);
        play();
    }

    public void bgMusic() {
        setSound(0);
        play();
        loop();
    }

    private void setSound(int i) {
        if (i >= 0 && i < clips.length) {
            clip = clips[i];
        }
    }


    private void play() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    private void loop() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void resume() {
        if (clip != null) {
            clip.start();
        }
    }
}
