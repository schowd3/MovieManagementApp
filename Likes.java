//Import packages
import java.sql.*; //JDBC packages
import java.util.*;

/**
 * Likes class
 * @author Siam Al-mer Chowdhury, Steve Tran.
 */
public class Likes {
    Scanner scan = new Scanner(System.in);
    // DB connection properties
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String jdbc_url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    // Login information
    private String username = "xxx";
    private String password = "xxx";

    String member_id;
    String profile_name;
    String m_genre;
    
    /* Likes constructor.
     * @user_name username information of user.
     * @password password information of user.
     */
    public Likes(String username, String password){
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
     * @return the likes array.
     * @throws SQLException
     */
    public Likes[] loadAll() throws SQLException {
        // get the connection
        Connection connection = getConnection();
        // create the SELECT SQL
        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append(" SELECT member_id, profile_name, m_genre FROM Likes");
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
                    Likes  likes = new Likes(this.username,this.password);
                    likes.setMember_id(rs.getString("member_id"));
                    likes.setProfile_name(rs.getString("profile_name"));
                    likes.setM_genre(rs.getString("m_genre"));
                    // store it in the list
                    collection.add(likes);
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
        return (Likes[])collection.toArray(new Likes[0]);
    }

    /**
     * Display the table content
     */
    public void print_table_content(){
        // print out all information on the Member table
        Likes[] db;
        try {
            db = loadAll();
            System.out.println("\nMEMBER_ID \t PROFILE_NAME \t m_genre");
            for (int i = 0; i <db.length; i++) {
                Likes mdb = db[i];
                System.out.println(String.format("%-12s %-15s %s ", mdb.getMember_id() , mdb.getProfile_name(), mdb.getM_genre()));
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
        String sql = "INSERT into Likes(member_id, profile_name, m_genre) VALUES(?,?,?)";
        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,member_id);
        statement.setString(2,profile_name);
        statement.setString(3, m_genre);
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
     * Update table.
     */
    public void updateTable() throws SQLException{
        System.out.print("WHERE TO UPDATE:");
        setVariables();
        System.out.println("UPDATE TO:");
        System.out.println("Enter new member_id:");
        String temp1 = scan.next();

        System.out.println("Enter new profile_name:");
        String temp2 = scan.next();

        System.out.println("Enter new movie_genre:");
        String temp3 = scan.next();

        // get the connection
        Connection connection = getConnection();
        // create the INSERT SQL
        String sql = "UPDATE Likes SET member_id = ? , profile_name = ?, m_genre = ? WHERE member_id = ? AND profile_name =? AND m_genre =?";

        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,temp1);
        statement.setString(2,temp2);
        statement.setString(3,temp3);
        statement.setString(4,member_id);
        statement.setString(5,profile_name);
        statement.setString(6,m_genre);
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
     * Delete a data
     * @throws SQLException
     */
    public void deleteData() throws SQLException {
        System.out.print("WHERE TO DELETE:");
        setVariables();
        // get the connection
        Connection connection = getConnection();
        // create the INSERT SQL
        String sql = "DELETE FROM Likes WHERE member_id = ? AND profile_name = ? AND m_genre = ?";

        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,member_id);
        statement.setString(2,profile_name);
        statement.setString(3, m_genre);
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
     * Set instances variables
     */
    public void setVariables(){
        System.out.print("\nEnter member_id:");
        setMember_id(scan.next());
        System.out.print("Enter profile_name:");
        setProfile_name(scan.next());
        System.out.print("Enter movie_genre:");
        setM_genre(scan.next());
        System.out.println("\n");
    }

    /*Getters Setters*/
    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }
    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }
    public void setM_genre(String m_genre) {
        this.m_genre = m_genre;
    }
    public String getMember_id() {
        return member_id;
    }
    public String getProfile_name() {
        return profile_name;
    }
    public String getM_genre() {
        return m_genre;
    }

}
