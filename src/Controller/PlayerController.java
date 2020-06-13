package Controller;

import Model.Entity.Track;
import Model.MusicPlayer;
import View.MainWindow;
import View.PlayerPanel;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class PlayerController extends Thread implements ActionListener, LineListener, ItemListener {

    private PlayerPanel playerPanel;
    private volatile Boolean running = true;

    private Boolean playing = false;

    private MainWindow mainWindow;
    private final String DEFAULT_PROGRESS_LABEL = "0:00 - 0:00";


    public PlayerController(MainWindow mainWindow) {
        MusicPlayer.getInstance().setPlayerController(this);
        this.mainWindow = mainWindow;

        this.playerPanel = mainWindow.getHomePanel().getPlayerPanel();
        playerPanel.setProgress_label(DEFAULT_PROGRESS_LABEL);

        this.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "stop":
                MusicPlayer.getInstance().stopTrack();
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

        if(playing){
            //agafem els valors del Music player que ens diu a on esta la canço en la reproduccio
            int currentPosition = MusicPlayer.getInstance().getCurrentPosition();
            int duration = MusicPlayer.getInstance().getDuration() / 1000;
            int current_seconds = currentPosition / 1000;
            //per actualitzar la barra de reproducció on surt el progrés -> 2:03/5:24(======............)
            playerPanel.setProgressBarValue(currentPosition);
            playerPanel.setProgress_label(formatTime(current_seconds) + " - " + formatTime(duration));
        }
        setPlayerPanel();
    }

    private String formatTime(int s) {
        int minutes = s / 60;
        int seconds = s % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    @Override
    public void run() {
        while (running) {

            updatePlayer();

            try {
                //s'executa a 100Hz o almenys ho intenta
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setPlayerPanel() {
        String message = MusicPlayer.getInstance().getPlayer_message();
        String final_message = "";
        //el MusicPlayer mostra sempre un missatge amb el seu estat, crec que aixo quedaria millor amb un enum
        switch (message) {
            case "Now Playing":
                //si esta en mode reproduint, fem update del label del player i fiquem el nom del track "Now Playing . . . Despacito"
                Track track = MusicPlayer.getInstance().getCurrent_track();
                final_message = message + "  . . .  " + track.getTitle();
                playerPanel.showPlayerLabel();
                mainWindow.enableWindow();
                break;
            case "Downloading":
                //si esta en mode descarregar, elimina el label del player i fica una barra de download amb el progrés de la descarrega
                //que ens dona el music player
                playerPanel.showDownloadBar();
                playerPanel.updateDownloadBar();
                mainWindow.revalidate();
                mainWindow.disableWindow();
                break;
            default:
                //en qualsevol cas posem punts suspensius
                final_message = message + "    . . . . . . . . . . . . ";
                playerPanel.showPlayerLabel();
                mainWindow.enableWindow();
                break;
        }
        playerPanel.setPlayer_label(final_message);
    }

    @Override
    public void update(LineEvent event) {
        //si el music player acaba de començar a reproduir una canço
        if(event.getType() == LineEvent.Type.START){
            playing = true;
            initializePlayer();
        }
        //si el music player s'acaba d'aturar
        if(event.getType() == LineEvent.Type.STOP){
            playing = false;
            //resetejem el valor de la barra de reproducció
            playerPanel.setProgressBarValue(0);
            playerPanel.setProgress_label(DEFAULT_PROGRESS_LABEL);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        //mode repetir llista
        if(playerPanel.getRepeat_list().isSelected()){
            MusicPlayer.getInstance().setRepeatList(true);
        } else {
            MusicPlayer.getInstance().setRepeatList(false);
        }
        //mode repetir track
        if(playerPanel.getRepeat_track().isSelected()){
            MusicPlayer.getInstance().setRepeatTrack(true);
        } else {
            MusicPlayer.getInstance().setRepeatTrack(false);
        }
    }
}
