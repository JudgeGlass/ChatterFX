package com.JudgeGlass.ChatterFX.chatter.UI;

import com.JudgeGlass.ChatterFX.chatter.Utils.ServerData;
import com.JudgeGlass.ChatterFX.chatter.Utils.ServerHolder;

import javafx.scene.control.ListView;

public class AddServer {
    public static void add(ListView list){
        try {
            String username = InputDialog.show("Add Server", "Username", "Enter a username:");
            System.out.print(username);
            if(username.equals("ERROR"))return;

            String ip = InputDialog.show("Add Server", "IP/Hostname", "Enter a valid hostname:");
            if(ip.equals("ERROR"))return;

            int port = Integer.parseInt(InputDialog.show("Add Server", "Server port(eg. 888)", "Enter a valid port:"));
            if(port == 0)return;

            ServerData.addServer(new ServerHolder(username, ip, port));
            ServerData.updateList(list);
        }catch (Exception e){
            DetailedErrorAlert.show("Server adding error", "Could not add server", e);
        }
    }
}
