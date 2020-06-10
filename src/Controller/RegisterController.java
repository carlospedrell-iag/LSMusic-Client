package Controller;

import Model.Entity.ObjectMessage;
import Model.Entity.Session;
import Model.Entity.User;
import Model.ServerConnector;
import View.MainWindow;
import View.RegisterPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterController implements ActionListener {

    private RegisterPanel view;
    private MainWindow mainWindow;

    public RegisterController(MainWindow mainWindow){
        this.view = mainWindow.getRegisterPanel();
        this.mainWindow = mainWindow;
    }
    //TODO: Un server conenctor para cada clase
    @Override
    public void actionPerformed(ActionEvent e) {
        ServerConnector serverConnector = new ServerConnector();
        //enviem les dades de l'usuari al servidor
        User user = new User(view.getForm_name(), view.getForm_email(),view.getForm_password());
        user.setForm_password(view.getForm_confirmpassword());

        ObjectMessage user_om = new ObjectMessage(user,"register");

        ObjectMessage received_obj = serverConnector.sendObject(user_om);

        //si retorna errors els mostrem per GUI
        if(!received_obj.getErrors().isEmpty()){
            mainWindow.showError(received_obj.getFormattedErrors());
        } else {
            //si no hi ha hagut cap error llavors mostrem un missatge i fem login
            mainWindow.showMessage("Usuari enregistrat exitosament!");
            Session.getInstance().setUser((User)received_obj.getObject());
            mainWindow.switchPanel("home");
        }
    }
}
