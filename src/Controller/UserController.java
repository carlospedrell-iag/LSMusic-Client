package Controller;

import Model.Entity.ObjectMessage;
import Model.Entity.User;
import Model.ServerConnector;
import View.MainWindow;
import View.UserPanel;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class UserController implements MouseListener {

    private MainWindow mainWindow;
    private UserPanel userPanel;

    private HomeController homeController;

    private ArrayList<User> followedUsers;

    public UserController(MainWindow mainWindow, HomeController homeController){
        this.mainWindow = mainWindow;
        this.userPanel = mainWindow.getHomePanel().getUserPanel();
        this.homeController = homeController;
        updateTable();

    }

    private void updateTable(){
        //demanem al servidor la llista de cançons del sistema
        ObjectMessage output_obj = new ObjectMessage(null,"request_following");
        ObjectMessage received_obj = ServerConnector.getInstance().sendObject(output_obj);
        this.followedUsers = (ArrayList<User>)received_obj.getObject();

        userPanel.refreshTable(followedUsers);
        mainWindow.revalidate();

    }

    @Override
    public void mousePressed(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
            String name = (String)table.getValueAt(table.getSelectedRow(),0);
            homeController.setPlaylistUser(name);
            homeController.refreshPlaylists();
            mainWindow.revalidate(); //TODO: que paasa si se quita
        }
    }

    public ArrayList<User> getFollowedUsers() {
        return followedUsers;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }


    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
