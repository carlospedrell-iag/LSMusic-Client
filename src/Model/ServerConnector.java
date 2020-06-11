package Model;

import Controller.WindowController;
import Model.Entity.ObjectMessage;
import Model.Entity.Track;
import View.MainWindow;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class ServerConnector {
    private Socket socket;

    private int client_port;
    private String ip;

    private static final String CACHE_PATH = "./music-cache/track_id";

    public ServerConnector(){
        setConfig();
    }

    public ObjectMessage sendObject(ObjectMessage om){
        ObjectMessage input_obj = new ObjectMessage();

        try{
            socket = new Socket(ip,client_port);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(om);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            input_obj = (ObjectMessage)ois.readObject();

        } catch (IOException | ClassNotFoundException e){
            System.out.println("Hi ha hagut un error al connectar amb el servidor");
            input_obj.addError("Hi ha hagut un error al connectar amb el servidor");
        }

        return input_obj;
    }

    public void requestFile(Track track, WindowController windowController){

        JPanel panel = new JPanel(new GridLayout(3,1));
        JLabel label = new JLabel("Downloading track:");
        JLabel track_label = new JLabel(track.getTitle());

        JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        panel.add(label);
        panel.add(track_label);
        panel.add(progressBar);
        panel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));

        JDialog dialog = new JDialog();
        dialog.setSize(300,130);
        dialog.setTitle("Downloading");

        dialog.setContentPane(panel);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);


        dialog.setResizable(false);

        dialog.setLocationRelativeTo(null);
        dialog.toFront();
        dialog.setVisible(true);
        windowController.getMainWindow().setEnabled(false);

        ObjectMessage om = new ObjectMessage(track,"request_file");
        String path = CACHE_PATH + track.getId() + getFileExtension(track.getPath());


        try{
            socket = new Socket(ip,client_port);
            //enviem el track al server
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(om);
            oos.flush();

            //esperem rebre el file size
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            int file_length;
            file_length = ois.readInt();


            System.out.println("File size: " + file_length);
            progressBar.setMaximum(file_length);

            //rebem l'arxiu
            InputStream in = socket.getInputStream();
            OutputStream out = new FileOutputStream(path);

            byte[] buffer = new byte[4096 * 4];

            int count;
            int totalRead = 0;

            while ((count = in.read(buffer)) > 0)
            {
                //escriu en el fitxer
                out.write(buffer, 0, count);
                totalRead += count;
                progressBar.setValue(totalRead);
            }

            dialog.setVisible(false);

            windowController.getMainWindow().setEnabled(true);
            windowController.getMainWindow().toFront();

            out.close();
            in.close();
            socket.close();

        } catch (Exception e){
            System.out.println("Hi ha hagut un error al connectar amb el servidor");
            e.printStackTrace();
        }

    }



    private String getFileExtension(String path) {
        int lastIndexOf = path.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return path.substring(lastIndexOf);
    }

    private void setConfig(){
        final String config_path = "./config.json";

        try {
            BufferedReader br = new BufferedReader(new FileReader(config_path));
            JsonElement jelement = new JsonParser().parse(br);
            JsonObject jobject = jelement.getAsJsonObject();

            this.client_port = jobject.get("client_port").getAsInt();
            this.ip = jobject.get("ip").getAsString();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
