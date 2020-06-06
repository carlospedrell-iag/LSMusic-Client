package Controller;

import Model.Entity.ObjectMessage;
import Model.Entity.Track;
import Model.ServerConnector;
import View.MainWindow;
import View.MusicPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MusicController implements ActionListener {

    private MainWindow mainWindow;
    private MusicPanel musicPanel;

    public MusicController(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        this.musicPanel = mainWindow.getMusicPanel();

        updateTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private void updateTable(){
        try{
            //recull info d'user de la DB i la envia a la vista per refrescar la taula
            musicPanel.refreshTable(requestTrackList());
            mainWindow.revalidate();
            System.out.println("Taula musica actualitzada");
        } catch (Exception e){
            mainWindow.showError("Error al connectar al servidor");
            mainWindow.revalidate();
        }
    }

    private ArrayList<Track> requestTrackList(){
        //demanem al servidor la llista de cançons del sistema
        ObjectMessage output_obj = new ObjectMessage(null,"request_tracklist");
        ObjectMessage received_obj = ServerConnector.getInstance().sendObject(output_obj);

        if(received_obj.getObject() instanceof ArrayList){
            return (ArrayList<Track>)received_obj.getObject();
        } else {
            System.out.println("Invalid Object");
            return null;
        }
    }
}
