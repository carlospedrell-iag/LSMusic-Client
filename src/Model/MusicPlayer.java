package Model;

import Model.Entity.ObjectMessage;
import Model.Entity.Track;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MusicPlayer extends Thread{

    private static MusicPlayer instance;
    private static AudioInputStream ais;
    private static Clip clip;
    private static Boolean playing;
    private static File file;
    private static int duration;
    private static int seconds;
    private static int currentPosition;
    private volatile Boolean running;

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
                    stopTrack();
                    System.out.println("STOP");
                }
            }
        }
    }

    public void setTrack(int track_id){
        ObjectMessage om = new ObjectMessage(track_id,"request_file");
        ObjectMessage input_om = ServerConnector.getInstance().sendObject(om);
        Track track = (Track)input_om.getObject();

        String path = "./music/track_id" + track.getId() + getFileExtension(track.getPath());

        file = new File(path);

        if(!file.exists()){
            byte[] content = track.getFile();

            try{

                FileOutputStream fs = new FileOutputStream("./music/track_id" + track.getId() + getFileExtension(track.getPath()));
                fs.write(content);
                fs.close();

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void playTrack(){
        try{
            if(!playing){
                ais = AudioSystem.getAudioInputStream(file);
                AudioFormat format = ais.getFormat();
                long frameCount = ais.getFrameLength();



                clip = AudioSystem.getClip();
                clip.open(ais);

                duration = (int)clip.getMicrosecondLength() / 1000;
                System.out.println("Duration: " + duration);

                clip.start();

                playing = true;
            }

        } catch ( Exception e){
            e.printStackTrace();
        }
    }

    public void stopTrack(){
        clip.stop();
        playing = false;
        file.delete();
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
}
