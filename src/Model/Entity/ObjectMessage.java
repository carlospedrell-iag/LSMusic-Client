package Model.Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class ObjectMessage implements Serializable {
    private Object object;
    private String message;
    private ArrayList<String> errors;

    public ObjectMessage(){
        this.errors = new ArrayList<>();
    }

    public ObjectMessage(Object object, String type){
        this.object = object;
        this.message = type;
        this.errors = new ArrayList<>();
    }

    public void printErrors(){
        if(!errors.isEmpty()){
            System.out.println("Error/s enviats a client:");
        }
        for(String error: errors){
            System.out.println("    -" + error);
        }
    }

    public Object getObject() {
        return object;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<String> getErrors(){
        return errors;
    }

    public String getFormattedErrors(){
        //retorna una cadena formatejada en HTML amb tots els errors, per mostrar correctament per la GUI
        String string = "<html>";
        for(String error: errors){
            string += "<br>" + error;
        }
        string += "</html>";

        return string;
    }

    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }

    public void addError(String error){
        this.errors.add(error);
    }
}
