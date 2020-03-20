package ru.gb.java2.client.controller;

import ru.gb.java2.client.model.NetworkService;
import ru.gb.java2.client.window.AuthDialog;
import ru.gb.java2.client.window.ClientChat;

import javax.swing.*;
import java.io.IOException;

public class ClientController {

    private final NetworkService networkService;
    private final AuthDialog authDialog;
    private final ClientChat clientChat;
    private String nick;

    public ClientController(String serverIP, int serverPort){
        this.networkService = new NetworkService(serverIP, serverPort);
        this.authDialog = new AuthDialog(this);
        this.clientChat = new ClientChat(this);
    }



    public void goApp() throws IOException {
        connectToServer();
        runAuthProcess();
    }

    private void runAuthProcess() {

        networkService.setSuccessfulAuthEvent(nick -> {
            setUserName(nick);
            openChat();
        });
        authDialog.setVisible(true);
    }

    private void openChat() {
        authDialog.dispose();
        networkService.setMessageHandler(clientChat::addMessage);
        clientChat.setVisible(true);
    }



    private void setUserName(String nick) {
        this.nick = nick;
    }
    public String getUserName(){
        return nick;
    }

    private void connectToServer() throws IOException {
        networkService.connect();
    }

    public void sendAuthMessage(String login, String pass) throws IOException {
        networkService.sendAuthMessage(login, pass);
    }
    public void sendMessage(String message){
        try {
            networkService.sendMessage(message);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Ошибка отправки сообщения");
            e.printStackTrace();
        }
    }

    public  void shutdown(){
        networkService.close();
    }
}
