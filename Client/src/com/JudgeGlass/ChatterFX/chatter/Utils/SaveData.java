package com.JudgeGlass.ChatterFX.chatter.Utils;

import java.io.File;
import java.util.ArrayList;

public class SaveData {
    private static String conf = "";

    public static void update(){
        if(new File("data.dat").exists())
            new File("data.dat").delete();
        conf = "";
        save();
    }

    public static void getSaveData(){
        int counter = 0;
        while(!Utils.readLine("data.dat", counter).equals("##END##")){
            String line = Utils.indexOf(Utils.readLine("data.dat", counter), '=');
            String[] args = line.split(",");

            ServerData.addServer(new ServerHolder(args[2], args[0], Integer.parseInt(args[1])));
            counter++;
        }
    }

    private static void save(){
       for(ServerHolder i: ServerData.getServers()){
           conf += "server=" + i.getHostname() + "," + i.getPort() + "," + i.getUsername() + "\n";
       }
       conf += "##END##";

       Utils.writeFile("data.dat", conf);
    }
}
