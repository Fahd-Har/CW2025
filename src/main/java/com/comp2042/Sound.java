package com.comp2042;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    private Clip clip;
    private final URL soundURL[] = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/background.wav");
        soundURL[1] = getClass().getResource("/rotation.wav");
        soundURL[2] = getClass().getResource("/delete line.wav");
        soundURL[3] = getClass().getResource("/gameover.wav");
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

    public void setSound(int i) {
        try {
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }
            if (clip != null) {
                clip.close();
            }

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            throw new RuntimeException(e);
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

    public void pause() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void resume() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.stop();
        }
    }
}