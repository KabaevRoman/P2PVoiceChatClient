package com.company;

import javax.sound.sampled.*;

public class SpeakersWriter {
    private final AudioFormat audioFormat;
    private final SourceDataLine audioOutput;

    SpeakersWriter() throws LineUnavailableException {
        this.audioFormat = new AudioFormat(41000.0f, 16, 1, true, true);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        this.audioOutput = (SourceDataLine) AudioSystem.getLine(info);

    }

    public SourceDataLine start() throws LineUnavailableException {

        audioOutput.open(audioFormat);
        audioOutput.start();
        return audioOutput;
    }

    public void close() {
        audioOutput.close();
        audioOutput.drain();
    }

}
