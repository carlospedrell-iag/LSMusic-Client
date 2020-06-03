package Model.Entity;

import java.time.LocalDateTime;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime created_at;
    private LocalDateTime last_access;

    private String form_password;

    public User(int id, String name, String email, String password, LocalDateTime created_at, LocalDateTime last_access){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.created_at = created_at;
        this.last_access = last_access;
    }

    public User(String name, String email, String password){
        //aquest constructor s'utilitza per crear un nou usuari
        this.name = name;
        this.email = email;
        this.password = password;
        this.created_at = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getLast_access() {
        return last_access;
    }

    public void setLast_access(LocalDateTime last_access) {
        this.last_access = last_access;
    }

    public String getForm_password() {
        return form_password;
    }

    public void setForm_password(String form_password) {
        this.form_password = form_password;
    }
}
