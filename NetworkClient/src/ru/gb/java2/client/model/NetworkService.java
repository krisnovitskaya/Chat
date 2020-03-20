package ru.gb.java2.client.model;

import ru.gb.java2.client.controller.AuthEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

public class NetworkService {
    private final String serverIP;
    private final int port;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    private Consumer<String> messageHandler;
    private AuthEvent successfulAuthEvent;
    private String nick;


    public NetworkService(String serverIP, int port){
        this.serverIP = serverIP;
        this.port = port;
    }


    public void connect() throws IOException {
        socket = new Socket(serverIP, port);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        runReadingThread();
    }

    private void runReadingThread() {
        new Thread(() -> {
            while(true){
                try {
                    String message = in.readUTF();

                    if(message.startsWith("/auth")){

                        String[] messagePart = message.split(" ", 2); // может 3?
                        nick = messagePart[1];
                        //auth

                        successfulAuthEvent.authIsSuccessful(nick);  //запуск метода описанного в clientController

                    } else if(messageHandler != null){
                        messageHandler.accept(message);   //вызов описанного в clientController.openChat метода через функц интерфейс
                    }
                } catch (IOException e) {
                    System.out.println("Поток чтения был прерван!");
                    return;

                }
            }
        }).start();
    }

    public void setSuccessfulAuthEvent(AuthEvent successfulAuthEvent) {
        this.successfulAuthEvent = successfulAuthEvent;
    }

    public void sendAuthMessage(String login, String password) throws IOException{
        out.writeUTF(String.format("/auth %s %s", login, password));
    }

    public void sendMessage(String message) throws IOException{
        out.writeUTF(message);
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
