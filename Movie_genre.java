//Import packages
import java.sql.*; //JDBC packages
import java.util.*;

/**
 * A class representing Movie_Genre objects.
 * @author Siam Al-mer Chowdhury
 */
public class Movie_genre {
    Scanner scan = new Scanner(System.in);

    // DB connection properties
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String jdbc_url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    // Login information
    private String username = "xxx";
    private String password = "xxx";

    String movie_id;
    String genre;

    public Movie_genre(String username, String password){
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
    public Movie_genre[] loadAll() throws SQLException {
        // get the connection
        Connection connection = getConnection();
        // create the SELECT SQL
        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append(" SELECT movie_id, m_genre FROM Movie_genre ORDER BY movie_id");
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
                    Movie_genre movie_genre = new Movie_genre(this.username,this.password);
                    movie_genre.setMovie_id(rs.getString("movie_id"));
                    movie_genre.setGenre(rs.getString("m_genre"));

                    // store it in the list
                    collection.add(movie_genre);
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
        return (Movie_genre[])collection.toArray(new Movie_genre[0]);
    }

    /**
     * Display the table content
     */
    public void print_table_content(){
        // print out all information on the Member table
        Movie_genre[] db;
        try {
            db = loadAll();
            System.out.println("\nMOVIE_ID \t GENRE");
            for (int i = 0; i <db.length; i++) {
                Movie_genre mdb = db[i];
                System.out.println(String.format("%-12s %-15s", mdb.getMovie_id() , mdb.getGenre()));
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
        String sql = "INSERT into Movie_genre(movie_id, m_genre) VALUES(?,?)";
        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,movie_id);
        statement.setString(2,genre);
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
        System.out.print("\nEnter movie_id:");
        setMovie_id(scan.next());
        System.out.print("Enter genre:");
       setGenre(scan.next());

        System.out.println("\n");
    }

    /**
     * Update table.
     */
    public void updateTable() throws SQLException{
        System.out.print("Please provide the existing movie_id and genre to update on");
        setVariables();
        System.out.print("Update to new movie_id:\n>\t");
        String temp = scan.next();
        System.out.print("Update to new movie_genre:\n>\t");
        String temp1 = scan.next();
        // get the connection
        Connection connection = getConnection();
        // create the INSERT SQL
        String sql = "UPDATE Movie_genre SET movie_id = ? , m_genre = ? WHERE movie_id = ? AND m_genre = ?";

        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,temp);
        statement.setString(2,temp1);
        statement.setString(3,movie_id);
        statement.setString(4,genre);
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

    /**
     * Delete a data.
     * @throws SQLException
     */
    public void deleteData() throws SQLException {
        System.out.print("Enter the movie_ID and movie_genre of the movie you want to delete:");
        setVariables();
        // get the connection
        Connection connection = getConnection();
        // create the INSERT SQL
        String sql = "DELETE FROM Movie_genre WHERE movie_id = ? AND m_genre = ?";
        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,movie_id);
        statement.setString(2,genre);
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
    /*Getter and Setter*/
    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getMovie_id() {
        return movie_id;
    }
    public String getGenre() {
        return genre;
    }
}
