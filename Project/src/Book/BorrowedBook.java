package Book;

import Database.ISBNdb;

public class BorrowedBook extends ISBN {                               //        extends from ISBN
    String expiredDate;                                                //        to save books that was borrowed by students
    String borrowDate;                                                 //        and show their borrow and expired date
    String studentID;
    int feePerDay;

    public BorrowedBook(String expiredDate, String borrowDate, String studentID, String ISBN, int feePerDay) {
        super.setISBN(ISBN);
        this.expiredDate = expiredDate;
        this.borrowDate = borrowDate;
        this.studentID = studentID;
        this.feePerDay = feePerDay;
    }

    public BorrowedBook( String studentID, String ISBN) {
        super.setISBN(ISBN);
        this.studentID = studentID;
    }

    public BorrowedBook() {}

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void save(){
        ISBNdb borrow = new ISBNdb();
        borrow.borrowBook(this, true);
    }

    public void reserve(){
        System.out.println("reserve"+"  "+ ISBN);
        ISBNdb borrow = new ISBNdb();
        borrow.borrowBook(this, false);
    }

    public int getFeePerDay() {
        return feePerDay;
    }

    public void setFeePerDay(int feePerDay) {
        this.feePerDay = feePerDay;
    }
}
