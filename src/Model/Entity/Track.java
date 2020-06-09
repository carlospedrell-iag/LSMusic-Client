package Model.Entity;

import java.io.Serializable;

public class Track implements Serializable {

    private int id;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private String path;
    private int plays;
    private float rating;

    private int playlist_track_id;
    private byte[] file;

    public Track(int id, String title, String artist, String album, String genre, String path, int plays, float rating) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.path = path;
        this.plays = plays;
        this.rating = rating;
    }

    public Track(String title, String artist, String album, String genre, String path) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.path = path;
    }

    public String getStarRating(){
        String[] stars = {
                "★",
                "★★",
                "★★★",
                "★★★★",
                "★★★★★",
        };

        return stars[(int)rating - 1];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPlays() {
        return plays;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getPlaylist_track_id() {
        return playlist_track_id;
    }

    public void setPlaylist_track_id(int playlist_track_id) {
        this.playlist_track_id = playlist_track_id;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }
}
