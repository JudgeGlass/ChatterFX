package com.JudgeGlass.ChatterFX.chatter.UI;

import com.JudgeGlass.ChatterFX.chatter.Chatting.Client;
import com.JudgeGlass.ChatterFX.chatter.Main;
import com.JudgeGlass.ChatterFX.chatter.Utils.Closer;
import com.JudgeGlass.ChatterFX.chatter.Utils.SaveData;
import com.JudgeGlass.ChatterFX.chatter.Utils.ServerData;
import com.JudgeGlass.ChatterFX.chatter.Utils.ServerHolder;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javafx.scene.image.ImageView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class Controller {
    @FXML private TextFlow textLayout;
    @FXML private TextField txtMessage;
    @FXML private ListView serverList;
    @FXML private ScrollPane scrollPane;

    private Client client;
    private Text text;

    @FXML
    private void initialize(){
        client = new Client(this);
        Closer.setClient(client);
        Main.setClose(client);

        if(new File("data.dat").exists()){
            SaveData.getSaveData();
            ServerData.updateList(serverList);
        }

        final ContextMenu contextMenu = new ContextMenu();
        MenuItem cut = new MenuItem("Delete");
        contextMenu.getItems().addAll(cut);
        cut.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                if(serverList.getSelectionModel().getSelectedItem() != null) {
                    String item = serverList.getSelectionModel().getSelectedItem().toString();
                    serverList.getItems().remove(serverList.getSelectionModel().getSelectedIndex());

                    ServerData.removeServer(ServerData.serversS.get(item));
                    ServerData.serversS.remove(item);
                    ServerData.updateList(serverList);
                }
            }
        });
        serverList.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isSecondaryButtonDown()){
                    contextMenu.show(serverList, event.getScreenX(), event.getScreenY());
                }
            }
        });
    }

    @FXML
    private void start(){

    }

    @FXML
    private void showAboutDialog(){
        AboutDialog.show();
    }

    @FXML
    private void addServer(){
        AddServer.add(serverList);
    }

    @FXML
    private void connect(){
        if(client.isRunning()){
            InfoAlert.show("Already running", "A connection is already in progress.");
            return;
        }
        if(serverList.getSelectionModel().getSelectedItem() == null){
            Alert alt = new Alert(Alert.AlertType.ERROR);
            alt.setTitle("Error");
            alt.setHeaderText(null);
            alt.setContentText("No server selected");
            alt.showAndWait();
            return;
        }

        try{
            ServerHolder server = ServerData.serversS.get(serverList.getSelectionModel().getSelectedItem().toString());

            client.setUsername(server.getUsername());
            client.setIP(server.getHostname());
            client.setPort(server.getPort());

            ConnectDialog.show(client);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void upload(){
        try {
            if(client.isRunning())
                client.sendFile(IO.openFileDialog(null));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void txtSend(){
        send();
    }

    @FXML
    private void btnSend(){
        send();
    }

    private void send(){
        if(client.isRunning()){
            try {
                if(!txtMessage.getText().equals("")) {
                    if(txtMessage.getText().length() >= 1000){
                        DetailedErrorAlert.show("Error", "Too many characters", new RuntimeException());
                        return;
                    }
                    client.sendMessage(txtMessage.getText(), true);
                    txtMessage.setText("");
                }
            }catch (Exception e){
                text = new Text("\nError Sending message");
                textLayout.getChildren().add(text);
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void exitApplication(ActionEvent e){

    }

    public void append(String message){
        Platform.runLater(() -> textLayout.getChildren().add(new Text("\n" + message)));
        Platform.runLater(() -> scrollPane.setVvalue(textLayout.getChildren().size()));
    }

    public void appendImage(Image image){
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        Platform.runLater(() -> textLayout.getChildren().addAll(new Text("\n"),imageView));
        Platform.runLater(() -> scrollPane.setVvalue(textLayout.getChildren().size()));
    }
}
