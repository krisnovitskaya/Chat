package ru.gb.java3.server;

import ru.gb.java3.clientserver.Command;
import ru.gb.java3.server.auth.AuthService;
import ru.gb.java3.server.auth.BaseAuthService;
import ru.gb.java3.server.client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NetworkServer {

    private final int port;
    private final List<ClientHandler> clients = new ArrayList<>();
    private final AuthService authService;

    //конструктор
    public NetworkServer(int port) {
        this.port = port;
        this.authService = new BaseAuthService();
    }

    //запуск
    public void go() {
        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Сервер был успешно создан. Порт: " + port);
            authService.start();
            while(true){
                System.out.println("Ожидание клиентского подключения...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Клиент подключился...");
                createClientHandler(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при работе сервера.");
            e.printStackTrace();
        } finally {
            authService.stop();
        }
    }

    //создание обработчика клиентского подключения
    private void createClientHandler(Socket clientSocket) {
        ClientHandler clientHandler = new ClientHandler(this, clientSocket);
        clientHandler.go();
    }

    public AuthService getAuthService() {
        return authService;
    }

    //разослать сообщение всем клиентам
    public synchronized void broadcastMessage(Command message, ClientHandler owner) throws IOException{
        for(ClientHandler client : clients ){
            if(client != owner) {
                client.sendMessage(message);
            }
        }
    }


    //для подключения после авторизации
    public synchronized void subscribe(ClientHandler clientHandler) throws IOException {
        clients.add(clientHandler);
        List<String> users = getAllUserNames();
        broadcastMessage(Command.updateUsersListCommand(users), null);
    }

    //для исключения после выхода или разрыва соединения
    public synchronized void unsubscribe(ClientHandler clientHandler) throws IOException {
        clients.remove(clientHandler);
        List<String> users = getAllUserNames();
        broadcastMessage(Command.updateUsersListCommand(users), null);
    }

    public List<String> getAllUserNames() {
        List<String> usernames = new LinkedList<>();
        for (ClientHandler clientHandler : clients) {
            usernames.add(clientHandler.getUserName());
        }
        return usernames;
    }

    //приват
    public synchronized  void sendMessage(String receiver, Command commandMessage) throws IOException {
        for (ClientHandler client : clients) {
            if(client.getUserName().equals(receiver)){
                client.sendMessage(commandMessage);
                break;
            }
        }
    }

    public boolean isNickBusy(String username){
        for (ClientHandler client : clients) {
            if(client.getUserName().equals(username)){
                return true;
            }
        }
        return false;
    }
}
