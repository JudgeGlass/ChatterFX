package com.JudgeGlass.ChatterFX.chatter.UI;

import com.JudgeGlass.ChatterFX.chatter.Chatting.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import javafx.scene.control.*;

public class ConnectUIController {
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private TextField ip;
    @FXML private TextField port;

    private Client client;

    @FXML
    public void initialize(){
        client = ConnectDialog.getClient();
        username.setText(client.getUsername());
        ip.setText(client.getIP());
        port.setText(Integer.toString(client.getPort()));

        password.setFocusTraversable(true);
    }

    @FXML
    private void connect(){
        try {
            String uname = username.getText();
            String pass = password.getText();
            String host = ip.getText();
            int port_ = Integer.parseInt(port.getText());

            client.setIP(host);
            client.setPort(port_);
            client.setUsername(uname);

            client.start(pass);

            ConnectDialog.getStage().close();
        }catch (Exception e){
            DetailedErrorAlert.show("Error", e.getMessage(), e);
        }
    }

    @FXML
    private void cancel(){
        ConnectDialog.getStage().close();
    }
}
