package View;

import Controller.MusicController;
import Model.Entity.Track;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MusicPanel {
    private JPanel main_panel;
    private JTable music_table;
    private JMenuItem addTrack;

    private String[] columnNames = {"Title", "Artist", "Album", "Genre", "Play Count", "Avg Rating"};

    public MusicPanel(){
        music_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        music_table.getTableHeader().setReorderingAllowed(false);
        music_table.getTableHeader().setEnabled(false);
        //pop-up menu per afegir tracks a la llista
        JPopupMenu popupMenu = new JPopupMenu();
        addTrack = new JMenuItem("Afegir a Llista");
        addTrack.setActionCommand("add_track");
        popupMenu.add(addTrack);
        music_table.setComponentPopupMenu(popupMenu);

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
                rating = " ---";
            } else {
                rating = "["+String.format("%.1f", track.getRating())+"] " + track.getStarRating();
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
        addTrack.addActionListener(controller);
        music_table.addMouseListener(controller);
    }

    public JPanel getMain_panel() {
        return main_panel;
    }

    public JTable getMusic_table() {
        return music_table;
    }
}
