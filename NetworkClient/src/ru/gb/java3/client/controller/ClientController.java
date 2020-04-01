package ru.gb.java3.client.controller;

import ru.gb.java3.client.model.NetworkService;
import ru.gb.java3.client.window.AuthDialog;
import ru.gb.java3.client.window.ChangeNickDialog;
import ru.gb.java3.client.window.ClientChat;
import ru.gb.java3.clientserver.Command;

import javax.swing.*;
import java.io.*;
import java.util.List;

public class ClientController {

    private final NetworkService networkService;
    private final AuthDialog authDialog;
    private final ClientChat clientChat;
    private final ChangeNickDialog changeNickDialog;
    private String nick;

    private int id;

    public ClientController(String serverIP, int serverPort){
        this.networkService = new NetworkService(serverIP, serverPort);
        this.authDialog = new AuthDialog(this);
        this.clientChat = new ClientChat(this);
        this.changeNickDialog = new ChangeNickDialog(this);
    }



    public void goApp() throws IOException {
        connectToServer();
        runAuthProcess();
    }

    private void runAuthProcess() {
        networkService.setSuccessfulAuthEvent(new AuthEvent() {
            @Override
            public void authIsSuccessful(String nick, int id) {
                ClientController.this.setUserName(nick);
                ClientController.this.setID(id);
                clientChat.setTitle(nick);
                clientChat.setChatListField(loadChatHistoryByID());
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

    public void openChangeNickDialog(){
        changeNickDialog.setVisible(true);
    }
    public void changeNickDialogClose() {
        changeNickDialog.setVisible(false);
    }

    public void setNewNick(String newNick){
        setUserName(newNick);
        clientChat.setTitle(nick);
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

    public void sendChangeNickMessage(String login, String pass, String newNick) throws IOException {
        networkService.sendCommand(Command.changeNickCommand(login, pass, newNick));
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
        } else if(changeNickDialog.isActive()){
            changeNickDialog.showError(errorMessage);
        }
        System.err.println(errorMessage);
    }

    public void updateUsersList(List<String> users) {
        users.remove(nick);
        users.add(0, "to All");
        clientChat.updateUsers(users);
    }


    public int getId() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public DefaultListModel<String> loadChatHistoryByID() {
        DefaultListModel<String> historyLast100 = new DefaultListModel<>();
        int numberLastLines = 100;


        try {
            BufferedReader reader = new BufferedReader(new FileReader(getLogFile()));

            String[] arr = new String[numberLastLines];
            //чтение в массив
            int lastIndex = 0;
            String string = reader.readLine();
            while (string != null){
                if(lastIndex == arr.length){
                    lastIndex = 0;
                }
                arr[lastIndex] = string;
                lastIndex++;
                string = reader.readLine();
            }

            //чтение из массива
            int i = lastIndex;
            do {
                if(i == arr.length){
                    i = 0;
                }
                if(arr[i] != null){
                    historyLast100.addElement((arr[i]));
                }
                i++;
            } while(i != lastIndex);


        reader.close();

        }catch (FileNotFoundException e){
            System.err.println("Файл не найден");
        }catch (IOException e){
            e.printStackTrace();
        }


        return historyLast100;
    }


    public void saveHistory(List<String> history) {
        try {
            FileWriter fr = new FileWriter(getLogFile(), true);
            BufferedWriter writer = new BufferedWriter(fr);
            for (String s : history) {
                writer.write(s + "\n");
            }
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getLogFile(){
        String fileLogName = String.format("NetworkClient/logs/history_id%d.txt", getId());
        File log = new File(fileLogName);
        if(!log.exists()){
            try {
                File dir = new File("NetworkClient/logs");
                dir.mkdirs();
                log.createNewFile();
            } catch (IOException e) {
                System.err.println("Ошибка при создании файла.");;
            }
        }
        return log;
    }
}

