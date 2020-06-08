package Controller;

import Model.Entity.*;
import Model.ServerConnector;
import View.MainWindow;
import View.PlaylistPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlaylistController implements ActionListener {
    private MainWindow mainWindow;
    private PlaylistPanel playlistPanel;
    private HomeController homeController;

    private ArrayList<Playlist> user_playlists;

    public PlaylistController(MainWindow mainWindow, HomeController homeController){
        this.mainWindow = mainWindow;
        this.playlistPanel = mainWindow.getHomePanel().getPlaylistPanel();
        this.homeController = homeController;

        updatePlaylists();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "new_playlist":
                String name = mainWindow.showInputDialog("Nom de la playlist:","New Playlist");
                if(name != null){
                    if(name.isBlank()){
                        mainWindow.showError("El nom de la llista no pot estar buit.");
                    }
                    if(playlistExists(name)){
                        mainWindow.showError("Aquesta llista ja existeix en la teva biblioteca.");
                    }
                    if(!name.isBlank() && !playlistExists(name)){
                        newPlaylist(name);
                        updatePlaylists();
                    }
                }
                break;
            case "delete_playlist":
                deletePlaylist();
                homeController.refreshAll();
                break;
            case "rate_track":
                rateTrack();
                homeController.refreshAll();
                break;
            case "delete_track":
                deleteTrack();
                homeController.refreshAll();
        }
    }

    public void updatePlaylists(){
        //TODO: valor del float rating es muy grande 3.333333 (en musiccontroller), covnertir a double
        //ens guardem l'index de la tab activa
        int tab_index = playlistPanel.getTabbedPane().getSelectedIndex();
        if(tab_index == -1){ tab_index = 0; }

        try{
            //recull info d'user de la DB i la envia a la vista per refrescar la taula
            this.user_playlists = requestPlaylists();
            playlistPanel.refreshPlaylists(user_playlists);

            if(!user_playlists.isEmpty()) {
                //tornem a posar l'indez de la tab activa
                JTabbedPane tabbedPane = playlistPanel.getTabbedPane();

                if(tab_index > user_playlists.size() + 1){
                    tab_index = user_playlists.size() + 1;
                }
                //TODO: Problemas al eliminar playlists
                tabbedPane.setSelectedIndex(tab_index);
                playlistPanel.setTabbedPane(tabbedPane);

                mainWindow.revalidate();
                System.out.println("Playlists actualitzades");
            }
        } catch (Exception e){
            e.printStackTrace();
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

    private void deletePlaylist(){
        if(!user_playlists.isEmpty()){
            ArrayList<String> options = new ArrayList<>();

            for (Playlist p:user_playlists) {
                options.add(p.getName());
            }

            String option = mainWindow.showOptionDialog("Escull una Llista","Afegir a Llista",options.toArray());
            int selected_id = options.indexOf(option);

            if(selected_id != -1){
                Playlist playlist = user_playlists.get(selected_id);

                ObjectMessage output_obj = new ObjectMessage(playlist,"delete_playlist");
                ObjectMessage received_obj = ServerConnector.getInstance().sendObject(output_obj);
            }
        }
    }

    private void rateTrack(){
        int playlist_index = playlistPanel.getTabbedPane().getSelectedIndex();
        int track_index = getSelectedTrackIndex();

        if(track_index != -1){
            int rating = mainWindow.showRateDialog("Puntuació","Valorar");
            if(rating != -1){
                int playlist_id = getAbsolutePlaylistId(playlist_index);
                int track_id = getAbsoluteTrackId(playlist_index,track_index);

                PlaylistTrack playlistTrack = new PlaylistTrack(playlist_id,track_id,rating);

                System.out.println("Playlist id: " + playlist_id + " , Track_id: " + track_id + " , Rating: " + rating);

                ObjectMessage output_obj = new ObjectMessage(playlistTrack,"rate_track");
                ObjectMessage received_obj = ServerConnector.getInstance().sendObject(output_obj);
            }
        } else {
            mainWindow.showError("No s'ha seleccionat cap track.");
        }
    }

    private void deleteTrack(){
        int playlist_index = playlistPanel.getTabbedPane().getSelectedIndex();
        int track_index = getSelectedTrackIndex();

        if(track_index != -1){

            int playlist_id = getAbsolutePlaylistId(playlist_index);
            int track_id = getAbsoluteTrackId(playlist_index,track_index);

            Track track = user_playlists.get(playlist_index).getTracks().get(track_index);
            PlaylistTrack playlistTrack = new PlaylistTrack(playlist_id,track_id);
            playlistTrack.setId(track.getPlaylist_track_id());

            ObjectMessage output_obj = new ObjectMessage(playlistTrack,"delete_playlist_track");
            ObjectMessage received_obj = ServerConnector.getInstance().sendObject(output_obj);

        } else {
            mainWindow.showError("No s'ha seleccionat cap track.");
        }
    }

    private int getAbsolutePlaylistId(int id){
        //Ens retorna el id absolut de la playlist en la base de dades (no el id de la playlist en la interficie, que seria el relatiu)
        return user_playlists.get(id).getId();
    }

    private int getAbsoluteTrackId(int id_playlist, int id_track){
        //Ens retorna el id absolut del track en la base de dades (no el id del track en la interficie, que seria el relatiu)
        return user_playlists.get(id_playlist).getTracks().get(id_track).getId();
    }

    private int getSelectedTrackIndex(){
        int playlist_index = playlistPanel.getTabbedPane().getSelectedIndex();
        //Hem de extreure el Jtable de dins del Jscrollpane de dins del Jtabbedpane......
        JScrollPane scrollPane = (JScrollPane)playlistPanel.getTabbedPane().getComponentAt(playlist_index);
        JTable table = (JTable)scrollPane.getViewport().getView();
        //Extreiem el track seleccionat en la UI
        return table.getSelectedRow();
    }

    private Boolean playlistExists(String name){
        Boolean exists = false;

        for( Playlist p:user_playlists){
            if(p.getName().equals(name)){
                exists = true;
            }
        }

        return exists;
    }
}
