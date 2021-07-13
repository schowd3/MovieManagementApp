import java.sql.*; //JDBC packages
import java.util.*;
/**
 * A class representing Genre objects.
 * @author Siam Al-mer Chowdhury
 */
public class Genre {
    Scanner scan = new Scanner(System.in);
    // DB connection properties
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String jdbc_url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    // Login information
    private String username = "xxx";
    private String password = "xxx";

    String m_genre;
    /* Constructor
     * @param username name of user.
     * @password password of user.
     */
    public Genre(String username, String password){
        this.username = username;
        this.password = password;
    }

    /** Method to get the connection for the database
     * @return java.sql.Connection object
     */
    private Connection getConnection() {
        // register the JDBC driver
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // create a connection
        Connection connection = null;
        try {
            connection = DriverManager.getConnection (jdbc_url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Close the connection
     * @param connection
     * @throws SQLException
     */
    public void close(Connection connection) throws SQLException
    {
        try
        {
            connection.close();
        } catch (SQLException e)
        {
            throw e;
        }
    }

    /**
     * Get everything in the sql table and store it
     * @return
     * @throws SQLException
     */
    public Genre[] loadAll() throws SQLException {
        // get the connection
        Connection connection = getConnection();

        // create the SELECT SQL
        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append(" SELECT m_genre FROM Genre");
        Statement statement = null;
        ResultSet rs = null;
        ArrayList collection = new ArrayList();
        try
        {
            // create the statement
            statement = connection.createStatement();
            // Insert the data
            rs = statement.executeQuery(sbSelect.toString());
            if (rs != null) {
                // when the resultset is not null, there are records returned
                while (rs.next())
                {
                    // loop through each result and store the data
                    // as an memberCollectionDatabase object
                    Genre genre = new Genre(this.username, this.password);
                    genre.setM_genre(rs.getString("m_genre"));
                    // store it in the list
                    collection.add(genre);
                }
            }
        } catch (SQLException e)
        {
            throw e;
        } finally
        {
            rs.close();
            statement.close();
            close(connection);
        }
        //   return the array
        return (Genre[])collection.toArray(new Genre[0]);
    }


    /**
     * Display the table content
     */
    public void print_table_content(){
        // print out all information on the Member table
        Genre[] db;
        try {
            db = loadAll();
            System.out.println("\nGENRE");
            for (int i = 0; i <db.length; i++) {
               Genre mdb = db[i];
                System.out.println(String.format("%-12s", mdb.getM_genre()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    /**
     * Insert data into table
     * @throws SQLException
     */
    public void insertData () throws SQLException
    {
        setVariables();
        // get the connection
        Connection connection = getConnection();
        // create the INSERT SQL
        String sql = "INSERT into Genre(m_genre) VALUES(?)";
        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,m_genre);
        try
        {
            // Insert the data
            statement.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println(e);
        } finally
        {
            statement.close();
            close(connection);
        }
    }

    /**
     * Set instances variables
     */
    public void setVariables(){
        System.out.print("\nEnter genre:");
        setM_genre(scan.next());
        System.out.println("\n");
    }

    /**
     * Update table.
     */
    public void updateTable() throws SQLException{
        System.out.print("For update");
        setVariables();
        System.out.println("Enter new value for current genre:");
        String temp = scan.next();
        // get the connection
        Connection connection = getConnection();
        // create the INSERT SQL
        String sql = "UPDATE Genre SET m_genre = ? WHERE m_genre = ?";

        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,temp);
        statement.setString(2,m_genre);
        try
        {
            // Insert the data
            statement.executeUpdate ();
        } catch (SQLException e)
        {
            System.out.println(e);
        } finally
        {
            statement.close();
            close(connection);
        }
    }

    public void deleteData() throws SQLException {
        System.out.print("Enter the genre you want to delete:\n>\t");
        setM_genre(scan.next());
        // get the connection
        Connection connection = getConnection();
        // create the INSERT SQL
        String sql = "DELETE FROM Genre WHERE m_genre = ?";

        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,m_genre);
        try
        {
            //delete the data
            statement.executeUpdate ();
        } catch (SQLException e)
        {
            System.out.println(e);
        } finally
        {
            statement.close();
            close(connection);
        }
    }






    public static void main(String args[]){

    }
    /*Getter & Setter*/
    public void setM_genre(String m_genre) {
        this.m_genre = m_genre;
    }
    public String getM_genre() {
        return m_genre;
    }
}
