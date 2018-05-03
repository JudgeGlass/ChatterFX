package com.JudgeGlass.ChatterFX.chatter.UI;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class InputDialog {
    public static String show(String title, String header, String question){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(question);

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            return result.get();
        }else {
            return "ERROR";
        }

    }
}
