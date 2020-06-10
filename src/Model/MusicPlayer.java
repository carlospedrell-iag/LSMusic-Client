package Model;

import Controller.PlayerController;
import Controller.PlaylistController;
import Model.Entity.ObjectMessage;
import Model.Entity.Playlist;
import Model.Entity.Track;

import javax.sound.sampled.*;
import java.io.*;

public class MusicPlayer extends Thread{

    private static final String CACHE_PATH = "./music-cache/track_id";

    private static MusicPlayer instance;
    private AudioInputStream ais;
    private Clip clip;
    private volatile Boolean playing;
    private File file;
    private int duration;
    private int currentPosition;
    private volatile Boolean running;
    private int track_id = -1;
    private int track_index = -1;
    private int queue_index = -1;
    private volatile Boolean trackOver = false;
    private volatile Boolean trackStart = false;
    private volatile String player_message = "No Music Playing";
    private Track current_track;
    private Playlist queue;

    public MusicPlayer(){
        playing = false;
        running = true;
        this.start();
    }

    public static MusicPlayer getInstance() {
        if(instance == null){
            instance = new MusicPlayer();
        }
        return instance;
    }

    @Override
    public void run(){
        System.out.println("running");
        while(running){
            while(playing){
                currentPosition = (int)clip.getMicrosecondPosition() / 1000;
                if(clip.getMicrosecondPosition() >= clip.getMicrosecondLength()){
                    trackEnd();
                }
                if(trackStart = true){
                    trackStart = false;
                }

            }

            try{
                sleep(100);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }

    public void setTrack(Track track_request){
        System.out.println("Downloading");
        this.player_message = "Downloading";
        this.current_track = track_request;
        //es una canço diferent
        this.track_id = track_request.getId();
        //demana el track al servidor i el descarrega localment
        System.out.println("estamos en un nuevo thread");
        ObjectMessage om = new ObjectMessage(track_id,"request_file");
        ObjectMessage input_om = ServerConnector.getInstance().sendObject(om);
        Track track = (Track)input_om.getObject();

        if(track != null){
            String path = CACHE_PATH + track.getId() + getFileExtension(track.getPath());

            file = new File(path);

            System.out.println("Arxiu rebut " + file.getName());

            byte[] content = track.getFile();

            try{
                FileOutputStream fs = new FileOutputStream(path);
                fs.write(content);
                fs.close();

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void playTrack(){
        try{
            if(!playing && file != null){
                ais = AudioSystem.getAudioInputStream(file);

                clip = AudioSystem.getClip();
                clip.open(ais);
                duration = (int)clip.getMicrosecondLength() / 1000;
                clip.start();

                trackOver = false;
                trackStart = true;
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
            clip.stop();
            playing = false;
            try{
                ais.close();
            }catch (IOException e){
                e.printStackTrace();
            }
            this.player_message = "No Music Playing";
            trackOver = false;
        }

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
        //Si la canço ja esta descarregada la torna a posar i si no elimina la canço que s'esta reproduint i reprodueix la nova
        if(this.track_id == track.getId()){
            stopTrack();
            playTrack();
        } else {

            Thread thread = new Thread("Download") {
                public void run(){
                    stopTrack();
                    deleteTrack();
                    setTrack(track);
                    playTrack();
                }
            };

            thread.start();
        }
    }

    private void trackEnd(){
        trackOver = true;
        stopTrack();
        nextTrack();
        System.out.println("STOP");
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

    private String getFileExtension(String path) {
        int lastIndexOf = path.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return path.substring(lastIndexOf);
    }

    public void destroy(){
        instance = null;
    }

    public Boolean isPlaying() {
        return playing;
    }

    public int getDuration() {
        return duration;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public int getQueue_index() {
        return queue_index;
    }

    public int getTrack_index() {
        return track_index;
    }

    public Boolean isTrackOver() {
        return trackOver;
    }

    public Boolean getTrackStart() {
        return trackStart;
    }

    public String getPlayer_message() {
        return player_message;
    }

    public Track getCurrent_track() {
        return current_track;
    }
}
