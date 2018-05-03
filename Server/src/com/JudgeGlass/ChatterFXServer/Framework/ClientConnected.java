

package com.JudgeGlass.ChatterFXServer.Framework;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import com.JudgeGlass.ChatterFXServer.Utils.Users;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.net.ssl.SSLSocket;

public class ClientConnected {

    private DataInputStream in;
    private DataOutputStream out;

    private SSLSocket client;
    private boolean isRunning = true;
    private boolean isValidated = false;

    public void connected(SSLSocket client) throws IOException{
        this.client = client;
        while(isRunning){
            try {
                setreamSetup();
                chatting();
            }catch (EOFException e){
                close();
                System.out.println("CONNECTING ENDED");
                return;
            }
        }
    }

    private void setreamSetup() throws IOException{
        in = new DataInputStream(client.getInputStream());
        Client.addInput(in);

        out = new DataOutputStream(client.getOutputStream());
        Client.addOutput(out);
    }

    private void close() throws IOException{
        Client.usernameS.remove(client, Client.usernameS.get(client));
        Client.inputs.remove(in);
        Client.outputs.remove(out);
        Client.clients.remove(client);
        isRunning = false;
    }

    private void chatting() throws IOException{
        while(isRunning) {
            try {
                String message = in.readUTF();
                checkMessage(message);
            }catch (Exception e){
                e.printStackTrace();
                close();
                return;
            }
        }
    }

    private void checkMessage(String text) throws IOException{
        if(text.equals("<-EXIT->")){
            sendMessage(Client.getUsernameFromSocket(client) + " has left");
            close();
            return;
        }

        if(text.contains("<-PASSWORD->")){
            if(Users.getPassword(text).equals(Client.getPassword())){
                isValidated = true;
                return;
            }else isValidated = false;
        }

        if(!isValidated){
            sendPrivateMessage("Password is incorrect!");
            close();
            return;
        }

        if(Users.isUsername(text)){
            if(Client.usernameS.containsValue(Users.getUsername(text))){
                sendPrivateMessage("Error: username taken!");
                close();
            }
            sendMessage(Users.getUsername(text) + " has joined the server!");
            Client.addUsernameS(Users.getUsername(text), client);
            return;
        }

        if(Users.isFile(text)){
            String filename = in.readUTF();
            if(filename.contains("/")) filename = filename.substring(filename.lastIndexOf("/") + 1);
            else filename = filename.substring(filename.lastIndexOf("\\") + 1);
            OutputStream out = new FileOutputStream(filename);
            long size = in.readLong();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while (size > 0 && (bytesRead = in.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1)
            {
                out.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }
            out.close();
            sendImage(filename);
            return;
        }

        System.out.println("Message: " + text);
        sendMessage(text);
    }

    private void sendImage(String filename) throws IOException{
        sendMessage(Client.getUsernameFromSocket(client) + " has sent an image!");
        sendMessage("<-FILE->");
        File file = new File(filename);
        byte[] ar = new byte[(int)file.length()];
        FileInputStream fin = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fin);
        bis.read(ar, 0, ar.length);

        System.out.println("FILE NAME: " + filename);
        for(DataOutputStream i: Client.getOutputs()){
            if(i.equals(out))continue;
            i.writeUTF(filename);
            i.writeLong(ar.length);
            i.write(ar, 0, ar.length);
            i.flush();
        }


        bis.close();
        fin.close();
    }

    private void sendPrivateMessage(String message) throws IOException{
        out.writeUTF(message);
    }

    private void sendMessage(String message) throws IOException{
        for(DataOutputStream i: Client.getOutputs()){
            if(i.equals(out))continue;
            i.writeUTF(message);
        }
    }
}
