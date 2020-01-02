package Database;

import java.sql.*;

import Book.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

public class Bookdb {
    private static final String DATABASE_URL = "jdbc:derby:Database";
    public ObservableList<Book> observableList= FXCollections.observableArrayList();
    public void saveBook(Book book) {
        Connection conn;
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet_forBook = statement.executeQuery("SELECT COUNT(*) FROM book");
            resultSet_forBook.next();
            int rowCount_forBook = resultSet_forBook.getInt(1);
            PreparedStatement prstmnt = conn.prepareStatement("INSERT INTO book (title,author,subject,amount,publishDate, globalISBN) VALUES (?,?,?,?,?,?)");
            prstmnt.setString(1, book.getTitle());
            prstmnt.setString(2, book.getAuthor());
            prstmnt.setString(3, book.getSubject());
            prstmnt.setString(4, book.getAmount());
            prstmnt.setString(5, book.getPublishDate());
            prstmnt.setString(6, book.getId());
            prstmnt.executeUpdate();
            prstmnt.close();
            System.out.println(rowCount_forBook);
            System.out.println(book.getTitle());
            System.out.println(book.getAuthor());
            System.out.print(book.getSubject());
            System.out.print(book.getAmount());
            System.out.print(book.getPublishDate());
            System.out.print("DONE!");
            resultSet_forBook.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void show()  {
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet())
        {
            System.out.print("DONE!");
            rowSet.setUrl(DATABASE_URL);
            rowSet.setCommand("SELECT * FROM book"); // set query
            rowSet.execute();
            while (rowSet.next()){
                System.out.println("aa");
                observableList.add(new Book (rowSet.getString("Author"), rowSet.getString("Title"),
                        rowSet.getString("Subject"), rowSet.getString("globalISBN"), rowSet.getString("Amount"), rowSet.getString("PublishDate")));
                System.out.println();
            }
            rowSet.close();
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
    }

    public void delete(Book book){
        try {
            ISBNdb isbn=new ISBNdb();
            isbn.delete(book);
            Connection connection=DriverManager.getConnection(DATABASE_URL);
            Statement statement=connection.createStatement();
            System.out.print("DONE!");
            System.out.println();
            //System.out.println("index" + deletingIndex);
            //System.out.println("DELETE * FROM book WHERE book_id =" + deletingIndex + " ");

            statement.executeUpdate("DELETE FROM book WHERE globalISBN='" + book.getId() + "'"); // set query
            statement.close();
            connection.close();
        }
        catch (Exception e) {
            System.err.println("Exception: "+e.getMessage());
        }
    }
    public boolean isFree(Book book) {

        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {
            rowSet.setUrl(DATABASE_URL);
            rowSet.setCommand("SELECT status FROM ISBN WHERE book_id = '" + book.getId() + "'");// set query
            rowSet.execute();


            while (rowSet.next()) {
                if(rowSet.getString("status").equals("B")||rowSet.getString("status").equals("R")) {
                    rowSet.close();
                    return false;
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
        return true;
    }
    public Book showExactBook(String ISBN)  {
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet())
        {
            System.out.println(ISBN);
            System.out.print("DONE!");
            rowSet.setUrl(DATABASE_URL);
            rowSet.setCommand("SELECT * FROM isbn WHERE ISBN = '"+ISBN+"'");
            rowSet.execute();
            rowSet.next();
            ISBN isbn = new ISBN();
            isbn.setBookID(rowSet.getString("book_id"));
            String globalISBN = isbn.getBookID();
            rowSet.close();
            System.out.println(globalISBN+"asasasas");
            JdbcRowSet row = RowSetProvider.newFactory().createJdbcRowSet();
            row.setUrl(DATABASE_URL);
            row.setCommand("SELECT * FROM book WHERE globalISBN = '"+globalISBN+"'"); // set query
            row.execute();
            row.next();
            Book book = new Book (row.getString("Author"), row.getString("Title"), row.getString("Subject"), row.getString("globalISBN"), row.getString("Amount"), row.getString("PublishDate"));
            System.out.println(book.getTitle()+"asasasassasasassa112121212");
            row.close();
            return book;


        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public void usersBook(ObservableList<BorrowedBook> list){

        for (int i = 0; i <list.size(); i++) {
            observableList.add(showExactBook(list.get(i).getISBN()));
        }
    }
    public void update(Book book) throws SQLException {
        Connection connection;
        connection = DriverManager.getConnection(DATABASE_URL);
        Statement statement = connection.createStatement();
        System.out.print("DONE!");
        System.out.println("yeeeeee");
        statement.executeUpdate("UPDATE book SET "
                +"author = '"+book.getAuthor()+"',"
                +"title = '"+book.getTitle()+"',"
                +"subject = '"+book.getSubject()+"',"
                +"amount = '"+book.getAmount()+"',"
                +"publishDate = '"+book.getPublishDate()+".'"
                + " WHERE globalISBN = '"+book.getId()+"'");
        statement.close();
    }

    public boolean isISBNExist(String ISBN){
        System.out.println("check "+ISBN);
//        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet())
//        {
//            System.out.print("DONE!");
//            rowSet.setUrl(DATABASE_URL);
//            rowSet.setCommand("SELECT globalISBN FROM books "); // set query
//            rowSet.execute();
//            while(rowSet.next()){
//                if(rowSet.getString("globalISBN").equals(ISBN))
//                    return true;
//            }
//
//        }
//        catch (SQLException sqlException){
//            sqlException.printStackTrace();
//            System.exit(1);
//        }
        return false;
    }


    public void getSortBook(Book book, String status){

        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet())
        {
            System.out.println(book.getTitle());
            System.out.print("SORT!");
            rowSet.setUrl(DATABASE_URL);
            rowSet.setCommand("SELECT * FROM book WHERE title LIKE '"+book.getTitle()+"%'"); // set query
            rowSet.execute();
            while (rowSet.next()){
                System.out.println("bvcxzxczasaszx");
                observableList.add(new Book (rowSet.getString("Author"), rowSet.getString("Title"),
                        rowSet.getString("Subject"), rowSet.getString("globalISBN"), rowSet.getString("Amount"), rowSet.getString("PublishDate")));
                System.out.println();
            }
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
    }

}
