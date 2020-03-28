package ru.gb.java3.client.controller;

import ru.gb.java3.client.model.NetworkService;
import ru.gb.java3.client.window.AuthDialog;
import ru.gb.java3.client.window.ClientChat;
import ru.gb.java3.clientserver.Command;

import java.io.IOException;
import java.util.List;

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
        networkService.setSuccessfulAuthEvent(new AuthEvent() {
            @Override
            public void authIsSuccessful(String nick) {
                ClientController.this.setUserName(nick);
                clientChat.setTitle(nick);
                ClientController.this.openChat();
            }
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
        networkService.connect(this);
    }



    public void sendAuthMessage(String login, String pass) throws IOException {
        networkService.sendCommand(Command.authCommand(login, pass));
    }

    public void sendMessageToAll(String message){
        try {
            networkService.sendCommand(Command.broadcastMessage(message));
        } catch (IOException e) {
            clientChat.showError("Ошибка отправки сообщения");
            e.printStackTrace();
        }
    }

    public void sendPrivateMessage(String username, String message) {
        try {
            networkService.sendCommand(Command.privateMessageCommand(username, message));
        } catch (IOException e) {
            showErrorMessage(e.getMessage());
        }
    }
    public  void shutdown(){
        networkService.close();
    }


    public void showErrorMessage(String errorMessage) {
        if (clientChat.isActive()){
            clientChat.showError(errorMessage);
        } else if(authDialog.isActive()){
            authDialog.showError(errorMessage);
        }
        System.err.println(errorMessage);
    }

    public void updateUsersList(List<String> users) {
        users.remove(nick);
        users.add(0, "to All");
        clientChat.updateUsers(users);
    }
}
