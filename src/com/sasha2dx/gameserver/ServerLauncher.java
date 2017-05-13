package com.sasha2dx.gameserver;

import java.util.Scanner;

public class ServerLauncher {

    public static void main(String[] args) {
        System.out.println("Server started!");
        Server server = new Server();
        Scanner scanner = new Scanner(System.in);
        while (server.isRun() && !scanner.nextLine().equals("stop"));
        server.stop();
        System.out.println("Server closed.");
        System.out.println();
    }
}
