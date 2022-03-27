import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.*; //JDBC packages

/**
 * This class control the GUI of the program
 * @author Siam A-mer Chowdhury
 */
public class GUI_Controller {

    Actor actor;
    Member mem;
    Genre genre;
    Starred_by starred_by;
    Movie_genre movie_genre;
    Profile profile;
    Watch watch;
    Movie movie;
    Likes likes;

    // DB connection properties
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String jdbc_url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";

    // Login information
    private String username = "xxx";
    private String password = "xxx";

    private int choice;
    Scanner scan = new Scanner(System.in);



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
     * Goodbye msg to user.
     */
    public static void end_msg(){
        System.out.println("Thank you for using our service");
        System.out.println("Goodbye.");
    }

    /**
     * Display login msg.
     */
    public static void login_promt(){
        System.out.println("\n\nWelcome to the best DBMS for movie app");
        System.out.println("Please choose one of the following options:");
    }

    /**
     * Display menu prompt.
     */
    public void menu(){
        System.out.println("\n\t\t\t1 - View Table Content\n" +
                "          \t2 - Insert New Record\n" +
                "          \t3 - Update Record\n" +
                "          \t4 - Search for movies\n" +
                "          \t5 - Show rental history\n" +
                "          \t6 - Exit");
    }

    /**
     * Display list of tables.
     */
    public void table_list() throws SQLException {
        // get the connection
        Connection connection = getConnection();
        // create the SELECT SQL
        StringBuffer sbSelect = new StringBuffer();
        sbSelect.append(" SELECT table_name FROM user_tables ORDER BY table_name");

        Statement statement = null;
        ResultSet rs = null;

        try
        {
            // create the statement
            statement = connection.createStatement();
            // Insert the data
            rs = statement.executeQuery(sbSelect.toString());
            System.out.println("+-----------------------+\n" + "|\t\tTables\t\t\t|\n" + "+-----------------------+");
            if (rs != null) {
                // when the resultset is not null, there are records returned
                while (rs.next()) {
                    System.out.print(String.format("|  %-21s|\n",rs.getString(1) ));
                }
                System.out.print("+-----------------------+\n");
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

}

    /**
     * Display the content of a table.
     * @param table_name name of the table.
     */
    public void display_table_content(String table_name){
        if(table_name.toLowerCase().equals("member")){
            mem.print_table_content();
        }
        else if(table_name.toLowerCase().equals("movie")){
            movie.print_table_content();
        }
        else if(table_name.toLowerCase().equals("profile")){
            profile.print_table_content();
        }
        else if(table_name.toLowerCase().equals("actor")){
            actor.print_table_content();
        }
        else if(table_name.toLowerCase().equals("genre")){
            genre.print_table_content();
        }
        else if(table_name.toLowerCase().equals("watch")){
            watch.print_table_content();
        }
        else if(table_name.toLowerCase().equals("likes")){
            likes.print_table_content();
        }
        else if(table_name.toLowerCase().equals("movie_genre")){
            movie_genre.print_table_content();
        }
        else if(table_name.toLowerCase().equals("starred_by")){
            starred_by.print_table_content();
        }  else {
            System.out.println("Table name not found.");
        }
    }

    /**
     * Get the table name from user.
     */
    public String get_table_name() throws SQLException {
        String table_name;
        System.out.print("Please enter the table name:\n>\t");
        table_name = scan.next();
        return table_name;
    }

    /**
     * Insert into table
     * @param table_name
     * @throws SQLException
     */
    public void insert_into_table(String table_name) throws SQLException {
        if(table_name.toLowerCase().equals("member")){
            mem.insertData();
        }
        else if(table_name.toLowerCase().equals("movie")){
            movie.insertData();
        }
        else if(table_name.toLowerCase().equals("profile")){
            profile.insertData();
        }
        else if(table_name.toLowerCase().equals("actor")){
            actor.insertData();
        }
        else if(table_name.toLowerCase().equals("genre")){
            genre.insertData();
        }
        else if(table_name.toLowerCase().equals("watch")){
            watch.insertData();
        }
        else if(table_name.toLowerCase().equals("likes")){
            likes.insertData();
        }
        else if(table_name.toLowerCase().equals("movie_genre")){
            movie_genre.insertData();
        }
        else if(table_name.toLowerCase().equals("starred_by")){
            starred_by.insertData();
        }
        else{
            System.out.println("Table name not found.");
        }
    }

    /**
     * Update table.
     * @param table_name
     */
    public void update_from_table(String table_name) throws SQLException {
        if(table_name.toLowerCase().equals("member")){
            mem.updateTable();
        }
        else if(table_name.toLowerCase().equals("movie")){
            movie.updateTable();
        }
        else if(table_name.toLowerCase().equals("profile")){
            profile.updateTable();
        }
        else if(table_name.toLowerCase().equals("actor")){
            actor.updateTable();
        }
        else if(table_name.toLowerCase().equals("genre")){
            genre.updateTable();
        }
        else if(table_name.toLowerCase().equals("watch")){
            watch.updateTable();
        }
        else if(table_name.toLowerCase().equals("likes")){
            likes.updateTable();
        }
        else if(table_name.toLowerCase().equals("movie_genre")){
            movie_genre.updateTable();
        }
        else if(table_name.toLowerCase().equals("starred_by")){
            starred_by.updateTable();
        }
        else{
            System.out.println("Table name not found.");
        }
    }

    /**
     * Delete from table
     * @param table_name
     */
    public void delete_from_table(String table_name) throws SQLException {
        if(table_name.toLowerCase().equals("member")){
            mem.deleteData();
        }
        else if(table_name.toLowerCase().equals("movie")){
            movie.deleteData();
        }
        else if(table_name.toLowerCase().equals("profile")){
            profile.deleteData();
        }
        else if(table_name.toLowerCase().equals("actor")){
            actor.deleteData();
        }
        else if(table_name.toLowerCase().equals("genre")){
            genre.deleteData();
        }
        else if(table_name.toLowerCase().equals("watch")){
            watch.deleteData();
        }
        else if(table_name.toLowerCase().equals("likes")){
            likes.deleteData();
        }
        else if(table_name.toLowerCase().equals("movie_genre")){
            movie_genre.deleteData();
        }
        else if(table_name.toLowerCase().equals("starred_by")){
            starred_by.deleteData();
        }
        else{
            System.out.println("Table name not found.");
        }
    }

    public void update_or_delete() throws SQLException {
        System.out.println("Choose one of the option:");
        System.out.println("1. Update");
        System.out.print("2. Delete\n>\t");
        int choice = scan.nextInt();
        if (choice == 1){
            update_from_table(get_table_name());
        }
        else if(choice == 2){
            delete_from_table(get_table_name());
        }
        else{
            return;
        }
    }

    private void showHistory() throws SQLException {
        watch.getRentalHistory();
    }

    public void search_options() throws SQLException{
        System.out.println("\n\t\t\t1 - Search for movie based on title\n" +
                "           \t2 - Search for movie based on actor name\n" +
                "           \t3 - Exit");
        int option=scan.nextInt();
        if(option==1){
            movie.search_for_movie();
        }
        else if (option==2){
            actor.search_for_movie_actor();
        }
        else {
            return;
        }
    }

    public int login(){
        System.out.print("Username: ");
        setUsername(scan.next());
        System.out.print("Password: ");
        setPassword(scan.next());
        if(getConnection() != null){
            mem = new Member(getUsername(), getPassword());
            actor = new Actor(getUsername(), getPassword());
            genre = new Genre(getUsername(), getPassword());
            starred_by = new Starred_by(getUsername(), getPassword());
            movie_genre = new Movie_genre(getUsername(), getPassword());
            profile = new Profile(getUsername(), getPassword());
            watch = new Watch(getUsername(), getPassword());
            movie = new Movie(getUsername(), getPassword());
            likes = new Likes(getUsername(), getPassword());
            return 0;
        }
        return -1;
    }



    /**
     * Main method.
     */
    public static void main(String[] args) throws SQLException {
        int choice;
        Scanner scan = new Scanner(System.in);
        GUI_Controller gui = new GUI_Controller();
        while(gui.login() == -1){
            System.out.println("Invalid login try again");
        }
        gui.login_promt();
        while(true){
            System.out.println("********************************************************************");
            gui.menu();
            System.out.print(">\t");
            choice = scan.nextInt();
            switch (choice){
                case 1:
                    gui.table_list();
                    gui.display_table_content(gui.get_table_name());
                    break;
                case 2:
                    gui.insert_into_table(gui.get_table_name());
                    break;
                case 3:
                    gui.update_or_delete();
                    break;
                case 4:
                    gui.search_options();
                    break;
                case 5:
                    gui.showHistory();
                    break;
                case 6:
                    gui.end_msg();
                    scan.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid input try again");
            }
        }

    }



    /*Getters Setters*/
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
