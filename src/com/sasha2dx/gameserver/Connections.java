package com.sasha2dx.gameserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connections {

    private BufferedInputStream inputStream;
    private BufferedOutputStream outputStream;
    private boolean isAlive;
    private long outputTimer = 0L, inputTimer = 0L;
    private static final int EMPTY_MESSAGE = 0;
    private Socket socket;


    public Connections(Socket socket) throws IOException {
        this.socket = socket;
        inputStream = new BufferedInputStream(socket.getInputStream());
        outputStream = new BufferedOutputStream(socket.getOutputStream());
        outputTimer = inputTimer = System.currentTimeMillis();
        isAlive = true;

    }

    public void write(int b){
        try {
            outputStream.write(b);
        } catch (IOException e) {
            close();
        }
    }

    public void write(byte[] b){
        try {
            outputStream.write(b);
        } catch (IOException e) {
            close();
        }
    }

    public void flush(){
        try {
            outputStream.flush();
        } catch (IOException e) {
            close();
        }
    }

    public boolean isAlive(){
        checkConnections();
        return isAlive;
    }

    public boolean available(){
        try {
            return inputStream.available() > 0;
        } catch (IOException e) {
            close();
            return false;
        }
    }

    public byte[] read(){
        try {
            if (inputStream.available()>0){
                inputTimer = System.currentTimeMillis();
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                return bytes;
            }
            return new byte[0];
        } catch (IOException e) {
            close();
            return new byte[0];
        }
    }

    private void checkConnections(){
        if (inputTimer < System.currentTimeMillis() - 15000) {
            isAlive = false;
        }
        if (outputTimer < System.currentTimeMillis() - 10000) {
            write(EMPTY_MESSAGE);
            flush();
            outputTimer = System.currentTimeMillis();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void close(){
        isAlive = false;
        System.out.println("Player disconected: " + socket.getInetAddress());
        try {inputStream.close();} catch (IOException e) {}
        try {outputStream.close();} catch (IOException e) {}
        try {socket.close();} catch (IOException e){}
    }
}
