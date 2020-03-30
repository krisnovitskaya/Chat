package ru.gb.java3.client.window;

import ru.gb.java3.client.controller.ClientController;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class ChangeNickDialog extends JFrame{
    private JTextField loginField;
    private JButton buttonChange;
    private JButton buttonCancel;
    private JLabel label1;
    private JLabel loginLabel;
    private JLabel passLabel;
    private JLabel newNickLabel;
    private JPasswordField passwordField;
    private JPanel contentPane;
    private JTextField nickField;

    private ClientController controller;

    public ChangeNickDialog(ClientController controller) {
        this.controller = controller;
        setContentPane(contentPane);
        setSize(400, 200);
        getRootPane().setDefaultButton(buttonChange); //если интер, то окбаттон
        setLocationRelativeTo(null);
        buttonChange.addActionListener(e -> onOk());
        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
    }

    private void onCancel() {
        controller.changeNickDialogClose();
    }

    private void onOk() {
        String login = loginField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();
        String newNick = nickField.getText().trim();
        try {
            controller.sendChangeNickMessage(login, pass, newNick);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }
}
