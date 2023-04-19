package pet;

import database.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PetRepository {
    private final ConnectionManager connectionManager;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public PetRepository() {
        this.connectionManager = new ConnectionManager();
    }


    public ArrayList<Pet> getAllPets() throws SQLException {

        ArrayList<Pet> pets = new ArrayList<>();

        String query = "SELECT * FROM pets";
        Connection connection = this.connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {

            pets.add(this.createPetFromResultSet(resultSet));

        }

        this.connectionManager.closeConnections(connection, resultSet, statement);
        return pets;
    }

    public Pet getPetByName(String petName) throws PetNotFoundException {
        try {
            String query = "SELECT * FROM pets WHERE petName = ?  LIMIT 1";
            this.connection = connectionManager.getConnection();
            this.statement = this.connection.prepareStatement(query);
            this.statement.setString(1, petName);
            this.resultSet = this.statement.executeQuery();
            if (resultSet.next()) return this.createPetFromResultSet((this.resultSet ));
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            this.connectionManager.closeConnections(this.connection, this.resultSet, this.statement);
        }
        throw new PetNotFoundException("Pet with name  " + petName + " not found");
    }

    public String createPet(Pet pet) {
        try {
            String query = "INSERT INTO pets(petName, birthDate, weight, petTypeId, ownerId) values(?, ?, ?, ?, ?)";
            this.connection = this.connectionManager.getConnection();
            this.statement = this.connection.prepareStatement(query);
            this.statement.setString(1, pet.getPetName());
            this.statement.setString(2, pet.getBirthDate());
            this.statement.setInt(3, pet.getWeight());
            this.statement.setInt(4, pet.getPetTypeId());
            this.statement.setInt(5, pet.getOwnerId());
            if (this.statement.executeUpdate() == 1) return "Pet created successfully.";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connectionManager.closeConnections(this.connection, null, this.statement);
        }
        return "Failed to create pet.";
    }


    public String updatePet(Pet pet) {
        try {
            String query = "UPDATE pets SET petName =?, birthDate = ?, weight = ?, ownerId=? WHERE petTypeId =?";
            this.connection = this.connectionManager.getConnection();
            this.statement = this.connection.prepareStatement(query);

            this.statement.setString(1, pet.getPetName());
            this.statement.setString(2, pet.getBirthDate());
            this.statement.setInt(3, pet.getWeight());
            this.statement.setInt(4, pet.getPetTypeId());
            this.statement.setInt(5, pet.getOwnerId());
            if (this.statement.executeUpdate() == 1) return "Pet updated successfully.";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connectionManager.closeConnections(this.connection, null, this.statement);
        }
        return "Failed to update pet.";
    }


    public String deletePet(String petName) {
        try {
            String query = "DELETE FROM  pets WHERE petName = ?";
            this.connection = this.connectionManager.getConnection();
            this.statement = this.connection.prepareStatement(query);

            this.statement.setString(1, petName);

            if (this.statement.executeUpdate() == 1) return "Pet deleted successfully.";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.connectionManager.closeConnections(this.connection, null, this.statement);
        }
        return "Failed to deleted pet.";
    }


    private Pet createPetFromResultSet(ResultSet resultSet) throws SQLException {
        return new Pet(
                resultSet.getInt("petTypeId"),
                resultSet.getString("petName"),
                resultSet.getString("birthDate"),
                resultSet.getInt("weight"),
                resultSet.getInt("ownerId"));
    }
}