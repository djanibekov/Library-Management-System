package Database;
import java.text.SimpleDateFormat;
import java.util.Date;
import Book.Book;
import Book.BorrowedBook;
import Book.ISBN;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
public class ISBNdb {
    private static final String DATABASE_URL = "jdbc:derby:Database";
    public void saveBookISBN(ISBN isbn, String globalISBN){
        Connection conn=null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement prstmntISBN=conn.prepareStatement("INSERT INTO ISBN(book_id, ISBN, Status) VALUES(?,?,?)");
            prstmntISBN.setString(1, globalISBN);
            prstmntISBN.setString(2, isbn.getISBN());
            prstmntISBN.setString(3, "F");
            prstmntISBN.executeUpdate();
            System.out.print("DONE!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ISBN> observableList= FXCollections.observableArrayList();



    public void show(String index)
    {
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet())
        {
            rowSet.setUrl(DATABASE_URL);
            System.out.println(index);
            rowSet.setCommand("SELECT * FROM ISBN WHERE book_id = '" + index + "'");// set query
            rowSet.execute();


            while (rowSet.next()){
                observableList.add(new ISBN(rowSet.getString("ISBN"), rowSet.getString("status"),
                        rowSet.getString("book_id")));
            }
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
    }

    public boolean isISBNExist(String ISBN){
        System.out.println("check "+ISBN);
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet())
        {
            System.out.print("DONE!");
            rowSet.setUrl(DATABASE_URL);
            rowSet.setCommand("SELECT ISBN FROM ISBN "); // set query
            rowSet.execute();
            while(rowSet.next()){
                if(rowSet.getString("ISBN").equals(ISBN)) {
                    return true;
                }
            }
            rowSet.close();
            return false;
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }

        return false;
    }


    public boolean isFreeIsbn(String ISBN) {

        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSet.setUrl(DATABASE_URL);
            rowSet.setCommand("SELECT status FROM ISBN WHERE ISBN = '" + ISBN + "'");// set query
            rowSet.execute();


            while (rowSet.next()) {
                if(rowSet.getString("status").equals("B")||rowSet.getString("status").equals("R"))
                    return false;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
        return true;
    }


    public void borrowBook(BorrowedBook book, boolean borrow){
        Connection conn;
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement statement;
            if(borrow) {
                statement = conn.prepareStatement("INSERT INTO borrowedBooks(usersID, isbnID, borrowedDate, expiredDate, feeForOneDay) VALUES(?,?,?,?,?)");
                statement.setString(1, book.getStudentID());
                statement.setString(2, book.getISBN());
                statement.setString(3, book.getBorrowDate());
                statement.setString(4, book.getExpiredDate());
                statement.setString(5, String.valueOf(book.getFeePerDay()));
            }
            else{
                statement = conn.prepareStatement("INSERT INTO borrowedBooks(usersID, isbnID) VALUES(?,?)");
                statement.setString(1, book.getStudentID());
                statement.setString(2, book.getISBN());
            }
            statement.executeUpdate();
            statement.close();
            PreparedStatement prstmntISBN;
            if(borrow)
                 prstmntISBN=conn.prepareStatement("UPDATE ISBN SET status = 'B' WHERE ISBN = '"+book.getISBN()+"'");
            else
                 prstmntISBN=conn.prepareStatement("UPDATE ISBN SET status = 'R' WHERE ISBN = '"+book.getISBN()+"'");
            prstmntISBN.executeUpdate();
            System.out.print("DONE!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<BorrowedBook> listOfUsersBook= FXCollections.observableArrayList();

    public int viewUsersBook(String userID)  {
        int fee = 0;
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet())
        {

            System.out.println(userID);
            System.out.print("DONE!");
            rowSet.setUrl(DATABASE_URL);
            if(!userID.equals("all"))
            rowSet.setCommand("SELECT * FROM borrowedBooks WHERE usersID = '"+userID+"'"); // set query
            else
                rowSet.setCommand("SELECT * FROM borrowedBooks"); // set query
            rowSet.execute();
            Date date = new Date();
            Date date2 = new Date();
            Date today = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("E yyyy.MM.dd");
            while (rowSet.next()){
                if(rowSet.getString("expiredDate")!=null) {
                    date.setTime(Long.parseLong(rowSet.getString("expiredDate")) * 86400000);
                    date2.setTime(Long.parseLong(rowSet.getString("borrowedDate")) * 86400000);

                    if (date.before(today))
                        fee = fee + Integer.parseInt(rowSet.getString("feeForOneDay")) * ((int) today.getTime() / 86400000 - (int) date.getTime() / 86400000);

                    listOfUsersBook.add(new BorrowedBook(formatForDateNow.format(date), formatForDateNow.format(date2),
                            rowSet.getString("usersID"), rowSet.getString("isbnID"), Integer.parseInt(rowSet.getString("feeForOneDay"))));
                }
                else
                    listOfUsersBook.add(new BorrowedBook(rowSet.getString("usersID"), rowSet.getString("isbnID")));

            }

        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
        System.out.println(fee);
        return fee;
    }

    public void delete(Book book){
        try {
            Connection connection=DriverManager.getConnection(DATABASE_URL);
            Statement statement=connection.createStatement();
            System.out.print("DONE!");

            System.out.println();
            //System.out.println("index" + deletingIndex);
            //System.out.println("DELETE * FROM ISBN WHERE book_id =" + deletingIndex + " ");

            statement.executeUpdate("DELETE FROM ISBN WHERE book_id ='" + book.getId() + "'"); // set query
            statement.close();
            connection.close();
        }
        catch (Exception e) {
            System.err.println("Exception: "+e.getMessage());
        }
    }

    public void searchWithOverdue()
    {
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet())
        {
            System.out.print("DONE!");
            rowSet.setUrl(DATABASE_URL);
            rowSet.setCommand("SELECT * FROM borrowedBooks"); // set query
            rowSet.execute();
            Date date = new Date();
            Date date2 = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("E yyyy.MM.dd");
            while (rowSet.next()){
                date2.setTime(Long.parseLong(rowSet.getString("expiredDate"))*86400000);
                if(!date.before(date2)) {
                    listOfUsersBook.add(new BorrowedBook(formatForDateNow.format(date), formatForDateNow.format(date2),
                            rowSet.getString("usersID"), rowSet.getString("isbnID"), Integer.parseInt(rowSet.getString("feeForOneDay"))));
                    System.out.println("found");
                }
                        System.out.println();
            }
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
    }

    public void returnBook(String ISBN){
        Connection conn;
        try  {
            Connection connection=DriverManager.getConnection(DATABASE_URL);
            Statement statement=connection.createStatement();
            System.out.print("DONE!");

            System.out.println();
            //System.out.println("index" + deletingIndex);
            //System.out.println("DELETE * FROM ISBN WHERE book_id =" + deletingIndex + " ");

            statement.executeUpdate("DELETE FROM borrowedBooks WHERE isbnID ='" + ISBN + "'"); // set query
            statement.close();
            connection.close();
            conn = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement prstmntISBN=conn.prepareStatement("UPDATE ISBN SET status = 'F' WHERE ISBN = '"+ISBN+"'");
            prstmntISBN.executeUpdate();
            System.out.print("DONE!");
        }


        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
    }


}
