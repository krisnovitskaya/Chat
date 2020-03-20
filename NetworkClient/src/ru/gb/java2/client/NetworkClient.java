package ru.gb.java2.client;

import ru.gb.java2.client.controller.ClientController;

import java.io.IOException;

public class NetworkClient {
    public static void main(String[] args) {
        try{
            ClientController clientController = new ClientController("localhost", 4040);
            clientController.goApp();
        }catch(IOException e){
            System.err.println("Ошибка соединения  сервером");
        }
    }
}
