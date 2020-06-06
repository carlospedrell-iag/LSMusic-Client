package View;

import Controller.PlaylistController;
import Model.Entity.Playlist;
import Model.Entity.Track;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PlaylistPanel {
    private JTabbedPane tabbedPane;
    private JPanel main_panel;
    private JButton newPlaylist_button;

    private String[] columnNames = {"Title", "Artist", "Album", "Genre", "Plays", "Rating"};

    public PlaylistPanel(){

    }

    public void refreshPlaylists(ArrayList<Playlist> playlists){
        tabbedPane.removeAll();

        for(Playlist playlist:playlists){

            JTable table = new JTable();
            DefaultTableModel model = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            model.setColumnIdentifiers(columnNames);

            for(Track track: playlist.getTracks()){
                String rating;
                //formatejem la puntuació
                if(track.getRating() == -1){
                    rating = "Not Rated Yet";
                } else {
                    rating = track.getRating() + "★";
                }
                model.addRow(new Object[]{
                        track.getTitle(),
                        track.getArtist(),
                        track.getAlbum(),
                        track.getGenre(),
                        track.getPlays(),
                        rating
                });
            }
            table.setModel(model);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.getTableHeader().setReorderingAllowed(false);
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.getViewport().add(table);

            tabbedPane.add(playlist.getName(),scrollPane);
        }
    }

    public void setUpController(PlaylistController controller){

    }

    public JPanel getMain_panel() {
        return main_panel;
    }
}
