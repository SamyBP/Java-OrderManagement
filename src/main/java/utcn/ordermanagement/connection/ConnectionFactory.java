package utcn.ordermanagement.connection;

import java.sql.*;
/**
 * A utility class responsible for managing database connections
 */
public class ConnectionFactory {
    private static ConnectionFactory connectionFactory = new ConnectionFactory();

    /**
     * Private constructor to prevent instantiation from outside the class
     */
    private ConnectionFactory() {
        try {
            Class.forName(PropertiesLoader.loadDriver());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Private method used from inside the class to create a new database connection
     *
     * @return A connection object representing the connection to the database
     * @throws RuntimeException if an error occurs while getting the connection
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection =  DriverManager.getConnection(
                    PropertiesLoader.loadURL(),
                    PropertiesLoader.loadUser(),
                    PropertiesLoader.loadPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    /**
     * Gets a connection to the database.
     *
     * @return A connection object representing the connection to the database
     * @throws RuntimeException if an error occurs while getting the connection
     *
     * @implNote This method should be called from outside the class to obtain a connection
     * Example usage: Connection con = ConnectionFactory.getConnection();
     *
     */
    public static Connection getConnection() {
        return connectionFactory.createConnection();
    }

    /**
     * Closes the database connection.
     *
     * @param connection The Connection object to be closed.
     * @throws RuntimeException if a database access error occurs.
     *
     * @implNote This method should be called from outside the class to close a connection
     * Example usage: ConnectionFactory.close(con);
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * Closes the PreparedStatement.
     *
     * @param statement The PreparedStatement to be closed.
     * @throws RuntimeException if a database access error occurs.
     *
     * @implNote This method should be called from outside the class to close a Statement
     * Example usage: ConnectionFactory.close(statement);
     */
    public static void close(PreparedStatement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Closes the PreparedStatement.
     *
     * @param resultSet The PreparedStatement to be closed.
     * @throws RuntimeException if a database access error occurs.
     *
     * @implNote This method should be called from outside the class to close a ResultSet
     * Example usage: ConnectionFactory.close(resultSet);
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }
    }
}
