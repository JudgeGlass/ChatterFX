package com.JudgeGlass.ChatterFX.chatter.Utils;

import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerData {
    private static ArrayList<ServerHolder> servers = new ArrayList<>();

    public static HashMap<String, ServerHolder> serversS = new HashMap<>();

    public static void addServer(ServerHolder h){
        serversS.put(h.getHostname(), h);
        servers.add(h);
    }

    public static ArrayList<ServerHolder> getServers(){
        return servers;
    }

    public static ServerHolder getServerByIndex(int index){
        return servers.get(index);
    }

    public static void removeServer(ServerHolder h){
        servers.remove(h);
    }

    public static void removeServerByIndex(int index){
        servers.remove(index);
    }

    public static void updateList(ListView list){
        for(int i = 0; i<list.getItems().size();i++){
            list.getItems().remove(i);
        }

        for(int i = 0; i<servers.size(); i++){
            if(list.getItems().contains(servers.get(i).getHostname()))continue;
            list.getItems().add(servers.get(i).getHostname());
        }
        SaveData.update();
    }
}
