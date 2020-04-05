package ru.gb.java3.client.controller;

@FunctionalInterface
public interface AuthEvent {
    void authIsSuccessful(String nick, int id);
}
