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
    private int progress;
    private int file_length;
    private String ip;

    private static ServerConnector instance;

    private static final String CACHE_PATH = "./music-cache/track_id";

    public ServerConnector(){
        setConfig();
    }

    public static ServerConnector getInstance() {
        if (instance == null){
            instance = new ServerConnector();
        }
        return instance;
    }

    public synchronized ObjectMessage sendObject(ObjectMessage om){
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

    public synchronized void requestFile(Track track){
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
            this.file_length = ois.readInt();
            System.out.println("File size: " + this.file_length);

            //input llegeix del socket, output escriu al fitxer del client
            InputStream in = socket.getInputStream();
            OutputStream out = new FileOutputStream(path);

            byte[] buffer = new byte[4096 * 4];

            int count;
            this.progress = 0;

            while ((count = in.read(buffer)) > 0)
            {
                //escriu en el fitxer en buffer
                out.write(buffer, 0, count);
                this.progress += count;
            }

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

    public int getFile_length() {
        return file_length;
    }

    public int getProgress() {
        return progress;
    }
}
