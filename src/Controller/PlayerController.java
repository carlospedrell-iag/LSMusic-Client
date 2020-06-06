package Controller;

import View.MainWindow;
import View.PlayerPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerController implements ActionListener {

    private MainWindow mainWindow;
    private PlayerPanel playerPanel;


    public PlayerController(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        this.playerPanel = mainWindow.getPlayerPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
