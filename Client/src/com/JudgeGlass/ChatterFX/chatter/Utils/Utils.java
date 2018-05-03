package com.JudgeGlass.ChatterFX.chatter.Utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {
    public static String indexOf(String txt, char ch) {
        return txt.substring(txt.lastIndexOf(ch) + 1);
    }

    public static String readLine(String fileName, int lineNumber) {
        String line;
        try {
            line = Files.readAllLines(Paths.get(fileName)).get(lineNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return line;
    }

    public static void writeFile(String fileName, String txt) {
        try {
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            writer.print(txt);
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
