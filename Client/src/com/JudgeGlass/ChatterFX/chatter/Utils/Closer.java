package com.JudgeGlass.ChatterFX.chatter.Utils;

import com.JudgeGlass.ChatterFX.chatter.Chatting.Client;

import java.io.IOException;

public class Closer {
    private static Client c;

    public static void setClient(Client cl){
        c = cl;
    }

    public static void closeSocket(){
        try {
            c.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
