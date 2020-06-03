package Controller;

import View.LoginWindow;
import View.RegisterWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {

    private LoginWindow view;

    public LoginController(LoginWindow loginWindow){
        this.view = loginWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println("submit login, name: " + view.getForm_name() + " password: " + view.getForm_password());
    }
}
