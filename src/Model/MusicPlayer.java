package Model;

import Controller.PlayerController;
import Controller.PlaylistController;
import Controller.WindowController;
import Model.Entity.ObjectMessage;
import Model.Entity.Playlist;
import Model.Entity.Session;
import Model.Entity.Track;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;

public class MusicPlayer{

    private static final String CACHE_PATH = "./music-cache/track_id";

    private static MusicPlayer instance;
    private AudioInputStream ais;
    private Clip clip;
    private File file;
    private int track_id = -1;
    private int track_index = -1;
    private int queue_index = -1;
    private volatile String player_message = "No Music Playing";
    private Track current_track;
    private Playlist queue;

    private volatile Boolean playing;
    private volatile Boolean manualMode = false;

    private LineListener lineListener;

    private PlayerController playerController;
    private PlaylistController playlistController;


    public MusicPlayer(){
        playing = false;

        this.lineListener = new LineListener() {
            @Override
            public void update(LineEvent event) {
                //listener que s'activa quan el track finalitza pero nomes automaticament
                if(event.getType() == LineEvent.Type.STOP && !manualMode){
                    nextTrack();
                } else {
                    manualMode = false;
                }
            }
        };
    }

    public static MusicPlayer getInstance() {
        if(instance == null){
            instance = new MusicPlayer();
        }
        return instance;
    }

    public void downloadTrack(Track track_request){
        String path = CACHE_PATH + track_request.getId() + getFileExtension(track_request.getPath());

        System.out.println("Downloading");
        this.player_message = "Downloading";
        this.current_track = track_request;
        //es una canço diferent
        this.track_id = track_request.getId();
        //demana el track al servidor i el descarrega localment
        ServerConnector.getInstance().requestFile(track_request);
        file = new File(path);
        System.out.println("Arxiu rebut " + file.getName());
    }

    public void playTrack(){
        try{
            if(!playing && file != null){
                ais = AudioSystem.getAudioInputStream(file);

                clip = AudioSystem.getClip();
                clip.addLineListener(playerController); //PLAYER
                clip.addLineListener(playlistController); //PLAYlist
                clip.addLineListener(this.lineListener);

                clip.open(ais);
                clip.start();

                playing = true;

                System.out.println("Playing.");
                this.player_message = "Now Playing";
            }

        } catch ( Exception e){
            e.printStackTrace();
        }
    }

    public void stopTrack(){
        if(playing){
            manualStopClip();
            playing = false;
            try{
                System.out.println("ais closed");
                ais.close();
            }catch (IOException e){
                e.printStackTrace();
            }
            this.player_message = "No Music Playing";
        }
    }

    public void pauseTrack(){

    }

    public void nextTrack(){
        if(track_index != -1){
            if(track_index < queue.getTracks().size() -1){
                track_index++;
                setAndPlayTrack(queue.getTracks().get(track_index));
            } else {
                track_index = -1;
                stopTrack();
            }
        }
    }

    public void previousTrack(){
        if(track_index != -1){
            if(track_index > 0){
                track_index--;
                setAndPlayTrack(queue.getTracks().get(track_index));
            } else {
                track_index = -1;
                stopTrack();
            }
        }
    }

    public void deleteTrack(){
        if(file != null){
            if(file.delete()){
                System.out.println("Archiu " + file.getName() + " eliminat.");
            } else {
                System.out.println("Error en eliminar archiu.");
            }
        }
    }

    public void setAndPlayTrack(Track track){
        updatePlaycount(track);
        //Si la canço ja esta descarregada la torna a posar i si no elimina la canço que s'esta reproduint i reprodueix la nova
        if(this.track_id == track.getId()){
            stopTrack();
            playTrack();
        } else {

            Thread thread = new Thread("Download") {
                public void run(){
                    stopTrack();
                    deleteTrack();
                    downloadTrack(track);
                    playTrack();
                }
            };
            thread.start();
        }
    }

    public void setQueue(Playlist queue,int queue_index, int track_index){
        System.out.println("Track index: " + track_index);
        this.queue = queue;
        this.queue_index = queue_index;
        this.track_index = track_index;
    }

    public void setQueuePlaylist(Playlist queue){
        this.queue = queue;
    }

    public void resetQueue() {
        this.queue_index = -1;
        this.track_index = -1;
    }

    private void manualStopClip(){
        this.manualMode = true;
        System.out.println("Manual mode: " + manualMode);
        clip.stop();
    }

    private void updatePlaycount(Track track){
        ObjectMessage om = new ObjectMessage(track,"update_playcount");
        ObjectMessage input_om = ServerConnector.getInstance().sendObject(om);
        Object output_obj = input_om.getObject();
    }

    private String getFileExtension(String path) {
        int lastIndexOf = path.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return path.substring(lastIndexOf);
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    public void setPlaylistController(PlaylistController playlistController) { this.playlistController = playlistController; }

    public void destroy(){
        instance = null;
    }

    public int getDuration() {
        if(clip != null){
            return (int)clip.getMicrosecondLength() / 1000;
        } else {
            return 0;
        }
    }

    public int getCurrentPosition() {
        return (int)this.clip.getMicrosecondPosition() / 1000;
    }

    public int getQueue_index() {
        return queue_index;
    }

    public int getTrack_index() {
        return track_index;
    }

    public String getPlayer_message() {
        return player_message;
    }

    public Track getCurrent_track() {
        return current_track;
    }

}
