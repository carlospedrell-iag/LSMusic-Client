package View;

import Controller.HomeController;
import Controller.MusicController;
import Controller.PlayerController;
import Controller.PlaylistController;
import Model.Entity.Playlist;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import java.awt.*;

public class HomePanel {

    private JPanel mainPanel;

    private MusicPanel musicPanel;
    private PlaylistPanel playlistPanel;
    private PlayerPanel playerPanel;
    private UserPanel userPanel;

    private JButton signout_button;
    private JButton refresh_button;


    public HomePanel(){

        this.musicPanel = new MusicPanel();
        this.playlistPanel = new PlaylistPanel();
        this.playerPanel = new PlayerPanel();
        this.userPanel = new UserPanel();

        mainPanel = new JPanel(new BorderLayout());

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        signout_button = new JButton("Sign Out");
        refresh_button = new JButton("Refresh");
        signout_button.setActionCommand("sign_out");
        refresh_button.setActionCommand("refresh");

        toolBar.add(refresh_button);
        toolBar.add(signout_button);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.musicPanel.getMain_panel(), this.playlistPanel.getMain_panel());
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerSize(10);
        splitPane.setBackground(new Color(44,47,49));
        splitPane.setDividerLocation(730);

        JPanel musicContainer = new JPanel(new BorderLayout());
        musicContainer.add(splitPane);
        musicContainer.add(playerPanel.getMain_panel(),BorderLayout.SOUTH);

        JPanel mainContainer = new JPanel( new BorderLayout());
        mainContainer.add(musicContainer);
        mainContainer.add(userPanel.getMain_panel(),BorderLayout.EAST);

        mainPanel.add(toolBar,BorderLayout.NORTH);
        mainPanel.add(mainContainer);
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

    public UserPanel getUserPanel() {
        return userPanel;
    }
}
