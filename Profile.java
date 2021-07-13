//Import packages
import java.sql.*; //JDBC packages
import java.util.*;
import java.util.Scanner;
/**
 * A class representing Profile objects.
 * @author Siam Al-mer Chowdhury
 */
public class Profile {
    Scanner scan = new Scanner(System.in);

    // DB connection properties
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String jdbc_url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    // Login information
    private String username = "xxx";
    private String password = "xxx";

    private String member_id;
    private String profile_name;

    public Profile(String username, String password){
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
    public Profile[] loadAll() throws SQLException {
        // get the connection
        Connection connection = getConnection();
        // create the SELECT SQL
        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append(" SELECT member_id, profile_name FROM Profile ORDER BY member_id");
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
                   Profile profile = new Profile(this.username,this.password);
                    profile.setMember_id(rs.getString("member_id"));
                    profile.setProfile_name(rs.getString("profile_name"));
                    // store it in the list
                    collection.add(profile);
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
        return (Profile[])collection.toArray(new Profile[0]);
    }

    /**
     * Display the table content
     */
    public void print_table_content(){
        // print out all information on the Member table
       Profile[] db;
        try {
            db = loadAll();
            System.out.println("\nMEMBER_ID \t PROFILE_NAME\t ");
            for (int i = 0; i <db.length; i++) {
                Profile mdb = db[i];
                System.out.println(String.format("%-12s %-15s", mdb.getMember_id() , mdb.getProfile_name()));
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
        String sql = "INSERT into Profile(member_id, profile_name) VALUES(?,?)";
        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,member_id);
        statement.setString(2,profile_name);
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
        System.out.print("\nEnter member_id:");
        setMember_id(scan.next());
        System.out.print("Enter profile_name:");
        setProfile_name(scan.next());
        System.out.println("\n");
    }

    /**
     * Update table.
     */
    public void updateTable() throws SQLException{
        System.out.println("TO UPDATE ON:");
        setVariables();
        System.out.print("Enter new member_id:\n>\t");
        String temp = scan.next();
        System.out.print("enter new profile_name:\n>\t");
        String temp1=scan.next();
        // get the connection
        Connection connection = getConnection();
        // create the INSERT SQL
        String sql = "UPDATE Profile SET member_id = ? ,profile_name = ? WHERE member_id = ? AND profile_name = ?";

        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,temp);
        statement.setString(2, temp1);
        statement.setString(3, member_id);
        statement.setString(4, profile_name);

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
        System.out.print("VALUES TO DELETE ON PROFILE:");
        setVariables();
        // get the connection
        Connection connection = getConnection();
        // create the INSERT SQL
        String sql = "DELETE FROM Profile WHERE member_id = ? AND profile_name = ?";

        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,member_id);
        statement.setString(2, profile_name);
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



    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }
    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }
    public String getMember_id() {
        return member_id;
    }
    public String getProfile_name() {
        return profile_name;
    }
}
