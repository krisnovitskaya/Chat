package ru.gb.java3.clientserver.command;

import java.io.Serializable;

public class AuthCommand implements Serializable {
    private final String login;
    private final String password;
    private int id;
    private String username;

    public AuthCommand(String login, String password) {
        this.login = login;
        this.password = password;

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
    public int getID() {return id;}

    public void setID(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
