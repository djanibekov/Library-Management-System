package User;

import Database.ISBNdb;
import Database.UserDb;

import java.sql.SQLException;

public class User {
    private String ID, name, surname, password;
    private boolean loggedIn;
    private String type;
    private String blockStatus;
    public User() {
    }

    public User(String ID, String name, String surname, String password, String type, String blockStatus) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.type = type;
        this.blockStatus = blockStatus;
    }

    public String getBlockStatus() {
        return blockStatus;
    }

    public void setBlockStatus(String blockStatus) throws SQLException {
        this.blockStatus = blockStatus;
        UserDb userdb = new UserDb();
        userdb.setStatus(this);
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name){this.name=name;}

    public void setPassword(String password){this.password=password;}

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getID() {  return ID;  }

    public String getSurname() {return surname; }

    public String getName(){return name;}

    public String getPassword(){return password;}

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void Login(){
            loggedIn =  true;
    }

    public void Logout(){
        loggedIn = false;
    }

    static public User getUser(String type, String login, String password){
        UserDb user=new UserDb();
        return user.getUser(type,login,password);
    }

    public void save(){
        UserDb db=new UserDb();
        db.saveUser(this);
    }

    public void delete(){
        UserDb db=new UserDb();
        db.delete(this);
    }

    public boolean isFree(){
        UserDb db=new UserDb();
        if(db.userIsFree(this))
            return  true;
        else
            return false;
    }

}
