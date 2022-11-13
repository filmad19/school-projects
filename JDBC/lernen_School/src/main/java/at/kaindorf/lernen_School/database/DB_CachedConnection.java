package at.kaindorf.lernen_School.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class DB_CachedConnection {
    private LinkedList<Statement> statementQueue = new LinkedList<>();
    private Connection connection;

    public DB_CachedConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement getStatement() throws SQLException {
        if(connection == null || connection.isClosed()){
            throw new RuntimeException("connect to database first");
        }

        if(statementQueue.isEmpty()){
            return connection.createStatement();
        }

        return statementQueue.poll();
    }

    public void releaseStatement(Statement statement){
        statementQueue.offer(statement);
    }
}
