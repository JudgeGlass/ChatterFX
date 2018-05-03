package com.JudgeGlass.ChatterFX.chatter;

import com.JudgeGlass.ChatterFX.chatter.Chatting.Client;
import com.JudgeGlass.ChatterFX.chatter.UI.DetailedErrorAlert;
import com.JudgeGlass.ChatterFX.chatter.Utils.Closer;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main extends Application {
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("UI/MainWindow.fxml"));
        primaryStage.setTitle("ChatterFX");
        primaryStage.setScene(new Scene(root, 800, 480));
        primaryStage.show();
    }
    public static void setClose(Client c){
        if(c.isRunning()) {
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    try {
                        c.sendMessage("<-EXIT->", false);
                        c.close();
                    } catch (IOException e) {
                        DetailedErrorAlert.show("Error", "Error closing program", e);
                    }
                }
            });
        }
    }

    @Override
    public void stop(){
        if(Client.isRunning){
            Closer.closeSocket();
        }
    }


    public static void main(String[] args) {
        launch(args);

    }
}
