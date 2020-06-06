package Controller;

import Model.Entity.ObjectMessage;
import Model.Entity.Session;
import Model.Entity.User;
import Model.ServerConnector;
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
        //enviem les dades de l'usuari al servidor
        User user = new User(view.getForm_name(), view.getForm_name(),view.getForm_password());

        ObjectMessage user_om = new ObjectMessage(user,"login");

        ObjectMessage output_obj = ServerConnector.getInstance().sendObject(user_om);

        //si retorna errors els mostrem per GUI
        if(!output_obj.getErrors().isEmpty()){
            mainWindow.showError(output_obj.getFormattedErrors());
        } else {
            //si no hi ha hagut cap error fem login
            //iniciem la sessio assignant l'usuari
            Session.getInstance().setUser((User)output_obj.getObject());
            mainWindow.switchPanel("home");
        }
    }
}
