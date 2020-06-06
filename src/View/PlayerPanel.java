package View;

import Controller.PlayerController;

import javax.swing.*;

public class PlayerPanel {
    private JPanel main_panel;
    private JSlider slider1;
    private JButton button1;
    private JButton button2;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;

    public PlayerPanel(){

    }

    public void setUpController(PlayerController controller){

    }

    public JPanel getMain_panel() {
        return main_panel;
    }
}
