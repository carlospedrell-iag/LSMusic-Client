package Model;

public class MusicPlayer {

    private static MusicPlayer instance;

    public MusicPlayer(){

    }

    public static MusicPlayer getInstance() {
        if(instance == null){
            instance = new MusicPlayer();
        }
        return instance;
    }

    public void playTrack(int track_id){
        System.out.println("Playing Track: " + track_id);
    }
}
