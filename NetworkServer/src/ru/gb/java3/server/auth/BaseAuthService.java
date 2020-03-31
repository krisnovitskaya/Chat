package ru.gb.java3.server.auth;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseAuthService extends Config implements AuthService {

    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement pstmt;


    public static void connect() throws SQLException {
        try {
            String connectionString = String.format("jdbc:mysql://%s:%s/%s?useSSL=false", dbHost, dbPort, dbName);
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
    public boolean changeCurrentNickname(String login, String pass, String newNick) {
        //update users set nickname = 'newnick' where login = 'login1' and password = 'pass1';
        String prep = "UPDATE users SET nickname = ? WHERE login = ? and password = ?";
        try {
            pstmt = connection.prepareStatement(prep);
            pstmt.setString(1, newNick);
            pstmt.setString(2, login);
            pstmt.setString(3, pass);
            int x = pstmt.executeUpdate();

            if (x == 1){

                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
