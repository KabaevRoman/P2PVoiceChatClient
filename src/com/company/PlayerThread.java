package com.company;

import javax.sound.sampled.SourceDataLine;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class PlayerThread extends Thread {
    private final DatagramSocket datagramSocketIn;
    private final SourceDataLine audioOutput;
    private byte[] buffer;
    private boolean running;

    public PlayerThread(SourceDataLine audioOutput) throws SocketException {
        this.datagramSocketIn = new DatagramSocket(7777);
        this.audioOutput = audioOutput;
        this.buffer = new byte[512];
        this.running = true;
    }

    public void run() {
        //int count = 0;
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
        while (running) {
            try {
                datagramSocketIn.receive(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            buffer = datagramPacket.getData();
            audioOutput.write(buffer, 0, buffer.length);
            //System.out.println("#"+count++); for diagnostics
        }
        audioOutput.close();
        audioOutput.drain();
        System.out.println("Thread ended successfully");
    }

    public void close() {
        this.running = false;
    }

}
