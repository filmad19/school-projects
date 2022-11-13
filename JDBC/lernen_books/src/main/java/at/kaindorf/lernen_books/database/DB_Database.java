package at.kaindorf.lernen_books.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_Database {
//    PROPERTIES
    String dbUrl = DB_Properties.getProperty("db_url");
    String dbDriver = DB_Properties.getProperty("db_driver");
    String dbUsername = DB_Properties.getProperty("db_username");
    String dbPassword = DB_Properties.getProperty("db_password");

//    OTHER OBJECTS
    private DB_CachedConnection cachedConnection;
    private Connection connection;

//    SINGLETON
    private static DB_Database theInstance;

    private DB_Database() {
        try {
            Class.forName(dbDriver);
            connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DB_Database getInstance(){
        if(theInstance == null){
            theInstance = new DB_Database();
        }
        return theInstance;
    }
////////////////////////////////////


    public void connect() throws SQLException {
        disconnect();
        connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        cachedConnection = new DB_CachedConnection(connection);
    }
 
    public void disconnect() throws SQLException {
        if(connection != null){
            connection.close();
            connection = null;
        }
    }
////////////////////////////////////

    public Connection getConnection(){
        return connection;
    }

    public Statement getStatement() throws SQLException {
        return cachedConnection.getStatement();
    }

    public void releaseStatement(Statement statement){
        cachedConnection.releaseStatement(statement);
    }
}
