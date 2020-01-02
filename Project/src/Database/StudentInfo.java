package Database;//package Database;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

public class StudentInfo{
    private static final String DATABASE_URL = "jdbc:derby:Database";
    public static void main(String args[])
    {
        try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet())
        {
            rowSet.setUrl(DATABASE_URL);
            rowSet.setCommand("SELECT * FROM ISBN"); // set query
            rowSet.execute();
            ResultSetMetaData metaData = rowSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            System.out.println("Authors Table of Books Database:\n");
            for (int i = 1; i <= numberOfColumns; i++)
                System.out.print("\t\t"+ metaData.getColumnName(i));
            System.out.println();
            while (rowSet.next()){
                for (int i = 1; i <= numberOfColumns; i++)
                    System.out.print("\t\t"+ rowSet.getObject(i));
                System.out.println();
            }
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
}
