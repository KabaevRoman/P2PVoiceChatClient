package com.company;

import javax.sound.sampled.TargetDataLine;
import java.io.IOException;
import java.net.*;

public class RecorderThread extends Thread {
    private final TargetDataLine audioInput;
    private final DatagramSocket datagramSocket;
    private final byte[] buffer;
    private final InetAddress serverAddress;
    private final int serverPort;
    private boolean running;


    public RecorderThread(TargetDataLine audioInput) throws SocketException, UnknownHostException {
        this.audioInput = audioInput;
        this.datagramSocket = new DatagramSocket();
        this.serverAddress = InetAddress.getByName("localhost");
        this.serverPort = 7777;
        this.buffer = new byte[512];
        this.running = true;
    }

    public RecorderThread(TargetDataLine audioInput, int port, String address) throws SocketException, UnknownHostException {
        this.audioInput = audioInput;
        this.datagramSocket = new DatagramSocket();
        this.serverAddress = InetAddress.getByName(address);
        this.serverPort = port;
        this.buffer = new byte[512];
        this.running = true;
    }


    @Override
    public void run() {
        //int counter = 0;
        while (running) {
            audioInput.read(buffer, 0, buffer.length);
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
            //System.out.println("send #" + counter);
            //counter++;
            try {
                datagramSocket.send(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        audioInput.close();
        audioInput.drain();
        System.out.println("Recorder thread ended successfully");
    }

    public void close() {
        this.running = false;
    }
}
