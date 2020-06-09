package Controller;

import Model.MusicPlayer;
import View.MainWindow;
import View.PlayerPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerController extends Thread implements ActionListener {

    private MainWindow mainWindow;
    private PlayerPanel playerPanel;
    private int clock;
    Boolean playing = false;
    Timer timer;
    private volatile Boolean running = true;


    public PlayerController(MainWindow mainWindow){
        this.start();
        this.mainWindow = mainWindow;
        this.playerPanel = mainWindow.getHomePanel().getPlayerPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "stop":
                MusicPlayer.getInstance().stopTrack();
                playerPanel.setProgressBarValue(0);
                break;
            case "play":
                if(!MusicPlayer.isPlaying()){
                    MusicPlayer.getInstance().playTrack();
                    initializePlayer();
                }

                break;
        }
    }

    public void initializePlayer(){
        playerPanel.setProgressBarMaximum(MusicPlayer.getDuration());
    }


    public void updatePlayer(){
        System.out.println(MusicPlayer.getCurrentPosition());
        playerPanel.setProgressBarValue(MusicPlayer.getCurrentPosition());
    }

    @Override
    public void run() {
        while(running){
            while(MusicPlayer.getInstance().isPlaying()){
                updatePlayer();
            }

        }
    }

}
