package View;

import Controller.MusicController;
import Model.Entity.Track;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class MusicPanel {
    private JPanel main_panel;
    private JTable music_table;

    private String[] columnNames = {"Title", "Artist", "Album", "Genre", "Play Count", "Rating"};

    public MusicPanel(){
        music_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        music_table.getTableHeader().setReorderingAllowed(false);
    }

    public void refreshTable(ArrayList<Track> tracks){
        //fem un petit i rapid override de la funcio isCellEditable de DefaultTableModel per desactivar l'edicio de camps
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.setColumnIdentifiers(columnNames);

        for(Track track: tracks){
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

        music_table.setModel(model);
    }

    public void setUpController(MusicController controller){

    }

    public JPanel getMain_panel() {
        return main_panel;
    }

}
