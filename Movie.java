import java.sql.*;
import java.util.*;
/**
 * A class to represent Movie objects.
 * @author Siam Al-mer Chowdhury
 */
public class Movie {
    Scanner scan = new Scanner(System.in);

    private String movie_id;
    private String title;
    private String movie_year;
    private String producer;
    private String avg_rating;

    // DB connection properties
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String jdbc_url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";

    // Login information
    private String username = "xxx";
    private String password = "xxx";

    public Movie(String username, String password){
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
            //
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
    public Movie[] loadAll() throws SQLException {
        // get the connection
        Connection connection = getConnection();

        // create the SELECT SQL
        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append(" SELECT movie_id, title, movie_year, producer, avg_rating FROM Movie ORDER BY movie_id");

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
                    Movie movie = new Movie(this.username, this.password);
                    movie.setMovie_id(rs.getString("movie_id"));
                    movie.setTitle(rs.getString("title"));
                    movie.setMovie_year(rs.getString("movie_year"));
                    movie.setProducer(rs.getString("producer"));
                    movie.setAvg_rating(rs.getString("avg_rating"));

                    // store it in the list
                    collection.add(movie);
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
        return (Movie[])collection.toArray(new Movie[0]);
    }

    /**
     * Display the table content
     */
    public void print_table_content(){
        // print out all information on the Member table
        Movie[] db;
        try {
            db = loadAll();
            System.out.println("\nMOVIE_ID \t TITLE \t\t\tMOVIE_YEAR \t PRODUCER \t\t\tAVG_RATING");
            for (int i = 0; i <db.length; i++) {
                Movie mdb = db[i];
                System.out.println(String.format("%-12s %-15s %-11s %-23s %s", mdb.getMovie_id() , mdb.getTitle(), mdb.getMovie_year(), mdb.getProducer(), mdb.getAvg_rating()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        String sql = "INSERT into Movie(movie_id, title, movie_year, producer, avg_rating) VALUES(?,?,?,?,?)";
        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,movie_id);
        statement.setString(2,title);
        statement.setString(3,movie_year);
        statement.setString(4,producer);
        statement.setString(5,avg_rating);
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
        System.out.print("\nEnter movie_id:");
        setMovie_id(scan.next());

        System.out.print("Enter title:");
        setTitle(scan.next());

        System.out.print("Enter movie_year:");
        setMovie_year(scan.next());



        System.out.print("Enter producer:");
        setProducer(scan.next());

        System.out.print("Enter avg_rating:");
        setAvg_rating(scan.next());
        System.out.println("\n");
    }
    public void updateTable() throws SQLException{
        System.out.println("For update, please provide the movie_id to update on\nThen enter in new attributes after that");
        System.out.print("====================================================");
        setVariables();
        // get the connection
        Connection connection = getConnection();
        // create the INSERT SQL
        String sql = "UPDATE Movie SET title = ? ,movie_year = ?, producer = ?, avg_rating = ? WHERE movie_id = ?";
        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,title);
        statement.setString(2,movie_year);
        statement.setString(3,producer);
        statement.setString(4,avg_rating);
        statement.setString(5,movie_id);
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
        System.out.print("Enter the ID of the movie you want to delete:\n>\t");
        setMovie_id(scan.next());
        // get the connection
        Connection connection = getConnection();
        // create the INSERT SQL
        String sql = "DELETE FROM Movie WHERE movie_id = ?";
        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,movie_id);
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

    public void search_for_movie() throws SQLException{
        System.out.print("Enter the Movie title:\n>\t");
        setTitle(scan.nextLine());

        // get the connection
        Connection connection = getConnection();
        // create the INSERT SQL
        String sql = "SELECT * from Movie WHERE title= ?";
        // create the statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,title);
        try
        {
            ResultSet rs = statement.executeQuery();
            System.out.println("\nMOVIE_ID \t TITLE \t\t\tMOVIE_YEAR \t PRODUCER \t\t\tAVG_RATING");
            while(rs.next()){
                System.out.println(String.format("%-12s %-15s %-11s %-23s %s", rs.getString(1),
                        rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5)));


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



    // Getter and setter
    public String getMovie_id(){
        return movie_id;
    }
    public String getTitle(){
        return title;
    }
    public String getMovie_year() {
        return movie_year;
    }
    public String getProducer() {
        return producer;
    }
    public String getAvg_rating() {
        return avg_rating;
    }
    public void setMovie_id(String movie_id) {

        this.movie_id = movie_id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setMovie_year(String movie_year) {

        this.movie_year = movie_year;
    }
    public void setProducer(String producer) {

        this.producer = producer;
    }
    public void setAvg_rating(String avg_rating) {
        this.avg_rating = avg_rating;
    }


    public static void main ( String[] args){

    }
}
