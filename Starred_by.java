//Import packages
import java.sql.*; //JDBC packages
import java.util.*;

/**
 * LStarred_by class
 * @author Siam Al-mer Chowdhury, Steve Tran
 */
public class Starred_by {
    Scanner scan = new Scanner(System.in);
    // DB connection properties
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String jdbc_url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    // Login information
    private String username = "xxx";
    private String password = "xxx";

    private String actor_id;
    private String movie_id;

    public Starred_by(String username, String password){
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
    public Starred_by[] loadAll() throws SQLException {
        // get the connection
        Connection connection = getConnection();
        // create the SELECT SQL
        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append(" SELECT actor_id, movie_id FROM Starred_by");
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
                    Starred_by  starred_by = new Starred_by(this.username,this.password);
                    starred_by.setActor_id(rs.getString("actor_id"));
                    starred_by.setMovie_id(rs.getString("movie_id"));

                    // store it in the list
                    collection.add(starred_by);
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
        return (Starred_by[])collection.toArray(new Starred_by[0]);
    }

    /**
     * Display the table content
     */
    public void print_table_content(){
        // print out all information on the Member table
        Starred_by[] db;
        try {
            db = loadAll();
            System.out.println("\nACTOR_ID \t MOVIE_ID");
            for (int i = 0; i <db.length; i++) {
                Starred_by mdb = db[i];
                System.out.println(String.format("%-12s %-15s ", mdb.getActor_id() , mdb.getMovie_id()));
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
        String sql = "INSERT into Starred_by(actor_id, movie_id) VALUES(?,?)";
        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,actor_id);
        statement.setString(2,movie_id);

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
        System.out.print("\nEnter actor_id:");
        setActor_id(scan.next());
        System.out.print("Enter movie_id:");
        setMovie_id(scan.next());
        System.out.println("\n");
    }

    /**
     * Set instances variables
     */
    public void setVariables2(){
        System.out.print("\nEnter new actor_id:");
        setActor_id(scan.next());
        System.out.print("Enter new movie_id:");
        setMovie_id(scan.next());
        System.out.println("\n");
    }

    /**
     * Update table.
     */
    public void updateTable() throws SQLException{
        System.out.print("Enter actor_id to update on:\n>\t");
        String temp1 = scan.next();
        System.out.print("Enter movie_id of that actor:\n>\t");
        String temp2 = scan.next();
        setVariables2();
        // get the connection
        Connection connection = getConnection();
        // create the INSERT SQL
        String sql = "UPDATE Starred_by SET actor_id = ? , movie_id = ? WHERE actor_id = ? AND movie_id =?";

        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,actor_id);
        statement.setString(2,movie_id);
        statement.setString(3,temp1);
        statement.setString(4,temp2);
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
        System.out.print("Enter the ID of the starred actor you want to delete:\n>\t");
        setActor_id(scan.next());
        System.out.print("Enter the ID of the movie where the actor starred in you want to delete:\n>\t");
        setMovie_id(scan.next());
        // get the connection
        Connection connection = getConnection();
        // create the INSERT SQL
        String sql = "DELETE FROM Starred_by WHERE actor_id = ? AND movie_id = ?";

        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,actor_id);
        statement.setString(2,movie_id);
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

    public void main (String argsp[]){

    }
    /*Getters and Stters*/
    public void setActor_id(String actor_id) {
        this.actor_id = actor_id;
    }
    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }
    public String getActor_id() {
        return actor_id;
    }
    public String getMovie_id() {
        return movie_id;
    }
}

