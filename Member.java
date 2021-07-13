//Import packages
import java.sql.*; //JDBC packages
import java.util.*;

/**
 * This class control the Member table from SQL
 * @author Siam Al-mer Chowdhury, Steve Tran
 */
public class Member {
    Scanner scan = new Scanner(System.in);
    // instance variables
    private String member_id;
    private String first_name;
    private String last_name;
    private String card_number;
    private String exp_date;
    // DB connection properties
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String jdbc_url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";

    // Login information
    private String username = "xxx";
    private String password = "xxx";

    public Member(String username, String password){
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
     * Get everything in the sql table and store it
     * @return entire member array by id.
     * @throws SQLException
     */
    public Member[] loadAll() throws SQLException {
        // get the connection
        Connection connection = getConnection();

        // create the SELECT SQL
        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append(" SELECT member_id, first_name, last_name, card_number, exp_date FROM Member ORDER BY member_id");

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
                    Member member = new Member(this.username, this.password);
                    member.setMember_id(rs.getString("member_id"));
                    member.setFirst_name(rs.getString("first_name"));
                    member.setLast_name(rs.getString("last_name"));
                    member.setCard_number(rs.getString("card_number"));
                    member.setExp_date(rs.getString("exp_date"));

                    // store it in the list
                    collection.add(member);
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
        return (Member[])collection.toArray(new Member[0]);
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
        String sql = "INSERT into Member(member_id, first_name, last_name, card_number, exp_date) VALUES(?,?,?,?,?)";

        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,member_id);
        statement.setString(2,first_name);
        statement.setString(3,last_name);
        statement.setString(4,card_number);
        statement.setString(5,exp_date);
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

    public void setVariables(){
        System.out.print("\nEnter member_id:");
        setMember_id(scan.next());
        System.out.print("Enter first_name:");
        setFirst_name(scan.next());
        System.out.print("Enter last_name:");
        setLast_name(scan.next());
        System.out.print("Enter card_number:");
        setCard_number(scan.next());
        System.out.print("Enter exp_date:");
        setExp_date(scan.next());
        System.out.println("\n");
    }

    public void updateTable() throws SQLException{
        System.out.println("FOR UPDATE");
        setVariables();
        // get the connection
        Connection connection = getConnection();
        // create the INSERT SQL
        String sql = "UPDATE Member SET first_name = ? ,last_name = ?, card_number = ?, exp_date = ? WHERE member_id = ?";

        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,first_name);
        statement.setString(2,last_name);
        statement.setString(3,card_number);
        statement.setString(4,exp_date);
        statement.setString(5,member_id);

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
        // get the connection
        Connection connection = getConnection();
        // create the INSERT SQL
        String sql = "DELETE FROM Member WHERE member_id = ?";

        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,member_id);
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
     * Display the table content
     */
    public void print_table_content(){
        // print out all information on the Member table
        Member[] db;
        try {
            db = loadAll();
            System.out.println("\nMEMBER_ID \t FIRST_NAME \t LAST_NAME \t CARD_NUMBER \t EXP_DATE");
            for (int i = 0; i <db.length; i++) {
                Member mdb = db[i];
                System.out.println(String.format("%-12s %-15s %-11s %-11s %s", mdb.getMember_id() , mdb.getFirst_name(), mdb.getLast_name(), mdb.getCard_number(), mdb.getExp_date()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Getter and setter
    public String getMember_id(){
        return member_id;
    }
    public String getFirst_name() {
        return first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public String getCard_number() {
        return card_number;
    }
    public String getExp_date() {
        return exp_date;
    }
    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }
    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

}
