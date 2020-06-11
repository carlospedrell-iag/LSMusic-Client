package View;

import Controller.HomeController;
import Controller.MusicController;
import Controller.PlayerController;
import Controller.PlaylistController;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;
    private HomePanel homePanel;


    public MainWindow() {
        //settings principals
        setSize(1460, 800);
        setTitle("LaSalleMusic");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setMinimumSize(new Dimension(880, 500));
        //initzialitzem tots els panels
        loginPanel = new LoginPanel();
        registerPanel = new RegisterPanel();
        //el primer panel per default es el de login
        setContentPane(loginPanel.getMain_panel());

    }

    public void switchPanel(String panel_name) {
        //aquesta funcio es crida quan es necessita canviar a un altre panel segons el String rebut
        switch (panel_name) {
            case "register":
                setContentPane(registerPanel.getMain_panel());
                revalidate();
                break;
            case "login":
                setContentPane(loginPanel.getMain_panel());
                revalidate();
                break;

            case "home":
                this.homePanel = new HomePanel();
                homePanel.setUpController(new HomeController(this));
                setContentPane(homePanel.getMainPanel());
                revalidate();
                break;
        }
    }


    public void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public int showConfirmMessage(String message) {
        return JOptionPane.showConfirmDialog(null, message, "Warning", JOptionPane.YES_NO_OPTION);
    }

    public String showInputDialog(String message, String title) {
        return JOptionPane.showInputDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    public String showOptionDialog(String message, String title, Object[] options) {
        return (String) JOptionPane.showInputDialog(null, message, title, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }

    public int showRateDialog(String message, String title) {
        String[] stars = {
                "★",
                "★★",
                "★★★",
                "★★★★",
                "★★★★★",
        };
        String s = (String) JOptionPane.showInputDialog(null, message, title, JOptionPane.PLAIN_MESSAGE, null, stars, stars[0]);
        return s.length();
    }

    public LoginPanel getLoginPanel() {
        return loginPanel;
    }

    public RegisterPanel getRegisterPanel() {
        return registerPanel;
    }

    public HomePanel getHomePanel() {
        return homePanel;
    }

    public void disableWindow(){
        this.setEnabled(false);
    }

    public void enableWindow(){
        this.setEnabled(true);
    }


}
