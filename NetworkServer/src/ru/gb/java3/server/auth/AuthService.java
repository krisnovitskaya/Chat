package ru.gb.java3.server.auth;

public interface AuthService {
    boolean changeCurrentNickname(String login, String pass, String newNick);
    String getUserNameByLoginAndPass(String login, String pass);
    void start();
    void stop();
}
