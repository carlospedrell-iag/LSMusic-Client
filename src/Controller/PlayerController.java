package Controller;

import Model.Entity.Track;
import Model.MusicPlayer;
import View.MainWindow;
import View.PlayerPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerController extends Thread implements ActionListener {

    private MainWindow mainWindow;
    private PlayerPanel playerPanel;
    private volatile Boolean running = true;

    public PlayerController(MainWindow mainWindow) {
        this.start();
        this.mainWindow = mainWindow;
        this.playerPanel = mainWindow.getHomePanel().getPlayerPanel();
        playerPanel.setProgress_label("0:00 - 0:00");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "stop":
                MusicPlayer.getInstance().stopTrack();
                playerPanel.setProgressBarValue(0);
                playerPanel.setProgress_label("0:00 - 0:00");
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
            case "previous":
                System.out.println("PREVIOUS");
                MusicPlayer.getInstance().previousTrack();
                initializePlayer();
                break;
        }
    }

    public void initializePlayer() {
        playerPanel.setProgressBarMaximum(MusicPlayer.getInstance().getDuration());
    }


    public void updatePlayer() {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int currentPosition = MusicPlayer.getInstance().getCurrentPosition();
                int duration = MusicPlayer.getInstance().getDuration() / 1000;
                int current_seconds = currentPosition / 1000;

                playerPanel.setProgressBarValue(currentPosition);
                playerPanel.setProgress_label(formatTime(current_seconds) + " - " + formatTime(duration));
            }
        });

    }

    private String formatTime(int s) {
        int minutes = s / 60;
        int seconds = s % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
    //TODO:USAR SWing utilites new runnable?


    @Override
    public void run() {
        while (running) {

            if (MusicPlayer.getInstance().isPlaying()) {
                updatePlayer();
            } else {
                playerPanel.setProgressBarValue(0);
            }

            if (MusicPlayer.getInstance().getTrackStart()) {
                initializePlayer();
            }

            setPlayerLabel(MusicPlayer.getInstance().getPlayer_message());

            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPlayerLabel(String message) {
        String final_message;
        switch (message) {
            case "Now Playing":
                Track track = MusicPlayer.getInstance().getCurrent_track();
                final_message = message + "  . . .  " + track.getTitle();
                break;
            default:
                final_message = message + "    . . . . . . . . . . . . ";
                break;
        }
        playerPanel.setPlayer_label(final_message);
    }
}
