//package Database;
//
//import java.sql.*;
//import java.lang.Boolean;
//public class DataBaseCreation {
//    final public static String url = "jdbc:derby:Database;create=true";
//    public static void main(String[] args) throws Exception {
//        try (Connection conn = DriverManager.getConnection(url);
//             Statement stmt = conn.createStatement()) {
//            stmt.executeUpdate("CREATE TABLE book ("
//                    + "id INT NOT NULL GENERATED ALWAYS AS IDENTITY, "
//                    + "Title VARCHAR(50), "
//                    + "Author VARCHAR(30), "
//                    + "Subject VARCHAR(30),"
//                    + "Amount VARCHAR(5),"
//                    + "PublishDate VARCHAR(15))");
//
//            stmt.executeUpdate("CREATE TABLE ISBN ("
//                    + "id INTEGER PRIMARY KEY, "
//                    + "book_id integer REFERENCES book (id), "
//                    + "ISBN VARCHAR(30), "
//                    + "Status VARCHAR(1))");
//
//            stmt.executeUpdate("CREATE TABLE users ("
//                    + "id INTEGER PRIMARY KEY,"
//                    + "idNumber VARCHAR(8),"
//                    + "nameOfUser VARCHAR(20),"
//                    + "surnameOfUser VARCHAR(20),"
//                    + "password VARCHAR(20),"
//                    +"typeOfUser VARCHAR(5))");
//
//            stmt.executeUpdate("CREATE TABLE borrowedBooks ("
//                    + "FOREIGN KEY (usersID) REFERENCES users (id), "
//                    + "FOREIGN KEY (isbnID) REFERENCES ISBN (id),"
//                    + "borrowedDate VARCHAR(20),"
//                    + "expiredDate VARCHAR(20))");
////                stmt.executeUpdate("INSERT INTO book VALUES (1, 'C++ How to Program', 'Kto to', '2')");
////                stmt.executeUpdate("INSERT INTO book VALUES (2, 'Java How to Program', 1.2)");
////                stmt.executeUpdate("INSERT INTO student VALUES (1, 1, 'U1810281', 'Free')");
////                stmt.executeUpdate("INSERT INTO student VALUES (2, 2, 'U1810266', '2002-08-15 09:12:00')");
////                stmt.executeUpdate("INSERT INTO student VALUES (3, 1, 'U1810270', '2002-09-09 10:36:00')");
////                stmt.executeUpdate("INSERT INTO student VALUES (4, 1, 'U1810256', '2010-06-08 01:24:00')");
//            stmt.executeUpdate("INSERT INTO users VALUES (1, 'admin', 'Jonibek', 'Mansurov','password','a')");
//            stmt.executeUpdate("INSERT INTO users VALUES (2, 'U1810266', 'Jonibek', 'Mansurov','password','s')");
//            stmt.executeUpdate("INSERT INTO users VALUES (2, 'U18266', 'Jonibek', 'Mansurov','password','l')");
//            System.out.print("DONE!");
//        }
//
//
//
//    }
//}




package Database;

import java.sql.*;
public class DataBaseCreation {
    final public static String url = "jdbc:derby:Database;create=true";
    public static void main(String[] args) throws Exception {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE book ("
                    + "globalISBN VARCHAR(30), "
                    + "title VARCHAR(50), "
                    + "author VARCHAR(30), "
                    + "subject VARCHAR(30),"
                    + "amount VARCHAR(5),"
                    + "publishDate VARCHAR(15),"
                    + "PRIMARY KEY(globalISBN))");
            stmt.executeUpdate("CREATE TABLE isbn ("
                    + "book_id VARCHAR(30),"
                    + "FOREIGN KEY (book_id) REFERENCES book (globalISBN), "
                    + "ISBN VARCHAR(30), "
                    + "Status VARCHAR(1),"
                    + "PRIMARY KEY(ISBN))");

            stmt.executeUpdate("CREATE TABLE users ("
                    + "login VARCHAR(21),"
                    + "nameOfUser VARCHAR(20),"
                    + "surnameOfUser VARCHAR(20),"
                    + "password VARCHAR(20),"
                    + "typeOfUser VARCHAR(5),"
                    + "fee INT,"
                    + "blockStatus VARCHAR(5),"
                    + "PRIMARY KEY(login))");

            stmt.executeUpdate("CREATE TABLE borrowedBooks ("
                    + "usersID VARCHAR(21),"
                    + "isbnID VARCHAR(30),"
                    + "FOREIGN KEY (usersID) REFERENCES users (login), "
                    + "FOREIGN KEY (isbnID) REFERENCES ISBN (ISBN),"
                    + "borrowedDate VARCHAR(20),"
                    + "feeForOneDay VARCHAR(5),"
                    + "expiredDate VARCHAR(20))");
//                 stmt.executeUpdate("CREATE TABLE student ("
//                        + "id INTEGER PRIMARY KEY,"
//                        + "id_number VARCHAR(8),"
//                        + "name_of_student VARCHAR(20),"
//                        + "surname_of_student VARCHAR(20),"
//                        + "password VARCHAR(20))");
//                 stmt.executeUpdate("CREATE TABLE users ("
//                    + "id INTEGER PRIMARY KEY,"
//                    + "id_Number VARCHAR(8),"
//                    + "name_Of_User VARCHAR(20),"
//                    + "surname_Of_User VARCHAR(20),"
//                    + "password VARCHAR(20),"
//                    + "type_Of_User VARCHAR(5))");
//                 stmt.executeUpdate("CREATE TABLE librarian("
//                        + "id INTEGER PRIMARY KEY,)"
//                        + "id_number VARCHAR(8),"
//                        + "name_of_librarian VARCHAR(20),"
//                        + "surname_of_librarian VARCHAR(20),"
//                        + "password VARCHAR(20)");
//                stmt.executeUpdate("INSERT INTO book VALUES (1, 'C++ How to Program', 'Kto to', '2', '20/12/2019')");
//                stmt.executeUpdate("INSERT INTO book VALUES (2, 'Java How to Program', 1.2)");
//                stmt.executeUpdate("INSERT INTO ISBN VALUES (1, 1, 'U1810281', 'F')");
//                stmt.executeUpdate("INSERT INTO student VALUES (2, 2, 'U1810266', '2002-08-15 09:12:00')");
//                stmt.executeUpdate("INSERT INTO student VALUES (3, 1, 'U1810270', '2002-09-09 10:36:00')");
        //        stmt.executeUpdate("INSERT INTO student VALUES (4, 1, 'U1810256', '2010-06-08 01:24:00')");
            stmt.executeUpdate("INSERT INTO  users (login, nameOfUser, surnameOfUser, password, typeOfUser) VALUES ( 'admin', 'Jonibek', 'Mansurov','adminnumberone','a')");
            stmt.executeUpdate("INSERT INTO users (login, nameOfUser, surnameOfUser, password, typeOfUser, blockStatus, fee) VALUES ( 'U1810281', 'Jonibek', 'Mansurov','tyIndians4help','s','can',0)");
            stmt.executeUpdate("INSERT INTO users (login, nameOfUser, surnameOfUser, password, typeOfUser) VALUES ( 'librarian', 'Jonibek', 'Mansurov','pythonBetter','l')");
            System.out.print("DONE!");
        }
    }
}