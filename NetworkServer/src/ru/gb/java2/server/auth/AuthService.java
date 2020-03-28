package ru.gb.java2.server.auth;

public interface AuthService {

    String getUserNameByLoginAndPass(String login, String pass);
    void start();
    void stop();
}
