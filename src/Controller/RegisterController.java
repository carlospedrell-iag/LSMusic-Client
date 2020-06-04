package Controller;

import Model.Entity.ObjectMessage;
import Model.Entity.User;
import Model.ServerConnector;
import View.MainWindow;
import View.RegisterPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterController implements ActionListener {

    private RegisterPanel view;
    private MainWindow mainWindow;
    private ServerConnector serverConnector;

    public RegisterController(MainWindow mainWindow){
        this.view = mainWindow.getRegisterPanel();
        this.mainWindow = mainWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //enviem les dades de l'usuari al servidor
        User user = new User(view.getForm_name(), view.getForm_email(),view.getForm_password());
        user.setForm_password(view.getForm_confirmpassword());

        ObjectMessage user_om = new ObjectMessage(user,"register");

        serverConnector = new ServerConnector();
        ObjectMessage output_obj = serverConnector.sendObject(user_om);

        //si retorna errors els mostrem per GUI
        if(!output_obj.getErrors().isEmpty()){
            mainWindow.showError(output_obj.getFormattedErrors());
        } else {
            //si no hi ha hagut cap error llavors mostrem un missatge i fem login
            mainWindow.showSuccess("Usuari enregistrat exitosament!");
            mainWindow.switchPanel("login");
        }
    }
}
