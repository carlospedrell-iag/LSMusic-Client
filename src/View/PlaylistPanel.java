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
    private JMenuItem rateTrack;
    private JMenuItem deleteTrack;
    private JPopupMenu popupMenu;

    private String[] columnNames = {"Title", "Artist", "Album", "Genre", "Plays", "Your Rating"};


    public PlaylistPanel(){
        newPlaylist_button.setActionCommand("new_playlist");

        //pop-up menu per puntuar la canço
        popupMenu = new JPopupMenu();
        rateTrack = new JMenuItem("Valorar");
        rateTrack.setActionCommand("rate_track");
        deleteTrack = new JMenuItem("Eliminar Track");
        deleteTrack.setActionCommand("delete_track");
        popupMenu.add(rateTrack);
        popupMenu.add(deleteTrack);

    }

    public void refreshPlaylists(ArrayList<Playlist> playlists){
        tabbedPane.removeAll();

        for (Playlist playlist : playlists) {

            JTable table = new JTable();
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            model.setColumnIdentifiers(columnNames);

            for (Track track : playlist.getTracks()) {
                String rating;
                //formatejem la puntuació
                if (track.getRating() == -1) {
                    rating = " -  -  -  -  -";
                } else {
                    rating = track.getStarRating();
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
            table.setComponentPopupMenu(popupMenu);

            JScrollPane scrollPane = new JScrollPane();
            scrollPane.getViewport().add(table);

            tabbedPane.add(playlist.getName(), scrollPane);
        }
    }

    public void setUpController(PlaylistController controller){
        newPlaylist_button.addActionListener(controller);
        rateTrack.addActionListener(controller);
        deleteTrack.addActionListener(controller);
    }

    public JPanel getMain_panel() {
        return main_panel;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public void setTabbedPane(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
    }
}
