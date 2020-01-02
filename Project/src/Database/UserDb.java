package Database;

import Book.Book;
import User.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class UserDb {
    private static final String DATABASE_URL = "jdbc:derby:Database";
    public ObservableList<User> observableList= FXCollections.observableArrayList();
    public void saveUser(User User){
        Connection conn=null;

        try {
            conn = DriverManager.getConnection(DATABASE_URL);
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet_forStudent=statement.executeQuery("SELECT COUNT(*) FROM users");
            resultSet_forStudent.next();
            PreparedStatement prstmntStudent=conn.prepareStatement("INSERT INTO users(login, nameOfUser, surnameOfUser, password, typeOfUser, blockStatus) VALUES(?,?,?,?,?,?)");
            prstmntStudent.setString(1, User.getID());
            prstmntStudent.setString(2, User.getName());
            prstmntStudent.setString(3, User.getSurname());
            prstmntStudent.setString(4, User.getPassword());
            prstmntStudent.setString(5, User.getType());
            prstmntStudent.setString(6, "can");
            prstmntStudent.executeUpdate();
            System.out.print("DONE!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String type, String login, String password){
            User user = new User();
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet())
        {
            System.out.print("DONE!");
            rowSet.setUrl(DATABASE_URL);
            rowSet.setCommand("SELECT * FROM users WHERE typeOfUser = "+"'"+type+"'"+" AND login = "+"'"+login+"'"+" AND password = "+"'"+password+"'"); // set query
            rowSet.execute();
            if(rowSet.next()){
                user = new User(rowSet.getString("login"), rowSet.getString("nameOfUser"),
                        rowSet.getString("surnameOfUser"), rowSet.getString("password"), rowSet.getString("typeOfUser"), rowSet.getString("blockStatus"));
                return user;
            }

        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public boolean isLoginExist(String login){
        System.out.println("check "+login);
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet())
        {
            System.out.print("DONE!");
            rowSet.setUrl(DATABASE_URL);
            rowSet.setCommand("SELECT login FROM users "); // set query
            rowSet.execute();
            while(rowSet.next()){
                if(rowSet.getString("login").equals(login))
                    return true;
            }

        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public void show(String type)  {
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet())
        {
            System.out.print("DONE!");
            rowSet.setUrl(DATABASE_URL);
            rowSet.setCommand("SELECT * FROM users WHERE typeOfUser = "+"'"+type+"'"); // set query
            rowSet.execute();
            while (rowSet.next()){

                observableList.add(new User (rowSet.getString("login"), rowSet.getString("nameOfUser"),
                        rowSet.getString("surnameOfUser"), rowSet.getString("password"), rowSet.getString("typeOfUser"), rowSet.getString("blockStatus")));
                System.out.println();
            }
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
    }

    public boolean userIsFree(User user){
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet())
        {
        rowSet.setUrl(DATABASE_URL);
        rowSet.setCommand("SELECT * FROM borrowedBooks WHERE usersID = "+"'"+user.getID()+"'"); // set query
        rowSet.execute();
        while(rowSet.next())
             return false;
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
        return true;
    }

    public void setStatus(User user) throws SQLException {
        Connection conn ;
        conn = DriverManager.getConnection(DATABASE_URL);
        PreparedStatement prstmntISBN=conn.prepareStatement("UPDATE users SET blockStatus = '"+user.getBlockStatus()+"' WHERE login = '"+user.getID()+"'");
        prstmntISBN.executeUpdate();
    }

    public void delete(User user){
        try {
            Connection connection=DriverManager.getConnection(DATABASE_URL);
            Statement statement=connection.createStatement();
            System.out.print("DONE!");

            System.out.println();
            //System.out.println("index" + deletingIndex);
            //System.out.println("DELETE * FROM ISBN WHERE book_id =" + deletingIndex + " ");

            statement.executeUpdate("DELETE FROM users WHERE login ='" + user.getID() + "'"); // set query
            statement.close();
            connection.close();
        }
        catch (Exception e) {
            System.err.println("Exception: "+e.getMessage());
        }
    }
}
