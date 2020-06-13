package Controller;

import Model.Entity.ObjectMessage;
import Model.Entity.Session;
import Model.Entity.User;
import Model.ServerConnector;
import View.MainWindow;
import View.UserPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class UserController implements MouseListener, ActionListener {

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
        ObjectMessage output_obj = new ObjectMessage(Session.getInstance().getUser(),"request_following");
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
            mainWindow.revalidate();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "follow":
                String name = mainWindow.showInputDialog("Escriu el nom de l'usuari:","Seguir Usuari");
                if(name != null){
                    if(name.isBlank()){
                        mainWindow.showError("El camp no pot estar buit.");
                    } else if (!userExists(name)) {
                        mainWindow.showError("L'usuari introduit no existeix.");
                    } else if(name.equals(Session.getInstance().getUser().getName())){
                        mainWindow.showError("No et pots seguir a tu mateix.");
                    } else if(alreadyFollowing(name)){
                        mainWindow.showError("Ja segueixes aquest usuari");
                    } else {
                        followUser(name);
                        updateTable();
                    }
                }
                break;
            case "unfollow":
                //agafa el nom de l'usuari seleccionat i fa una peticio al servidor per eliminar-lo
                JTable table = userPanel.getUser_table();
                int selected_row = table.getSelectedRow();
                if(selected_row != -1){
                    String followed_user = (String)table.getValueAt(selected_row,0);
                    unfollowUser(followed_user);
                    updateTable();
                } else {
                    mainWindow.showError("No has seleccionat cap usuari.");
                }

                break;
        }
    }

    private Boolean alreadyFollowing(String name){
        Boolean flag = false;

        for(User u:followedUsers){
            if(u.getName().equals(name)){
                flag = true;
            }
        }

        return flag;
    }

    private Boolean userExists(String name){
        ObjectMessage output_om = new ObjectMessage(name,"request_user");
        ObjectMessage input_om = ServerConnector.getInstance().sendObject(output_om);

        if(input_om.getObject() == null){
            return false;
        } else {
            return true;
        }
    }

    private void followUser(String name){
        ObjectMessage output_om = new ObjectMessage(Session.getInstance().getUser(),"follow_user");
        output_om.setExtra(name);
        ObjectMessage input_om = ServerConnector.getInstance().sendObject(output_om);
    }

    private void unfollowUser(String name){
        ObjectMessage output_om = new ObjectMessage(Session.getInstance().getUser(),"unfollow_user");
        output_om.setExtra(name);
        ObjectMessage input_om = ServerConnector.getInstance().sendObject(output_om);
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
