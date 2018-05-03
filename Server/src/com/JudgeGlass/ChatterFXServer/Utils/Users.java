package com.JudgeGlass.ChatterFXServer.Utils;

public class Users {
    public static boolean isUsername(String text){
        if(text.contains("<-u->"))return true;
        return false;
    }

    public static boolean isFile(String text){
        if(text.contains("<-FILE->"))return true;
        return false;
    }

    public static String getPassword(String text){
        return text.substring(text.lastIndexOf("<-PASSWORD->") + 12, text.length());
    }

    public static String getUsername(String text){
        String username = text.substring(text.lastIndexOf("<-u->") + 5, text.length());
        return username;
    }
}
