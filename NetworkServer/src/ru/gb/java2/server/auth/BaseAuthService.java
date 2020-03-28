package ru.gb.java2.server.auth;

import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {

    private static class UserData {
        private String login;
        private String password;
        private String userName;

        public UserData(String login, String password, String userName) {
            this.login = login;
            this.password = password;
            this.userName = userName;
        }
    }

    private  List<UserData> userData;

    public BaseAuthService() {
        userData = new ArrayList<>();
        userData.add(new UserData("login1", "pass1", "nick1"));
        userData.add(new UserData("login2", "pass2", "nick2"));
        userData.add(new UserData("login3", "pass3", "nick3"));
    }

    @Override
    public String getUserNameByLoginAndPass(String login, String pass) {
        for (UserData data : userData) {
            if(data.login.equals(login) && data.password.equals(pass)) return data.userName;
        }
        return null;
    }

    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен.");
    }

    @Override
    public void stop() {
        System.out.println("Сервис аутентификации остановлен.");

    }
}
