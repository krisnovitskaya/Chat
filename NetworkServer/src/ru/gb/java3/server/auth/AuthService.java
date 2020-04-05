package ru.gb.java3.server.auth;

import javafx.util.Pair;

public interface AuthService {
    boolean changeCurrentNickname(String login, String pass, String newNick);
    Pair<Integer, String> getUserNameByLoginAndPass(String login, String pass);
    void start();
    void stop();
}
