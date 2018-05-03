package com.JudgeGlass.ChatterFX.chatter.UI;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class IO {
    public static String openFileDialog(String title){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File f = fileChooser.showOpenDialog(null);
        return f.toString();
    }

    public static void extractInnerFiles(String fileName){
        InputStream ddlStream = IO.class.getClassLoader().getResourceAsStream("com/JudgeGlass/ChatterFX/chatter/Misc/external_files/" + fileName);
        try(FileOutputStream fos = new FileOutputStream("content/" + fileName);){
            byte[] buf = new byte[2048];
            int r;
            while(-1 != (r = ddlStream.read(buf))) {
                fos.write(buf, 0, r);
            }
        }catch (Exception e){
            System.out.println("Could not extract files.");
            e.printStackTrace();
        }
    }
}
