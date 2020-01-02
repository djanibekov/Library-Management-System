package Book;

import Database.Bookdb;

import java.util.Arrays;

public class Book {
    public String title;
    public String author;
    public String subject;
    public String amount;
    public String globalISBN;
    private String publishDate;

    public Book(String author, String title, String subject, String globalISBN, String amount, String publishDate) {
        this.author = author;
        this.subject = subject;
        this.title = title;
        this.globalISBN=globalISBN;
        this.amount = amount;
        this.publishDate=publishDate;
    }
    public Book(){}
    public String getId() {
        return globalISBN;
    }

    public void setId(String id) {
        this.globalISBN = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

     public void setPublishDate(String day,String month, String year) {
         this.publishDate = day + "/" + month + "/" + year + ".";                  //set date in form day/month/year
     }

    public void save(){
        Bookdb db=new Bookdb();
        System.out.print("!Done");
        db.saveBook(this);                                      //pass itself (book) to save in database
    }

    public String getPublishDate() {
        return publishDate;
    }

    public char[] getDay() {
        char[] day = new char[2];
        for (int i = 0; i < publishDate.length(); i++)
            if (publishDate.charAt(i) == '/') {
                publishDate.getChars(0, i, day, 0);                     //get exactly day from publish date that was saved in form  day/month/year
                break;
            }
        return day;
    }
    public char[] getMonth() {
        char[] month = new char[2];
        for (int i = 0; i < publishDate.length(); i++)
            if (publishDate.charAt(i) == '/') {
                for (int j = i+1; j < publishDate.length(); j++)
                    if (publishDate.charAt(j) == '/')
                    publishDate.getChars(i+1, j, month, 0);                  //get exactly month from publish date that was saved in form  day/month/year
                break;
            }
        return month;
    }

    public char[] getYear() {
        char[] year = new char[4];
        for (int i = 0; i < publishDate.length(); i++)
            if (publishDate.charAt(i) == '/') {
                for (int j = i+1; j < publishDate.length(); j++)
                    if (publishDate.charAt(j) == '/')
                        publishDate.getChars(j+1, publishDate.length()-1, year, 0); //get exactly year from publish date that was saved in form  day/month/year
                break;
            }
        return year;
    }
}



