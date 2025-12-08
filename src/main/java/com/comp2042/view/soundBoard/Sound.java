package com.comp2042.view.soundBoard;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

/**
 * Manages all audio resources, including background music (BGM) and sound effects (SFX).
 * It preloads audio clips for quick playback and provides methods to control audio playback (play, loop, stop, resume).
 */
public class Sound {
    private final Clip[] clips = new Clip[30];
    private Clip clip;

    /**
     * Constructs the Sound manager and preloads the specific sound files into memory.
     */
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

    /**
     * Sets the specified sound clip, resets it to the start, and plays it once.
     * This is used for all transient sound effects (SFX).
     *
     * @param i The index of the sound URL to play (0=BGM, 1=Rotation, 2=Line Clear, 3=Game Over).
     */
    public void soundEffects(int i) {
        setSound(i);
        play();
    }

    /**
     * Starts the background music (BGM) clip and sets it to loop continuously.
     * It uses the clip at index 0.
     */
    public void bgMusic() {
        setSound(0);
        play();
        loop();
    }

    /**
     * Sets the currently active {@code Clip} based on the provided index.
     *
     * @param i The index of the desired clip in the {@code clips} array.
     */
    private void setSound(int i) {
        if (i >= 0 && i < clips.length) {
            clip = clips[i];
        }
    }

    /**
     * Resets the active clip to the beginning and starts playback once.
     */
    private void play() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    /**
     * Sets the active clip to loop continuously.
     */
    private void loop() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Stops the playback of the active clip.
     */
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Resumes playback of the active clip from its current position.
     */
    public void resume() {
        if (clip != null) {
            clip.start();
        }
    }
}