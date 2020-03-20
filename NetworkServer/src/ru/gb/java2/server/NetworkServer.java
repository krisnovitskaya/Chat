package ru.gb.java2.server;

import ru.gb.java2.server.auth.AuthService;
import ru.gb.java2.server.auth.BaseAuthService;
import ru.gb.java2.server.client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
    public synchronized void broadcastMessage(String message) throws IOException{
        for(ClientHandler client : clients ){
            client.sendMessage(message);

        }
    }

    public synchronized void sendPrivateMessage(String message) throws IOException{
        String[] messageParts = message.split(" ", 4);
        // /w sender recipient message
        for(ClientHandler client : clients ){
            if((client.getUserName()).equals(messageParts[1]) || (client.getUserName()).equals(messageParts[2])){

                client.sendMessage(String.format("%s приватно %s: %s", messageParts[1], messageParts[2], messageParts[3]));
            }


        }
    }

    //для подключения после авторизации
    public synchronized void subscribe(ClientHandler clientHandler){
        clients.add(clientHandler);
    }

    //для исключения после выхода или разрыва соединения
    public synchronized void unsubscribe(ClientHandler clientHandler){
        clients.remove(clientHandler);
    }
}
