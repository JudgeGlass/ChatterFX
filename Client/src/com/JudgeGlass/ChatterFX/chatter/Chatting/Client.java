package com.JudgeGlass.ChatterFX.chatter.Chatting;

import com.JudgeGlass.ChatterFX.chatter.UI.Controller;
import com.JudgeGlass.ChatterFX.chatter.UI.DetailedErrorAlert;
import com.JudgeGlass.ChatterFX.chatter.UI.IO;
import javafx.scene.image.Image;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.security.Security;

public class Client {
    private SSLSocket client;

    private DataOutputStream out;
    private DataInputStream in;

    private String username;
    private String ip;
    private int port;

    private Controller controller;

    public static boolean isRunning = false;

    public Client(Controller controller){
        this.controller = controller;
    }

    public void start(String password) throws IOException{
        if(!new File("content").exists()){
            new File("content").mkdir();
        }

        IO.extractInnerFiles("SERVER.jks");
        IO.extractInnerFiles("SERVER_TRUST.jts");
        System.setProperty("javax.net.ssl.trustStore", "content/SERVER_TRUST.jts");
        System.setProperty("javax.net.ssl.trustStorePassword", "PASSWORD");

       setStreams();

       //txtOutput.appendText("\nConnected!\n");
        controller.append("Connected!");
       new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   isRunning = true;
                   sendMessage("<-PASSWORD->" + password, false);
                   sendMessage("<-u->"+username, false);
                   chatting();
               }catch (IOException e){
                   e.printStackTrace();
               }

           }
       }).start();
    }

    private void setStreams() throws IOException{
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        SSLSocketFactory sslSocketFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
        client = (SSLSocket)sslSocketFactory.createSocket(InetAddress.getByName(ip), port);
        final String[] enabledCipherSuites = { "TLS_DHE_RSA_WITH_AES_128_CBC_SHA256" };
        client.setEnabledCipherSuites(enabledCipherSuites);

        in = new DataInputStream(client.getInputStream());
        out = new DataOutputStream(client.getOutputStream());
    }

    public void close() throws IOException{
        sendMessage("<-EXIT->", false);
        System.out.println("Closing streams....");
        if(out != null)out.close();
        if(in != null)in.close();
        if(client != null)client.close();
        isRunning = false;
        controller.append("Disconnected");
    }

    public void sendMessage(String text, boolean show) throws IOException{
        if(show)
            out.writeUTF("<" + username + "> " + text);
        else
            out.writeUTF(text);
        out.flush();

        if(show)
            writeText("<" + username + "> " + text);
    }

    public void sendFile(String filename) throws IOException{
        try {
            if(filename == null)return;
            sendMessage("<-FILE->", false);
            File file = new File(filename);
            byte[] ar = new byte[(int)file.length()];
            FileInputStream fin = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fin);
            bis.read(ar, 0, ar.length);

            out.writeUTF(filename);
            out.writeLong(ar.length);
            out.write(ar, 0, ar.length);
            out.flush();

            bis.close();
            fin.close();

            controller.appendImage(new Image(new File(filename).toURI().toString()));
        }catch (FileNotFoundException e){
            DetailedErrorAlert.show("Error", e.getMessage(), e);
        }
    }

    private void chatting(){
        try {
            while (isRunning()) {
                String message = in.readUTF();
                if (message.equals("Password is incorrect") || message.equals("Error: username taken!")) {
                    close();
                }
                if (addImage(message)) continue;
                System.out.println(message);
                writeText(message);
            }
        }catch (Exception e){
            try {
                close();
            }catch (IOException e1){
                e1.printStackTrace();
            }
        }
    }

    private void writeText(String message){
        controller.append(message);
    }

    private boolean addImage(String message){
        String image = "";
        File imageDir = null;
        if(message.contains("<-FILE->")){
            System.out.println("File found:");
            try {
                image = in.readUTF();
                System.out.println("FILE NAME: " + image);
                imageDir = new File("content/" + image);
                OutputStream out = new FileOutputStream(imageDir);
                long size = in.readLong();
                System.out.println("FILE SIZE: " + size);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while (size > 0 && (bytesRead = in.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                    out.write(buffer, 0, bytesRead);
                    size -= bytesRead;
                }

                out.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else
        {
             return false;
        }

        controller.appendImage(new Image(imageDir.toURI().toString()));

        return true;
    }

    public boolean isRunning(){
        return isRunning;
    }

    public void setIP(String ip){
        this.ip = ip;
    }

    public void setPort(int port){
        this.port = port;
    }

    public void setUsername(String username){
        this.username =  username;
    }

    public String getIP(){
        return ip;
    }

    public int getPort(){
        return port;
    }

    public String getUsername(){
        return username;
    }
}
