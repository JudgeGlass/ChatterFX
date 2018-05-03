package com.JudgeGlass.ChatterFX.chatter.UI;

import com.JudgeGlass.ChatterFX.chatter.Chatting.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectDialog {
    private static Stage stage;
    private static Client client;

    public static Stage getStage(){
        return stage;
    }

    public static Client getClient(){
        return client;
    }

    public static void show(Client client) throws IOException{
        ConnectDialog.client = client;
        Parent root = FXMLLoader.load(ConnectDialog.class.getResource("ConnectDialog.fxml"));
        stage = new Stage();
        stage.setTitle("ChatterFX");
        stage.setScene(new Scene(root, 220, 280));
        stage.setResizable(false);
        stage.show();
    }
}
