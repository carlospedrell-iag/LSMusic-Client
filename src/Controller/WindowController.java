package Controller;

import View.LoginWindow;
import View.RegisterWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowController implements ActionListener {

    private LoginWindow loginWindow;
    private RegisterWindow registerWindow;

    public WindowController(LoginWindow loginWindow, RegisterWindow registerWindow){
        this.loginWindow = loginWindow;
        this.registerWindow = registerWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "login":
                registerWindow.setVisible(false);
                loginWindow.setVisible(true);
                break;

            case "register":
                loginWindow.setVisible(false);
                registerWindow.setVisible(true);
                break;
        }
    }
}
