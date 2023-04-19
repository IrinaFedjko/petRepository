package owner;

import database.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OwnerRepository {
    private final ConnectionManager connectionManager;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public OwnerRepository() {
        this.connectionManager = new ConnectionManager();
    }

    /**
     * Basic CRUD operation with database
     * and more
     * usually repositories used by services and its common exceptions to service which  uses it
     */

    public ArrayList<Owner> getAllOwners() throws SQLException {

        ArrayList<Owner> owners = new ArrayList<>();

        String query = "SELECT * FROM owners";
        Connection connection = this.connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            //need to add into array

//            Owner owner = new Owner();
//            owner.setId(resultSet.getInt("id"));
//            owner.setOwnerName(resultSet.getString("ownerName"));
//            owner.setAge(resultSet.getInt("age"));
//            owner.setEmail(resultSet.getString("email"));
//            owner.setCreated (resultSet.getTimestamp("createdAt"));
//            owner.setUpdatedAt(resultSet.getTimestamp("updatedAt"));
            owners.add(this.createOwnerFromResultSet(resultSet));
//            System.out.println(owner);
//                    resultSet.getString("ownerName") + "-" +
//                    resultSet.getInt("age") + "-" +
//                    resultSet.getString("email") + "-" +
//                    resultSet.getTimestamp("createdAt") + "-" +
//                    resultSet.getTimestamp("updatedAt") + "-");

        }
        //close connection
//        connection.close();
//        resultSet.close();
//        statement.close();
        this.connectionManager.closeConnections(connection, resultSet, statement);
        return owners;
    }

    public Owner getOwnerByEmail(String email) throws OwnerNotFoundException {
        try {
            String query = "SELECT * FROM owners WHERE email = ?  LIMIT 1";
            this.connection = connectionManager.getConnection();
            this.statement = this.connection.prepareStatement(query);
            this.statement.setString(1, email);
            this.resultSet = this.statement.executeQuery();
            if (resultSet.next()) return this.createOwnerFromResultSet(this.resultSet);

            //it is possible to throw exception inside try block if exception will be thrown from method signarure
            //throw new OwnerNotFoundEXception()
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            //closing connection
            this.connectionManager.closeConnections(this.connection, this.resultSet, this.statement);
        }
        //if u will not throw exception, then need to return 0 or owner object
        throw new OwnerNotFoundException("owner with email address " + email + " not found");
    }

    public ArrayList<Owner> filterOwnerByAge(int startAge, int endAge) {
        ArrayList<Owner> owners = new ArrayList<>();
        try {
            String query = "SELECT *  FROM owners WHERE age >= ? AND age <= ? ORDER BY age ASC";
            this.connection = this.connectionManager.getConnection();
            this.statement = this.connection.prepareStatement(query);
            this.statement.setInt(1, startAge);
            this.statement.setInt(2, endAge);
            this.resultSet = statement.executeQuery();
            while (this.resultSet.next()) {
                owners.add(this.createOwnerFromResultSet(this.resultSet));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            this.connectionManager.closeConnections(this.connection, this.resultSet, this.statement);
        }
        return owners;

    }


    public Owner getOwnerById(Integer id) throws OwnerNotFoundException {
        try {
            String query = "SELECT * FROM owners WHERE id = ?  LIMIT 1";
            this.connection = connectionManager.getConnection();
            this.statement = this.connection.prepareStatement(query);
            this.statement.setInt(1, id);
            this.resultSet = this.statement.executeQuery();
            if (resultSet.next()) return this.createOwnerFromResultSet(this.resultSet);
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            this.connectionManager.closeConnections(this.connection, this.resultSet, this.statement);
        }
        throw new OwnerNotFoundException("owner with id address " + id + " not found");
    }

    public String createOwner(Owner owner) {
        try {
            String query = "INSERT INTO owners(ownerName, age, email) values(?, ?, ?)";
            this.connection = this.connectionManager.getConnection();
            this.statement = this.connection.prepareStatement(query);

            this.statement.setString(1, owner.getOwnerName());
            this.statement.setInt(2, owner.getAge());
            this.statement.setString(3, owner.getEmail());
            //we use execute update when we dont expect some resultset or query database for some results
            //in this case we want to add new items or chance item or remove item in database
            if (this.statement.executeUpdate() == 1) return "owner created successfully.";
            //line is same as above
//            int result = this.statement.executeUpdate();
            /**if (result ==1){ return "owner created successfully."} else {
             *  return "Failed to create owner."}
             *
             */
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            this.connectionManager.closeConnections(this.connection, null, this.statement);
        }
        return "Failed to create owner.";
    }





    public String updateOwner(Owner owner) {
//        int result;
        try {
            String query = "UPDATE owners SET ownerName =?, age = ?, email = ? WHERE id =?";
            this.connection = this.connectionManager.getConnection();
            this.statement = this.connection.prepareStatement(query);

            this.statement.setString(1, owner.getOwnerName());
            this.statement.setInt(2, owner.getAge());
            this.statement.setString(3, owner.getEmail());
            this.statement.setInt(4, owner.getId());
//            result = this.statement.executeUpdate();
           if (this.statement.executeUpdate() == 1) return "Owner updated successfully.";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connectionManager.closeConnections(this.connection, null, this.statement);
        }
        return "Failed to update owner.";
    }


    public String deleteOwner (Integer ownerId) {
        try {
            String query = "DELETE FROM  owners WHERE id = ?";
            this.connection = this.connectionManager.getConnection();
            this.statement = this.connection.prepareStatement(query);

            this.statement.setInt(1, ownerId);

            if (this.statement.executeUpdate() == 1) return "Owner deleted successfully.";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connectionManager.closeConnections(this.connection, null, this.statement);
        }
        return "Failed to deleted owner.";
    }



    private Owner createOwnerFromResultSet(ResultSet resutSet) throws SQLException {
        return new Owner(
                resutSet.getInt("id"),
                resutSet.getString("ownerName"),
                resutSet.getInt("age"),
                resutSet.getString("email"),
                resutSet.getTimestamp("createdAt"),
                resutSet.getTimestamp("updatedAt")
        );
    }
}
