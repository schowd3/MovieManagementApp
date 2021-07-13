import java.sql.*;
import java.util.*;

/**
 * A class representing Watch objects.
 * @author Siam Al-mer Chowdhury
 */
public class Watch {
    Scanner scan = new Scanner(System.in);

    // DB connection properties
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String jdbc_url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    // Login information
    private String username = "xxx";
    private String password = "xxx";

    String member_id;
    String profile_name;
    String movie_id;
    String rating;

    public Watch(String username, String password){
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
    public Watch[] loadAll() throws SQLException {
        // get the connection
        Connection connection = getConnection();

        // create the SELECT SQL
        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append(" SELECT member_id, profile_name, movie_id, rating FROM Watch ORDER BY member_id");
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
                    Watch watch = new Watch(this.username,this.password);
                    watch.setMember_id(rs.getString("member_id"));
                    watch.setProfile_name(rs.getString("profile_name"));
                    watch.setMovie_id(rs.getString("movie_id"));
                    watch.setRating(rs.getString("rating"));


                    // store it in the list
                    collection.add(watch);
                }
            }
        } catch (SQLException e)
        {
            System.out.println(e);
        } finally
        {
            rs.close();
            statement.close();
            close(connection);
        }
        //   return the array
        return (Watch[]) collection.toArray(new Watch[0]);
    }

    /**
     * Display the table content
     */
    public void print_table_content(){
        // print out all information on the Member table
        Watch[] db;
        try {
            db = loadAll();
            System.out.println("\nMEMBER_ID \t Profile_NAME \t Movie_ID \t Rating\t");
            for (int i = 0; i <db.length; i++) {
                Watch mdb = db[i];
                System.out.println(String.format("%-12s %-15s %-12s %-12s", mdb.getMember_id() , mdb.getProfile_name(),mdb.getMovie_id(),mdb.getRating()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Set instances variables
     */
    public void setVariables(){
        System.out.print("\nEnter member_id:");
        setMember_id(scan.next());

        System.out.print("Enter profile name:");
        setProfile_name(scan.next());

        System.out.print("Enter movie_id:");
        setMovie_id(scan.next());

        System.out.print("Enter rating:");
        setRating(scan.next());

        System.out.println("\n");
    }


    /**
     * @throws SQLException
     */
    public void insertData () throws SQLException
    {
        setVariables();
        // get the connection
        Connection connection = getConnection();
        // create the INSERT SQL
        String sql = "INSERT into Watch(member_id, profile_name, movie_id, rating) VALUES(?,?,?,?)";

        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,member_id);
        statement.setString(2,profile_name);
        statement.setString(3,movie_id);
        statement.setString(4,rating);
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

    public void updateTable() throws SQLException{
        setVariables();
        // get the connection
        Connection connection = getConnection();

        System.out.print("Enter new member_id:\n>\t");
        String temp = scan.next();

        System.out.print("Enter new profile_name:\n>\t");
        String temp1=scan.next();

        System.out.print("Enter new movie_id:\n>\t");
        String temp2=scan.next();
        System.out.print("Enter new rating:\n>\t");
        String temp3=scan.next();
        // create the INSERT SQL
        String sql = "UPDATE Watch SET member_id = ? ,profile_name = ?, movie_id= ?, rating = ? WHERE member_id = ? AND profile_name = ? AND movie_id= ?";
        //Checkpoint
        // create the statement-
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,temp);
        statement.setString(2,temp1);
        statement.setString(3,temp2);
        statement.setString(4,temp3);

        statement.setString(5,member_id);
        statement.setString(6,profile_name);
        statement.setString(7,movie_id);

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
        System.out.print("Enter the ID of the member you want to delete:\n>\t");
        setMember_id(scan.next());
        System.out.print("Enter the profile_name of the member you want to delete:\n>\t");
        setProfile_name(scan.next());
        System.out.print("Enter the movie_ID of the member you want to delete:\n>\t");
        setMovie_id(scan.next());

        // get the connection
        Connection connection = getConnection();
        // create the INSERT SQL
        String sql = "DELETE FROM Watch WHERE member_id = ? AND profile_name = ? AND movie_id = ?";

        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,member_id);
        statement.setString(2,profile_name);
        statement.setString(3,movie_id);
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

    public void getRentalHistory() throws SQLException{
        System.out.print("Enter the member_id:\n>\t");
        setMember_id(scan.next());
        System.out.print("Enter the profile_name of that member:\n>\t");
        setProfile_name(scan.next());
        // get the connection
        Connection connection = getConnection();
        // create the INSERT SQL
        String sql = "SELECT * from Watch WHERE member_id = ? AND profile_name = ?";
        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,member_id);
        statement.setString(2,profile_name);
        try
        {
            ResultSet rs = statement.executeQuery();
            System.out.println("\nMEMBER_ID \t PROFILE_NAME \t MOVIE_ID \t RARTING\t");
            while(rs.next()){
                System.out.println(String.format("%-12s %-15s %-12s %-12s", rs.getString(1),
                        rs.getString(2), rs.getString(3), rs.getString(4)));

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

    /*Getters Setters*/
    public void setMember_id(String member_id){
        this.member_id=member_id;
    }
    public String getMember_id(){
        return member_id;
    }
    public void setProfile_name(String profile_name){
        this.profile_name=profile_name;
    }
    public String getProfile_name(){
        return profile_name;
    }
    private void setRating(String rating) {
        this.rating=rating;
    }
    public String getRating(){
        return rating;
    }
    private void setMovie_id(String movie_id) {
        this.movie_id=movie_id;
    }
    private String getMovie_id(){
        return movie_id;
    }

}
