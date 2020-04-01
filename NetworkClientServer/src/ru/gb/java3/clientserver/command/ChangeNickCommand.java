package ru.gb.java3.clientserver.command;

import java.io.Serializable;

public class ChangeNickCommand implements Serializable {
    private final String login;
    private final String password;
    private final String username;

    public ChangeNickCommand(String login, String password, String newNick) {
        this.login = login;
        this.password = password;
        this.username = newNick;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

}
