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
                MusicPlayer.getInstance().playTrack();
                initializePlayer();
                break;
            case "next":
                System.out.println("NEXT");
                MusicPlayer.getInstance().nextTrack();
                initializePlayer();
                break;
        }
    }

    public void initializePlayer(){
        playerPanel.setProgressBarMaximum(MusicPlayer.getDuration());
    }


    public void updatePlayer(){
        //System.out.println("current position " + MusicPlayer.getCurrentPosition());
        playerPanel.setProgressBarValue(MusicPlayer.getCurrentPosition());
    }

    @Override
    public void run() {
        while(running){

            if(MusicPlayer.getInstance().isPlaying()){
                updatePlayer();
            } else {
                playerPanel.setProgressBarValue(0);
            }

            if(MusicPlayer.getInstance().getTrackStart()){
                initializePlayer();
            }
        }
    }

}
