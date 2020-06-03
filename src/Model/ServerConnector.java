package Model;

import Model.Entity.ObjectMessage;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;

public class ServerConnector {
    private Socket client_socket;

    private int client_port;
    private String ip;

    public ServerConnector(){
        setConfig();
    }

    public void registerUser(ObjectMessage user_om){

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
