package com.company;

import javax.sound.sampled.*;

public class MicrophoneReader {
    private final AudioFormat audioFormat;
    private final TargetDataLine audioInput;

    public MicrophoneReader() throws LineUnavailableException {
        this.audioFormat = new AudioFormat(41000.0f, 16, 1, true, true);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
        this.audioInput = (TargetDataLine) AudioSystem.getLine(info);
    }

    public TargetDataLine start() throws LineUnavailableException {
        audioInput.open(audioFormat);
        audioInput.start();
        return audioInput;
    }

    public void close() {
        audioInput.close();
        audioInput.drain();
    }
}
