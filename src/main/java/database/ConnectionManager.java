package database;

import org.apache.commons.configuration.PropertiesConfiguration;

import java.sql.*;

public class ConnectionManager {
    PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
private Connection connection;
    public ConnectionManager() {
        try {
            propertiesConfiguration.load("database.properties");
            this.setupConnection();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void setupConnection() throws SQLException {
        String connectionUrl = this.propertiesConfiguration.getString("database.host") + ":"
                + this.propertiesConfiguration.getString("database.port") + "/"
                + this.propertiesConfiguration.getString("database.name");


        this.connection = DriverManager.getConnection(connectionUrl,
                this.propertiesConfiguration.getString("database.username"),
                this.propertiesConfiguration.getString("database.password"));
    }
    public Connection getConnection() throws SQLException {
        if(this.connection == null || this.connection.isClosed())this.setupConnection();
        return this.connection;
    }

    public void closeConnections(Connection connection, ResultSet resutSet, PreparedStatement statement) {
        try{
           if( connection != null) connection.close();
            if( resutSet != null) resutSet.close();
            if( statement != null) statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
