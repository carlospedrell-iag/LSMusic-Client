package View;

import Controller.HomeController;
import Controller.MusicController;
import Controller.PlayerController;
import Controller.PlaylistController;
import Model.Entity.Playlist;

import javax.swing.*;
import java.awt.*;

public class HomePanel {

    private JPanel mainPanel;

    private MusicPanel musicPanel;
    private PlaylistPanel playlistPanel;
    private PlayerPanel playerPanel;

    private JButton signout_button;
    private JButton refresh_button;


    public HomePanel(){

        this.musicPanel = new MusicPanel();
        this.playlistPanel = new PlaylistPanel();
        this.playerPanel = new PlayerPanel();

        mainPanel = new JPanel(new BorderLayout());
        JPanel container = new JPanel(new GridLayout(1,1));

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);


        signout_button = new JButton("Sign Out");
        refresh_button = new JButton("Refresh");

        signout_button.setActionCommand("sign_out");
        refresh_button.setActionCommand("refresh");

        toolBar.add(refresh_button);
        toolBar.add(signout_button);

        this.musicPanel = musicPanel;
        this.playlistPanel = playlistPanel;
        this.playerPanel = playerPanel;

        JSplitPane topPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.musicPanel.getMain_panel(), this.playlistPanel.getMain_panel());


        container.add(topPanel);

        mainPanel.add(toolBar,BorderLayout.NORTH);
        mainPanel.add(container);
        mainPanel.add(playerPanel.getMain_panel(), BorderLayout.SOUTH);


    }

    public void setUpController(HomeController controller){
        signout_button.addActionListener(controller);
        refresh_button.addActionListener(controller);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public MusicPanel getMusicPanel() {
        return musicPanel;
    }

    public PlaylistPanel getPlaylistPanel() {
        return playlistPanel;
    }

    public PlayerPanel getPlayerPanel() {
        return playerPanel;
    }
}
