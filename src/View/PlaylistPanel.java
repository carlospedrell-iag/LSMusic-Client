package View;

import Controller.PlaylistController;
import Model.Entity.Playlist;
import Model.Entity.Track;
import Model.MusicPlayer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;

public class PlaylistPanel {
    private JTabbedPane tabbedPane;
    private JPanel main_panel;
    private JButton newPlaylist_button;
    private JButton deletePlaylist_button;
    private JMenuItem rateTrack;
    private JMenuItem deleteTrack;
    private JPopupMenu popupMenu;

    private String[] columnNames = {"Playing","Title", "Artist", "Album", "Genre", "Plays", "Your Rating"};


    public PlaylistPanel(){
        newPlaylist_button.setActionCommand("new_playlist");
        deletePlaylist_button.setActionCommand("delete_playlist");

        //pop-up menu per puntuar la canço
        popupMenu = new JPopupMenu();
        rateTrack = new JMenuItem("Puntuar");
        rateTrack.setActionCommand("rate_track");
        deleteTrack = new JMenuItem("Eliminar Track");
        deleteTrack.setActionCommand("delete_track");
        popupMenu.add(rateTrack);
        popupMenu.add(deleteTrack);

    }

    public void refreshPlaying(int queue_index){
        //Hem de extreure el Jtable de dins del Jscrollpane de dins del Jtabbedpane......
        JScrollPane scrollPane = (JScrollPane)tabbedPane.getComponentAt(queue_index);
        JTable table = (JTable)scrollPane.getViewport().getView();

        TableModel model = table.getModel();
        //actualitzem el simbol de playing en la taula
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(isPlaying(queue_index,i),i,0);
        }
        table.setModel(model);
        JViewport viewport = new JViewport();
        viewport.setView(table);
        scrollPane.setViewport(viewport);
    }


    public void refreshPlaylists(ArrayList<Playlist> playlists){
        tabbedPane.removeAll();

        int playlist_index = 0;
        for (Playlist playlist : playlists) {


            JTable table = new JTable();
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            model.setColumnIdentifiers(columnNames);

            int queue_index = 0;
            for (Track track : playlist.getTracks()) {

                String rating;
                //formatejem la puntuació
                if (track.getRating() == -1) {
                    rating = " -  -  -  -  -";
                } else {
                    rating = track.getStarRating();
                }
                model.addRow(new Object[]{
                        isPlaying(playlist_index,queue_index),
                        track.getTitle(),
                        track.getArtist(),
                        track.getAlbum(),
                        track.getGenre(),
                        track.getPlays(),
                        rating
                });

                queue_index++;
            }

            table.setModel(model);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.getTableHeader().setReorderingAllowed(false);
            table.setComponentPopupMenu(popupMenu);

            //Serveix per centrar el text ( el simbol ▶) en la columna playing
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment( JLabel.CENTER );
            table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );



            JScrollPane scrollPane = new JScrollPane(table);

            tabbedPane.add(playlist.getName(), scrollPane);

            playlist_index++;
        }
    }

    public void setUpController(PlaylistController controller){
        newPlaylist_button.addActionListener(controller);
        deletePlaylist_button.addActionListener(controller);
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

    private String isPlaying(int queue_index, int track_index){
        if(MusicPlayer.getInstance().getQueue_index() == queue_index && MusicPlayer.getInstance().getTrack_index() == track_index){
            return "▶";
        } else {
            return "";
        }
    }
}
