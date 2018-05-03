package com.JudgeGlass.ChatterFX.chatter.Utils;

import java.util.ArrayList;

public class ServerHolder {
    private String username;
    private String hostname;
    private int port;

    public ServerHolder(String username, String hostname, int port){
        this.username = username;
        this.hostname = hostname;
        this.port = port;
    }

    public String getUsername(){
        return username;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }
}
