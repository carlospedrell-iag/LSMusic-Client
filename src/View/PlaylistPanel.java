package View;

import Controller.PlaylistController;
import Model.Entity.Playlist;
import Model.Entity.Track;
import Model.MusicPlayer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

public class PlaylistPanel {
    private JTabbedPane tabbedPane;
    private JPanel main_panel;
    private JButton newPlaylist_button;
    private JButton deletePlaylist_button;
    private JButton back_button;
    private JPanel backButtonPanel;
    private JPanel buttonPanel;
    private JMenuItem rateTrack;
    private JMenuItem deleteTrack;
    private JPopupMenu popupMenu;

    private String[] columnNames = {"Playing","Title", "Artist", "Album", "Genre", "Your Rating"};

    private String user_name = "";
    private LayoutManager defaultLayout;


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

        back_button = new JButton("Back");
        back_button.setActionCommand("back");

        defaultLayout = backButtonPanel.getLayout();

    }

    public void refreshPlaying(int queue_index){
        if(queue_index != -1){
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
    }

    public void resetPlaying(int queue_index){
        if(queue_index != -1){
            //Hem de extreure el Jtable de dins del Jscrollpane de dins del Jtabbedpane......
            JScrollPane scrollPane = (JScrollPane)tabbedPane.getComponentAt(queue_index);
            JTable table = (JTable)scrollPane.getViewport().getView();

            TableModel model = table.getModel();
            //actualitzem el simbol de playing en la taula
            for (int i = 0; i < model.getRowCount(); i++) {
                model.setValueAt("",i,0);
            }
            table.setModel(model);
            JViewport viewport = new JViewport();
            viewport.setView(table);
            scrollPane.setViewport(viewport);
        }
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
            //actualitzem la cua de reproducció a nova llista
            if(playlist_index == MusicPlayer.getInstance().getQueue_index()){
                MusicPlayer.getInstance().setQueuePlaylist(playlist);
            }

            playlist_index++;
        }
    }

    public void setUpController(PlaylistController controller){
        newPlaylist_button.addActionListener(controller);
        deletePlaylist_button.addActionListener(controller);
        rateTrack.addActionListener(controller);
        deleteTrack.addActionListener(controller);
        back_button.addActionListener(controller);
    }

    public void setFollowedPlaylistMode(){
        String title;
        //adapta el titol segons el nom acaba en s o no (Perque no surtin coses com Carlos's Playlists)
        if(user_name.substring(user_name.length() -1).equals("s")){
            title = "   " +user_name + "' Playlists";
        } else {
            title = "   " +user_name + "'s Playlists";
        }

        JLabel title_label = new JLabel(title);
        Font font = title_label.getFont();
        title_label.setFont(new Font(font.getName(),Font.PLAIN,16));


        buttonPanel.removeAll();
        backButtonPanel.removeAll();
        backButtonPanel.setLayout(new BorderLayout());
        backButtonPanel.add(back_button,BorderLayout.WEST);
        backButtonPanel.add(title_label);
    }

    public void setUserMode(){
        backButtonPanel.removeAll();
        JLabel title_label = new JLabel("Your Playlists");
        Font font = title_label.getFont();
        title_label.setFont(new Font(font.getName(),Font.PLAIN,16));

        backButtonPanel.add(title_label);
        GridLayout grid = new GridLayout(1,2);
        grid.setHgap(5);
        buttonPanel.setLayout(grid);

        buttonPanel.add(newPlaylist_button);
        buttonPanel.add(deletePlaylist_button);
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

    private void createUIComponents() {
        backButtonPanel = new JPanel(new GridLayout(1,1));
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
