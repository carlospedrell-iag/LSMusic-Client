package Controller;

import View.HomePanel;
import View.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class HomeController implements ActionListener {

    private MainWindow mainWindow;
    private HomePanel homePanel;

    private MusicController musicController;
    private PlaylistController playlistController;
    private PlayerController playerController;

    public HomeController(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        this.homePanel = mainWindow.getHomePanel();

        this.musicController = new MusicController(mainWindow,this);
        this.playlistController = new PlaylistController(mainWindow,this);
        this.playerController = new PlayerController(mainWindow);

        homePanel.getMusicPanel().setUpController(this.musicController);
        homePanel.getPlaylistPanel().setUpController(this.playlistController);
        homePanel.getPlayerPanel().setUpController(this.playerController);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "refresh":
                refreshAll();
                break;
            case "sign_out":
                int dialogResult = mainWindow.showConfirmMessage("Vols Sortir?");

                if(dialogResult == JOptionPane.YES_OPTION){
                    mainWindow.switchPanel("login");
                }

                break;
        }
    }

    public void refreshAll(){
        musicController.updateTable();
        playlistController.updatePlaylists();
    }
}
