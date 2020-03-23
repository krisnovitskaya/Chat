package ru.gb.java2.client.model;

import ru.gb.java2.client.controller.AuthEvent;
import ru.gb.java2.client.controller.ClientController;
import ru.gb.java2.clientserver.Command;
import ru.gb.java2.clientserver.command.AuthCommand;
import ru.gb.java2.clientserver.command.ErrorCommand;
import ru.gb.java2.clientserver.command.MessageCommand;
import ru.gb.java2.clientserver.command.UpdateUsersListCommand;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.function.Consumer;

public class NetworkService {
    private final String serverIP;
    private final int port;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private ClientController controller;

    private Consumer<String> messageHandler;
    private AuthEvent successfulAuthEvent;
    private String nick;


    public NetworkService(String serverIP, int port){
        this.serverIP = serverIP;
        this.port = port;
    }


    public void connect(ClientController controller) throws IOException {
        this.controller =controller;
        socket = new Socket(serverIP, port);
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        runReadingThread();
    }

    private void runReadingThread() {
        new Thread(() -> {
            while(true){
                try {
                    Command command = (Command) in.readObject();
                    switch (command.getType()){
                        case AUTH:{
                            AuthCommand commandData = (AuthCommand) command.getData();
                            nick = commandData.getUsername();
                            successfulAuthEvent.authIsSuccessful(nick);
                            break;
                        }
                        case MESSAGE:{
                            MessageCommand commandData = (MessageCommand) command.getData();
                            if(messageHandler != null){
                                String message = commandData.getMessage();
                                String username = commandData.getUsername();
                                if(username != null){
                                    message = username + ": " + message;
                                }
                                messageHandler.accept(message);
                            }
                            break;
                        }
                        case AUTH_ERROR:
                        case ERROR:{
                            ErrorCommand commandData = (ErrorCommand) command.getData();
                            controller.showErrorMessage(commandData.getErrorMessage());
                            break;
                        }
                        case UPDATE_USER_LIST:{
                            UpdateUsersListCommand commandData = (UpdateUsersListCommand) command.getData();
                            List<String> users = commandData.getUsers();
                            controller.updateUsersList(users);
                            break;
                        }
                        default:
                            System.err.println("unknown type of command: " + command.getType());
                    }
                } catch (IOException e) {
                    System.out.println("Поток чтения был прерван!");
                    return;
                } catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void setSuccessfulAuthEvent(AuthEvent successfulAuthEvent) {
        this.successfulAuthEvent = successfulAuthEvent;
    }

//    public void sendAuthMessage(String login, String password) throws IOException{
//        out.writeUTF(String.format("/auth %s %s", login, password));
//    }

    public void sendCommand(Command command) throws IOException{
        out.writeObject(command);
    }

    public void setMessageHandler(Consumer<String> messageHandler){
        this.messageHandler = messageHandler;
    }

    public void close(){
        try{
            socket.close();
        }catch (IOException e){
            e. printStackTrace();
        }
    }
}
