package Model;

import Model.Entity.ObjectMessage;
import Model.Entity.User;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerConnector {
    private Socket client_socket;

    private int client_port;
    private String ip;

    public ServerConnector(){
        setConfig();
    }

    public ObjectMessage sendObject(ObjectMessage om){
        ObjectMessage input_obj = new ObjectMessage();

        try{
            client_socket = new Socket(ip,client_port);

            ObjectOutputStream oos = new ObjectOutputStream(client_socket.getOutputStream());
            oos.writeObject(om);

            ObjectInputStream ois = new ObjectInputStream(client_socket.getInputStream());
            input_obj = (ObjectMessage)ois.readObject();

        } catch (IOException | ClassNotFoundException e){
            System.out.println("Hi ha hagut un error al connectar amb el servidor");
        }

        return input_obj;
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
