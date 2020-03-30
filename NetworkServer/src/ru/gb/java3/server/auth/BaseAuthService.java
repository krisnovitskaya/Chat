package ru.gb.java3.server.auth;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseAuthService extends Config implements AuthService {

//    private static class UserData {
//        private String login;
//        private String password;
//        private String userName;
//
//        public UserData(String login, String password, String userName) {
//            this.login = login;
//            this.password = password;
//            this.userName = userName;
//        }
//    }
//
//    private  List<UserData> userData;
//
//    public BaseAuthService() {
//        userData = new ArrayList<>();
//        userData.add(new UserData("login1", "pass1", "nick1"));
//        userData.add(new UserData("login2", "pass2", "nick2"));
//        userData.add(new UserData("login3", "pass3", "nick3"));
//    }
//
//    @Override
//    public String getUserNameByLoginAndPass(String login, String pass) {
//        for (UserData data : userData) {
//            if(data.login.equals(login) && data.password.equals(pass)) return data.userName;
//        }
//        return null;
//    }

    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement pstmt;


    public static void connect() throws SQLException {
        try {
            String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?useSSL=false";
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getUserNameByLoginAndPass(String login, String pass) {
        //SELECT nickname from users where login = 'login1' and password = 'pass1';
        String prep = "SELECT nickname from users where login = ? and password = ?";
        try {
            pstmt = connection.prepareStatement(prep);
            pstmt.setString(1, login);
            pstmt.setString(2, pass);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                return rs.getString("nickname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void start() {
        try {
            connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Сервис аутентификации запущен.");
    }

    @Override
    public void stop() {
        disconnect();
        System.out.println("Сервис аутентификации остановлен.");

    }
}
