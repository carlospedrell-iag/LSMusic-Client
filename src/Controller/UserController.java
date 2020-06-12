package Controller;

import Model.Entity.ObjectMessage;
import Model.Entity.User;
import Model.ServerConnector;
import View.MainWindow;
import View.UserPanel;

import java.util.ArrayList;

public class UserController {

    private MainWindow mainWindow;
    private UserPanel userPanel;

    public UserController(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        this.userPanel = mainWindow.getHomePanel().getUserPanel();

        updateTable();

    }

    private void updateTable(){
        //demanem al servidor la llista de cançons del sistema
        ObjectMessage output_obj = new ObjectMessage(null,"request_users");
        ObjectMessage received_obj = ServerConnector.getInstance().sendObject(output_obj);

        if(received_obj.getObject() instanceof ArrayList){
            userPanel.refreshTable((ArrayList<User>)received_obj.getObject());
            mainWindow.revalidate();
        } else {
            System.out.println("Invalid Object");

        }
    }

}
