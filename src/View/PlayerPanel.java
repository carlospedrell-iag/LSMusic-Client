package View;

import Controller.PlayerController;
import Model.ServerConnector;

import javax.swing.*;
import java.awt.*;

public class PlayerPanel {
    private JPanel main_panel;
    private JButton stopButton;
    private JButton playButton;
    private JButton previousButton;
    private JButton nextButton;
    private JProgressBar progressBar;
    private JLabel player_label;
    private JLabel progress_label;
    private JPanel status_panel;
    private JPanel controls;

    private JToggleButton repeat_list;
    private JToggleButton repeat_track;
    private JProgressBar downloadBar;

    public PlayerPanel() {
        Icon stopIcon = new ImageIcon("./resources/icons/stop.png");
        Icon playIcon = new ImageIcon("./resources/icons/play.png");
        Icon nextIcon = new ImageIcon("./resources/icons/next.png");
        Icon previousIcon = new ImageIcon("./resources/icons/previous.png");
        Icon repeatListIcon = new ImageIcon("./resources/icons/repeat_list.png");
        Icon repeatTrackIcon = new ImageIcon("./resources/icons/repeat_track.png");

        stopButton.setIcon(stopIcon);
        playButton.setIcon(playIcon);
        nextButton.setIcon(nextIcon);
        previousButton.setIcon(previousIcon);

        stopButton.setActionCommand("stop");
        playButton.setActionCommand("play");
        nextButton.setActionCommand("next");
        previousButton.setActionCommand("previous");

        status_panel.setLayout( new GridLayout(1,1));
        player_label = new JLabel();
        player_label.setText("DEFAULT");
        status_panel.add(player_label);

        downloadBar = new JProgressBar();
        downloadBar.setBorder(BorderFactory.createEmptyBorder(2,0,2,0));
        downloadBar.setStringPainted(true);
        downloadBar.setString("Downloading...");

        repeat_list = new JToggleButton();
        repeat_list.setIcon(repeatListIcon);

        repeat_track = new JToggleButton();
        repeat_track.setIcon(repeatTrackIcon);

        controls.add(repeat_list);
        controls.add(repeat_track);
    }

    public void setUpController(PlayerController controller) {
        stopButton.addActionListener(controller);
        playButton.addActionListener(controller);
        nextButton.addActionListener(controller);
        previousButton.addActionListener(controller);
        repeat_list.addItemListener(controller);
        repeat_track.addItemListener(controller);
    }

    public JPanel getMain_panel() {
        return main_panel;
    }

    public void setProgressBarMaximum(int maximum) {
        this.progressBar.setMaximum(maximum);
    }

    public void setProgressBarValue(int value) {
        this.progressBar.setValue(value);
    }

    public void setPlayer_label(String player_label) {
        this.player_label.setText(player_label);
    }

    public void showDownloadBar(){
        if(this.status_panel.getComponent(0) instanceof JLabel){
            this.status_panel.remove(0);
            this.status_panel.add(downloadBar);
        }
        downloadBar.setMaximum(ServerConnector.getInstance().getFile_length());
        downloadBar.setValue(ServerConnector.getInstance().getProgress());
    }

    public void showPlayerLabel(){
        if(this.status_panel.getComponent(0) instanceof JProgressBar){
            this.status_panel.remove(0);
            this.status_panel.add(player_label);
        }
    }

    public void setProgress_label(String progress_label) {
        this.progress_label.setText(progress_label);
    }

    private void createUIComponents() {
        GridLayout grid = new GridLayout(1,2);
        grid.setHgap(10);
        controls = new JPanel(grid);
        controls.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    }

    public JToggleButton getRepeat_list() {
        return repeat_list;
    }

    public JToggleButton getRepeat_track() {
        return repeat_track;
    }
}
