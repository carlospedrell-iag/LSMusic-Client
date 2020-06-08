package Model.Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class PlaylistTrack implements Serializable {

    private int id;
    private int playlist_id;
    private int track_id;
    private float rating;

    public PlaylistTrack(int id, int playlist_id, int track_id, float rating) {
        this.id = id;
        this.playlist_id = playlist_id;
        this.track_id = track_id;
        this.rating = rating;
    }

    public PlaylistTrack(int playlist_id, int track_id, float rating) {
        this.playlist_id = playlist_id;
        this.track_id = track_id;
        this.rating = rating;
    }

    public PlaylistTrack(int playlist_id, int track_id) {
        this.playlist_id = playlist_id;
        this.track_id = track_id;
    }

    public int getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(int playlist_id) {
        this.playlist_id = playlist_id;
    }

    public int getTrack_id() {
        return track_id;
    }

    public void setTrack_id(int track_id) {
        this.track_id = track_id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
