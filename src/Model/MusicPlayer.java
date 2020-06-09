package Model;

import Model.Entity.ObjectMessage;
import Model.Entity.Playlist;
import Model.Entity.Track;

import javax.sound.sampled.*;
import java.io.*;

public class MusicPlayer extends Thread{

    private static final String CACHE_PATH = "./music-cache/track_id";

    private static MusicPlayer instance;
    private static AudioInputStream ais;
    private static Clip clip;
    private static Boolean playing;
    private static File file;
    private static int duration;
    private static int seconds;
    private static int currentPosition;
    private volatile Boolean running;
    private int track_id = -1;
    private int playlist_id = -1;
    private int track_index = -1;
    private int queue_index = -1;
    private Boolean trackOver = false;
    private Boolean trackStart = false;

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
                seconds = (int)clip.getMicrosecondPosition() / 1000000;

                currentPosition = (int)clip.getMicrosecondPosition() / 1000;

                if(clip.getMicrosecondPosition() >= clip.getMicrosecondLength()){
                    trackOver = true;
                    stopTrack();
                    nextTrack();
                    System.out.println("STOP");
                }

                trackStart = false;
            }
        }
    }

    public void setAndPlayTrack(int track_id){
        //Si la canço ja esta descarregada la torna a posar i si no elimina la canço que s'esta reproduint i reprodueix la nova
        if(this.track_id == track_id){
            stopTrack();
            playTrack();
        } else {
            stopTrack();
            deleteTrack();
            setTrack(track_id);
            playTrack();
        }
    }

    public void nextTrack(){
        if(track_index != -1){
            if(track_index < queue.getTracks().size() -1){

                track_index++;
                setAndPlayTrack(queue.getTracks().get(track_index).getId());
            } else {
                track_index = -1;
            }
        }
    }

    public void setTrack(int track_id){

        //es una canço diferent
        this.track_id = track_id;
        //demana el track al servidor i el descarrega localment
        ObjectMessage om = new ObjectMessage(track_id,"request_file");
        ObjectMessage input_om = ServerConnector.getInstance().sendObject(om);
        Track track = (Track)input_om.getObject();

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

    public void playTrack(){
        try{
            if(!playing){
                ais = AudioSystem.getAudioInputStream(file);

                clip = AudioSystem.getClip();
                clip.open(ais);

                duration = (int)clip.getMicrosecondLength() / 1000;
                System.out.println("Track Duration: " + duration);

                clip.start();

                trackOver = false;
                trackStart = true;
                playing = true;
                System.out.println("Playing.");
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
            trackOver = false;
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

    public void setQueue(Playlist queue, int track_index, int queue_index){
        System.out.println("Track index: " + track_index);
        this.queue = queue;
        this.track_index = track_index;
        this.queue_index = queue_index;
    }

    private String getFileExtension(String path) {
        int lastIndexOf = path.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return path.substring(lastIndexOf);
    }

    public Clip getClip() {
        return clip;
    }

    public static Boolean isPlaying() {
        return playing;
    }

    public static int getDuration() {
        return duration;
    }

    public static int getCurrentPosition() {
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
}
