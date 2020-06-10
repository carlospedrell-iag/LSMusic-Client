package View;

import Controller.PlayerController;

import javax.swing.*;

public class PlayerPanel {
    private JPanel main_panel;
    private JButton stopButton;
    private JButton playButton;
    private JButton previousButton;
    private JButton nextButton;
    private JProgressBar progressBar;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JLabel player_label;
    private JLabel progress_label;

    public PlayerPanel() {
        Icon stopIcon = new ImageIcon("./resources/icons/stop.png");
        Icon playIcon = new ImageIcon("./resources/icons/play.png");
        Icon nextIcon = new ImageIcon("./resources/icons/next.png");
        Icon previousIcon = new ImageIcon("./resources/icons/previous.png");

        stopButton.setIcon(stopIcon);
        playButton.setIcon(playIcon);
        nextButton.setIcon(nextIcon);
        previousButton.setIcon(previousIcon);

        stopButton.setActionCommand("stop");
        playButton.setActionCommand("play");
        nextButton.setActionCommand("next");
        previousButton.setActionCommand("previous");
    }

    public void setUpController(PlayerController controller) {
        stopButton.addActionListener(controller);
        playButton.addActionListener(controller);
        nextButton.addActionListener(controller);
        previousButton.addActionListener(controller);
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

    public void setProgress_label(String progress_label) {
        this.progress_label.setText(progress_label);
    }
}
