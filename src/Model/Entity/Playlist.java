package Model.Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Playlist implements Serializable {

    private int id;
    private String name;
    private int id_user;

    private ArrayList<Track> tracks;

    public Playlist(int id, String name, int id_user, ArrayList<Track> tracks) {
        this.id = id;
        this.name = name;
        this.id_user = id_user;
        this.tracks = tracks;
    }

    public Playlist(String name, int id_user){
        this.name = name;
        this.id_user = id_user;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getId_user() {
        return id_user;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void addTrack(Track track){
        tracks.add(track);
    }
}
