package ru.gb.java3.clientserver;

public enum CommandType {
    AUTH,
    AUTH_ERROR,
    PRIVATE_MESSAGE,
    BROADCAST_MESSAGE,
    MESSAGE,
    UPDATE_USER_LIST,
    ERROR,
    END
}
