//Import packages
import java.sql.*; //JDBC packages
import java.util.*;

/**
 * This class control the Actor table from SQL 
 * @author Siam Al-mer Chowdhury
 */
public class Actor {
    Scanner scan = new Scanner(System.in);

    // DB connection properties
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String jdbc_url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    // Login information
    private String username = "xxx";
    private String password = "xxx";

    private String actor_id;
    private String first_name;
    private String last_name;

    public Actor(String username, String password){
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
     * @return the collection of Actor array.
     * @throws SQLException
     */
    public Actor[] loadAll() throws SQLException {
        // get the connection
        Connection connection = getConnection();
        // create the SELECT SQL
        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append(" SELECT actor_id, first_name, last_name FROM Actor ORDER BY actor_id");
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
                    Actor actor = new Actor(this.username,this.password);
                    actor.setActor_id(rs.getString("actor_id"));
                    actor.setFirst_name(rs.getString("first_name"));
                    actor.setLast_name(rs.getString("last_name"));
                    // store it in the list
                    collection.add(actor);
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
        return (Actor[])collection.toArray(new Actor[0]);
    }

    /**
     * Display the table content
     */
    public void print_table_content(){
        // print out all information on the Member table
        Actor[] db;
        try {
            db = loadAll();
            System.out.println("\nACTOR_ID \t FIRST_NAME \t LAST_NAME");
            for (int i = 0; i <db.length; i++) {
                Actor mdb = db[i];
                System.out.println(String.format("%-12s %-15s %-11s", mdb.getActor_id() , mdb.getFirst_name(), mdb.getLast_name()));
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
        String sql = "INSERT into Actor(actor_id, first_name, last_name) VALUES(?,?,?)";
        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,actor_id);
        statement.setString(2,first_name);
        statement.setString(3,last_name);

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
        System.out.print("Enter first_name:");
        setFirst_name(scan.next());
        System.out.print("Enter last_name:");
        setLast_name(scan.next());
        System.out.println("\n");
    }

    /**
     * Update table.
     */
    public void updateTable() throws SQLException{
        setVariables();
        // get the connection
        Connection connection = getConnection();
        // create the INSERT SQL
        String sql = "UPDATE Actor SET first_name = ? ,last_name = ? WHERE actor_id = ?";

        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,first_name);
        statement.setString(2,last_name);
        statement.setString(3,actor_id);
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
     * Delete table.
     */
    public void deleteData() throws SQLException {
        System.out.print("Enter the ID of the actor you want to delete:\n>\t");
       setActor_id(scan.next());
        // get the connection
        Connection connection = getConnection();
        // create the INSERT SQL
        String sql = "DELETE FROM Actor WHERE actor_id = ?";

        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,actor_id);
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
     /**
     * Search Movie Actor in table.
     */
    public void search_for_movie_actor() throws SQLException{
        System.out.print("Enter the First name:\n>\t");
        setFirst_name(scan.nextLine());

        System.out.print("Enter the Last name:\n>\t");
        setLast_name(scan.nextLine());

        // get the connection
        Connection connection = getConnection();
        // create the INSERT SQL
        String sql = "SELECT * from Movie M, Actor A, starred_by S Where A.actor_id = S.actor_id AND S.movie_id = M.movie_id And A.first_name= ? And A.last_name= ?";
        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,first_name);
        statement.setString(2,last_name);
        try
        {
            ResultSet rs = statement.executeQuery();
            System.out.println("\nMOVIE_ID \t TITLE \t\t\tMOVIE_YEAR \t\t PRODUCER \t\t\tAVG_RATING \t ACTOR_ID \t FIRST_NAME \t LAST_NAME");
            while(rs.next()){
                System.out.println(String.format("%-12s %-20s %-10s %-23s %-12s %-6s %-15s %s", rs.getString(1),
                        rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5),rs.getString(6),rs.getString(7),
                        rs.getString(8) ));



            }

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
     * Tester.
     */
    public static void main (String args[]){

    }

    /*Getter and Setters*/
    public void setActor_id(String actor_id) {
        this.actor_id = actor_id;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public String getActor_id() {
        return actor_id;
    }
    public String getFirst_name() {
        return first_name;
    }
    public String getLast_name() {
        return last_name;
    }
}
