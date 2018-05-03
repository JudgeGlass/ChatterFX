package com.JudgeGlass.ChatterFX.chatter.UI;

import javafx.scene.control.Alert;

public class InfoAlert {
    public static void show(String title, String message){
        Alert alt = new Alert(Alert.AlertType.INFORMATION);
        alt.setTitle(title);
        alt.setContentText(message);
        alt.setHeaderText(null);
        alt.showAndWait();
    }
}
