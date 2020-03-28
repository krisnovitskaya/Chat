package ru.gb.java3.client;

import ru.gb.java3.client.controller.ClientController;

import java.io.IOException;

public class NetworkClient {
    public static void main(String[] args) {
        try{
            ClientController clientController = new ClientController("localhost", 4050);
            clientController.goApp();
        }catch(IOException e){
            System.err.println("Ошибка соединения  сервером");
        }
    }
}
