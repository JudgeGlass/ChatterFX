

package com.JudgeGlass.ChatterFXServer;

import com.JudgeGlass.ChatterFXServer.Framework.Client;
import com.JudgeGlass.ChatterFXServer.Framework.Server;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final int port = 888;

    public static void main(String args[]){
        Scanner s = new Scanner(System.in);
        System.out.print("Password: ");
        Client.setPassword(s.nextLine());
        try {
            new Server().start(port);
        }catch (IOException e){
            System.err.print("Server failed to start\n" + e.getMessage());
        }
    }
}
