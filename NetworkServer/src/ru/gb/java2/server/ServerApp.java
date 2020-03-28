package ru.gb.java2.server;



public class ServerApp {

    private static final int DEFAULT_PORT = 4040;
    public static void main(String[] args) {
        int port = getServerPort(args);
        new NetworkServer(port).go();
    }

    private static int getServerPort(String[] args) {
        int port = DEFAULT_PORT;
        if(args.length ==1){
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Некорректный порт. Будет использоваться порт по умолчанию.");
            }
        }
        return port;
    }
}
