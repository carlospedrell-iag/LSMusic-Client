package Controller;

import Model.Entity.ObjectMessage;
import Model.Entity.User;
import Model.ServerConnector;
import View.RegisterPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterController implements ActionListener {

    private RegisterPanel view;
    private ServerConnector serverConnector;

    public RegisterController(RegisterPanel registerPanel){
        this.view = registerPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        User user = new User(view.getForm_name(),view.getForm_password(), view.getForm_email());
        ObjectMessage user_om = new ObjectMessage(user,"register");

        serverConnector = new ServerConnector();
        serverConnector.registerUser(user_om);
    }
}
