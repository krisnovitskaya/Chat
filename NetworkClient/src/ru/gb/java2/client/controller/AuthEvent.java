package ru.gb.java2.client.controller;

@FunctionalInterface
public interface AuthEvent {
    void authIsSuccessful(String nick);
}
