package Book;

import Database.ISBNdb;

import java.util.Vector;

public class ISBN {
    String status="Free";                                                                  //every book in library has its own local ISBN
    String ISBN;                                                                  //so same books has different isbn
    String bookID;
    public ISBN(){}
    public ISBN( String ISBN, String status, String bookID){
        this.ISBN=ISBN;
        this.status=status;
        this.bookID=bookID;
        System.out.println(ISBN+" "+status+" "+bookID);
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }




    public void setISBN(String ISBN){

        this.ISBN=ISBN;

    }

    public String getISBN(){

        return ISBN;
    }

    public void save(String globalISBN){
        ISBNdb db=new ISBNdb();
        db.saveBookISBN(this, globalISBN);                     //save itself (ISBN) in database
    }

}