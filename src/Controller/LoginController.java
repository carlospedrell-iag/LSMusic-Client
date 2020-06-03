package Controller;

import View.LoginPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {

    private LoginPanel view;

    public LoginController(LoginPanel loginPanel){
        this.view = loginPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println("submit login, name: " + view.getForm_name() + " password: " + view.getForm_password());
    }
}
