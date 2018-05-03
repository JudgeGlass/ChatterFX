package com.JudgeGlass.ChatterFXServer.Framework;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Client {
    public static ArrayList<SSLSocket> clients = new ArrayList<>();
    public static ArrayList<DataInputStream> inputs = new ArrayList<>();
    public static ArrayList<DataOutputStream> outputs = new ArrayList<>();
    public static HashMap<SSLSocket, String> usernameS = new HashMap<>();

    private static String password;

    public static void setPassword(String password){
        Client.password = password;
    }

    public static String getPassword(){
        return password;
    }

    public static void addOutput(DataOutputStream o){
        outputs.add(o);
    }

    public static void addInput(DataInputStream i){
        inputs.add(i);
    }

    public static ArrayList<SSLSocket> getClients() {
        return clients;
    }

    public static ArrayList<DataOutputStream> getOutputs() {
        return outputs;
    }

    public static ArrayList<DataInputStream> getInputs() {
        return inputs;
    }

    public static void addUsernameS(String username, SSLSocket s){
        usernameS.put(s, username);
    }

    public static String getUsernameFromSocket(SSLSocket s){
        return usernameS.get(s);
    }
}
