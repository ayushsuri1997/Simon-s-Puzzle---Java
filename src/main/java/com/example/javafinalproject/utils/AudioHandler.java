package com.example.javafinalproject.utils;

import javafx.scene.media.AudioClip;

/** Handler for the Audio Clips which is played when the user wins or loses.
 * @author Ayush
 * @version 1.0
 */
public class AudioHandler {

    private AudioClip winSound;
    private AudioClip loseSound;

    /**
     * Set the path for win sound and lose sound
     */
    public AudioHandler() {
        winSound = new AudioClip(getClass().getResource("/audio/win.wav").toExternalForm());
        loseSound = new AudioClip(getClass().getResource("/audio/lose.wav").toExternalForm());
    }

    /**
     * To get the win sound in order to play it
     * @return The win sound of type AudioClip
     */
    public AudioClip getWinSound() {
        return winSound;
    }

    /**
     * To get the lost sound in order to play it
     * @return The lost sound of type AudioClip
     */
    public AudioClip getLoseSound() {
        return loseSound;
    }
}
