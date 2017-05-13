package com.sasha2dx.gameserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private int port = 9995;
    private String host = "";
    private ServerSocket serverSocket;
    private boolean isRun = true;

    public Server() {
        initServerSocket();
        startServerMainLoop();
        startServerListener();
    }

    private void mainLoop() {
    }

    private void newConnections(Connections connections) {
        //todo
    }

    private void startServerListener() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRun) {
                    try {
                        Socket socket = serverSocket.accept();
                        System.out.println("New player has connected: " + socket.getInetAddress().toString());
                        newConnections(new Connections(socket));
                    } catch (IOException e) {
                        if (isRun) System.out.println("Error in server listener: " + e.toString());
                    }
                }
            }
        }).start();
    }
    private void startServerMainLoop() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRun) {
                    try {
                        mainLoop();
                        Thread.sleep(50);
                    } catch (Exception e) {
                        System.err.println("UNHANDLED EXCEPTION IN MAIN LOOP: " + e.toString());
                    }
                }
            }
        }).start();
    }
    private void initServerSocket() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Erron bing to port.");
            isRun = false;
        }
    }

    public boolean isRun() {
        return isRun;
    }

    public void stop() {
        isRun = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
        }
        System.out.println();

    }
}
