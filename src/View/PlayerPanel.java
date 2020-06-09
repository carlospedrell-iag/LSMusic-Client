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

    public PlayerPanel(){
        stopButton.setActionCommand("stop");
        playButton.setActionCommand("play");

    }

    public void setUpController(PlayerController controller){
        stopButton.addActionListener(controller);
        playButton.addActionListener(controller);
    }

    public JPanel getMain_panel() {
        return main_panel;
    }

    public void setProgressBarMaximum(int maximum){
        this.progressBar.setMaximum(maximum);
    }

    public void setProgressBarValue(int value){
        this.progressBar.setValue(value);

    }
}
