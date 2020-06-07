package Controller;

import Model.Entity.*;
import Model.ServerConnector;
import View.MainWindow;
import View.MusicPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MusicController implements ActionListener {

    private MainWindow mainWindow;
    private HomeController homeController;
    private MusicPanel musicPanel;

    private ArrayList<Playlist> user_playlists;
    private ArrayList<Track> tracklist;

    public MusicController(MainWindow mainWindow, HomeController homeController){
        this.mainWindow = mainWindow;
        this.musicPanel = mainWindow.getHomePanel().getMusicPanel();

        this.user_playlists = requestPlaylists();
        this.homeController = homeController;
        updateTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "add_track":
                addTrackToPlaylist();
                homeController.refreshAll();
                break;
        }
    }

    public void updateTable(){
        try{
            this.tracklist = requestTrackList();
            //recull info d'user de la DB i la envia a la vista per refrescar la taula
            musicPanel.refreshTable(tracklist);
            mainWindow.revalidate();
            System.out.println("Taula musica actualitzada");
        } catch (Exception e){
            mainWindow.showError("Error al connectar al servidor");
            mainWindow.revalidate();
        }
    }

    private ArrayList<Track> requestTrackList(){
        //demanem al servidor la llista de cançons del sistema
        ObjectMessage output_obj = new ObjectMessage(null,"request_tracklist");
        ObjectMessage received_obj = ServerConnector.getInstance().sendObject(output_obj);

        if(received_obj.getObject() instanceof ArrayList){
            return (ArrayList<Track>)received_obj.getObject();
        } else {
            System.out.println("Invalid Object");
            return null;
        }
    }

    private void addTrackToPlaylist(){
        this.user_playlists = requestPlaylists();
        int selected_row = musicPanel.getMusic_table().getSelectedRow();

        if(user_playlists.isEmpty()){
            mainWindow.showError("No hi ha llistes, clica \"New Playlist\" per crear una.");
        } else {
            if (selected_row >= 0) {
                ArrayList<String> options = new ArrayList<>();

                for (Playlist p:user_playlists) {
                    options.add(p.getName());
                }
                //demanem a quina llista la fiquem
                int selected_id = mainWindow.showOptionDialog("Escull una Llista","Afegir a Llista",options.toArray());

                int playlist_id = user_playlists.get(selected_id).getId();
                int track_id = tracklist.get(selected_row).getId();

                PlaylistTrack playlistTrack = new PlaylistTrack(playlist_id,track_id);

                ObjectMessage output_obj = new ObjectMessage(playlistTrack,"add_playlist_track");
                ObjectMessage received_obj = ServerConnector.getInstance().sendObject(output_obj);

            } else {
                mainWindow.showError("No s'ha seleccionat cap track.");
            }
        }
    }


    private ArrayList<Playlist> requestPlaylists(){
        //demanem al servidor la llista de playlists de l'usuari
        User session_user = Session.getInstance().getUser();
        ObjectMessage output_obj = new ObjectMessage(session_user,"request_playlists");
        ObjectMessage received_obj = ServerConnector.getInstance().sendObject(output_obj);

        if(received_obj.getObject() instanceof ArrayList){
            return (ArrayList<Playlist>)received_obj.getObject();
        } else {
            System.out.println("Invalid Object");
            return null;
        }
    }

}
