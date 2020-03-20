package ru.gb.java2.server.client;

import ru.gb.java2.server.NetworkServer;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    private final NetworkServer networkServer;
    private final Socket clientSocket;

    private DataInputStream in;
    private DataOutputStream out;


    private String nick;

    public ClientHandler(NetworkServer networkServer, Socket clientSocket) {
        this.networkServer = networkServer;
        this.clientSocket = clientSocket;
    }


    public void go(){
        doHandle(clientSocket);
    }

    private void doHandle(Socket clientSocket) {
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

            //запуск авторизации с последующим чтением ввода в отдельном потоке
            new Thread(() -> {

                try {
                    authentication();
                    readingMessages();
                } catch (IOException e) {
                    System.out.println("Соединение с клиентом " + nick + " завершено.");
                } finally {
                    closeConnection();
                }

            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {

        try{
            networkServer.unsubscribe(this); //отписаться
            networkServer.broadcastMessage(nick + " вышел из чата.");
            clientSocket.close(); //закрыть сокет и ридеры ввода/вывода
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void readingMessages() throws IOException {
        while (true){
            String message = in.readUTF();
            if("/end".equals(message)){  //команда выхода от пользователя
                return;
            }
            if(message.startsWith("/w")){
                sendPrivateMessage(message);
            } else {
                networkServer.broadcastMessage(nick + " : " + message);
           }
        }
    }

    private void sendPrivateMessage(String message) {
        try {
            networkServer.sendPrivateMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void authentication() throws IOException {
        while(true) {
            String message = in.readUTF();
            String[] messageParts = message.split(" ", 3);

            String login = messageParts[1];
            String password = messageParts[2];
            String userName = networkServer.getAuthService().getUserNameByLoginAndPass(login, password);

            if (userName == null) {
                System.out.println("Отсутствует учетная запись с таким логином и паролем");
                //sendMessage("Отсутствует учетная запись с таким логином и паролем");

            } else {
                nick = userName;
                sendMessage("/auth "+ nick);
                networkServer.broadcastMessage(nick + " зашел в чат.");
                networkServer.subscribe(this);
                break;
            }
        }
    }

    public void sendMessage(String message) throws IOException {
        out.writeUTF(message);
    }

    public String getUserName() {
        return nick;
    }
}
