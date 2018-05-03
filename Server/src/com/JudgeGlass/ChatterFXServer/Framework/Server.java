

package com.JudgeGlass.ChatterFXServer.Framework;


import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Security;
import java.util.ArrayList;

public class Server {
    private SSLServerSocket server;
    private SSLSocket client;

    private ArrayList<Socket> clients = new ArrayList<>();

    private boolean isRunning = true;

    public void start(int port) throws IOException{
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        System.setProperty("javax.net.ssl.keyStore", "SERVER.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "PASSWORD_HERE");
        out("Starting server on " + port);

        SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        server = (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);
        final String[] enabled = {"TLS_DHE_RSA_WITH_AES_128_CBC_SHA256"};
        server.setEnabledCipherSuites(enabled);

        while(isRunning){
            out("Listening...");
            client = (SSLSocket) server.accept();
            out("Client connected from: " + client.getInetAddress());
            Client.clients.add(client);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        new ClientConnected().connected(client);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private void out(String text){
        System.out.println(text);
    }
}
