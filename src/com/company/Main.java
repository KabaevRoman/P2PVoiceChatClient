package com.company;

import javax.sound.sampled.LineUnavailableException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws LineUnavailableException, SocketException, UnknownHostException {

        Thread receiver = new Thread(() -> {
            SpeakersWriter speakers = null;
            try {
                speakers = new SpeakersWriter();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            PlayerThread playerThread = null;
            try {
                playerThread = new PlayerThread(speakers.start());
            } catch (SocketException | LineUnavailableException e) {
                e.printStackTrace();
            }
            playerThread.start();
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
            playerThread.close();
            speakers.close();
        });


        Thread sender = new Thread(() -> {
            MicrophoneReader microphoneReader = null;
            try {
                microphoneReader = new MicrophoneReader();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            RecorderThread recorderThread = null;
            try {
                recorderThread = new RecorderThread(microphoneReader.start());
            } catch (SocketException | LineUnavailableException | UnknownHostException e) {
                e.printStackTrace();
            }
            recorderThread.start();
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
            recorderThread.close();
            microphoneReader.close();
        });
        receiver.start();
        sender.start();


    }
}
