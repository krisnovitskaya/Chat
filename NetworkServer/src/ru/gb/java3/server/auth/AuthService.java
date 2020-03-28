package ru.gb.java3.server.auth;

public interface AuthService {

    String getUserNameByLoginAndPass(String login, String pass);
    void start();
    void stop();
}
