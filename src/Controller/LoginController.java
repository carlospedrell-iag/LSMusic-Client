package Controller;

import View.LoginPanel;
import View.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {

    private MainWindow mainWindow;
    private LoginPanel view;

    public LoginController(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        this.view = mainWindow.getLoginPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println("submit login, name: " + view.getForm_name() + " password: " + view.getForm_password());
    }
}
