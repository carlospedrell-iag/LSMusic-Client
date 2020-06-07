package Controller;

import Model.Entity.ObjectMessage;
import Model.Entity.Playlist;
import Model.Entity.Session;
import Model.Entity.User;
import Model.ServerConnector;
import View.MainWindow;
import View.PlaylistPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlaylistController implements ActionListener {
    private MainWindow mainWindow;
    private PlaylistPanel playlistPanel;

    private ArrayList<Playlist> user_playlists;

    public PlaylistController(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        this.playlistPanel = mainWindow.getHomePanel().getPlaylistPanel();


        updatePlaylists();
    }

    public void updatePlaylists(){
        try{
            //recull info d'user de la DB i la envia a la vista per refrescar la taula
            this.user_playlists = requestPlaylists();
            playlistPanel.refreshPlaylists(user_playlists);
            mainWindow.revalidate();
            System.out.println("Playlists actualitzades");
        } catch (Exception e){
            mainWindow.showError("Error al connectar al servidor");
            mainWindow.revalidate();
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

    private void newPlaylist(String name){
        //Creem una nova playlist i l'enviem al servidor per emmagatzemar
        int user_id = Session.getInstance().getUser().getId();
        Playlist playlist = new Playlist(name, user_id);

        ObjectMessage output_obj = new ObjectMessage(playlist,"new_playlist");
        ObjectMessage received_obj = ServerConnector.getInstance().sendObject(output_obj);

        //si retorna errors els mostrem per GUI
        if(!received_obj.getErrors().isEmpty()){
            mainWindow.showError(received_obj.getFormattedErrors());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "new_playlist":
                String name = mainWindow.showInputDialog("Nom de la playlist:","New Playlist");
                newPlaylist(name);
                updatePlaylists();
                break;
        }
    }
}
