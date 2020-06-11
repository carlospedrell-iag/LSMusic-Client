package Controller;

import Model.MusicPlayer;
import View.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowController implements ActionListener {

    private MainWindow mainWindow;

    public WindowController(MainWindow mainWindow){
        MusicPlayer.getInstance().setWindowController(this);

        this.mainWindow = mainWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainWindow.switchPanel(e.getActionCommand());
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }
}
