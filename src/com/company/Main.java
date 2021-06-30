package com.company;

import javax.sound.sampled.LineUnavailableException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws LineUnavailableException, SocketException, UnknownHostException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Port for listener");
        int receiverPort = sc.nextInt();
        System.out.println("Port for sender");
        int senderPort = sc.nextInt();
        System.out.println("Address for sender");
        String address = sc.next();

        SpeakersWriter speakers = new SpeakersWriter();
        MicrophoneReader microphone = new MicrophoneReader();
        PlayerThread playerThread = new PlayerThread(speakers.start(), receiverPort);
        RecorderThread recorderThread = new RecorderThread(microphone.start(), senderPort, address);

        playerThread.start();
        recorderThread.start();

        System.out.println("Для завершения введите любой символ");
        sc.next();
        playerThread.close();
        recorderThread.close();
        microphone.close();
        speakers.close();
    }
}
