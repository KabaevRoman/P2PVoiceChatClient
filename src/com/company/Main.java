package com.company;

import javax.sound.sampled.LineUnavailableException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws LineUnavailableException, SocketException, UnknownHostException {

        SpeakersWriter speakers = new SpeakersWriter();
        PlayerThread playerThread = new PlayerThread(speakers.start());
        MicrophoneReader microphoneReader = new MicrophoneReader();
        RecorderThread recorderThread = new RecorderThread(microphoneReader.start());

        playerThread.start();
        recorderThread.start();

        Scanner sc = new Scanner(System.in);
        sc.nextLine();

        recorderThread.close();
        microphoneReader.close();
        playerThread.close();
        speakers.close();
    }
}
