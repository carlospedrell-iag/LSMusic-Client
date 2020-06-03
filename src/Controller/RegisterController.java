package Controller;

import Model.Entity.ObjectMessage;
import Model.Entity.User;
import Model.ServerConnector;
import View.RegisterWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class RegisterController implements ActionListener {

    private RegisterWindow view;
    private ServerConnector serverConnector;

    public RegisterController(RegisterWindow registerWindow){
        this.view = registerWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        User user = new User(view.getForm_name(),view.getForm_password(), view.getForm_email());
        ObjectMessage user_om = new ObjectMessage(user,"register");

        serverConnector = new ServerConnector();
        serverConnector.registerUser(user_om);
    }
}
