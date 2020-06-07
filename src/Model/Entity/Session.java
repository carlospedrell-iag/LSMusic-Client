package Model.Entity;

public class Session {
    private User user;
    private static Session instance;

    public Session(){}

    public static Session getInstance(){
        if(instance == null){
            instance = new Session();
        }
        return instance;
    }

    public void setUser(User user) {
        this.user = user;
        System.out.println("Sessio iniciada User: " + user.getName());
    }

    public User getUser() {
        return user;
    }
}
