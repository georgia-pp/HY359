/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase.Tables;

import mainClasses.Pet;
import com.google.gson.Gson;
import DataBase.DB_Connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mike
 */
public class EditPetsTable {

    public void addPetFromJSON(String json) throws ClassNotFoundException, SQLException {
        Pet bt = jsonToPet(json);
        addNewPet(bt);
    }

    public Pet jsonToPet(String json) {
        Gson gson = new Gson();
        Pet btest = gson.fromJson(json, Pet.class);
        return btest;
    }

    public String petToJSON(Pet bt) {
        Gson gson = new Gson();

        String json = gson.toJson(bt, Pet.class);
        return json;
    }

    public ArrayList<Pet> databaseToPets() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Pet> pets = new ArrayList<Pet>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM pets");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Pet pet = gson.fromJson(json, Pet.class);
                pets.add(pet);
            }
            return pets;

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Pet petOfOwner(String id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        Pet pet = new Pet();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM pets WHERE owner_id= '" + id + "'");

            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                pet = gson.fromJson(json, Pet.class);

            }
            return pet;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Pet> databaseToPets(String type) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Pet> pets = new ArrayList<Pet>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM pets WHERE type= '" + type + "'");

            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Pet pet = gson.fromJson(json, Pet.class);
                pets.add(pet);
            }
            return pets;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void updatePet(String id, String name) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        Pet bt = new Pet();

        String update = "UPDATE pets SET name='" + name + "'" + "WHERE pet_id = '" + id + "'";
        //stmt.executeUpdate(update);
    }

    public void deletePet(String id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String deleteQuery = "DELETE FROM pets WHERE pet_id='" + id + "'";
        stmt.executeUpdate(deleteQuery);
        stmt.close();
        con.close();
    }

    public void createPetsTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE pets "
                + "(pet_id VARCHAR(10) not NULL unique, "
                + "owner_id INTEGER not null,"
                + "name VARCHAR(30) not null,"
                + "type VARCHAR(3)  not null, "
                + "breed VARCHAR(30)  not null, "
                + "gender VARCHAR(7)  not null, "
                + "birthyear INTEGER not null , "
                + "weight DOUBLE not null , "
                + "description VARCHAR (500), "
                + "photo VARCHAR (300), "
                + "FOREIGN KEY (owner_id) REFERENCES petowners(owner_id) ON DELETE CASCADE, "
                + "PRIMARY KEY (pet_id ))";
        stmt.execute(sql);
        stmt.close();
        con.close();

    }

    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public void addNewPet(Pet bt) throws ClassNotFoundException, SQLException {

        Connection con = DB_Connection.getConnection();

        Statement stmt = con.createStatement();

        String insertQuery = "INSERT INTO "
                + " pets (pet_id,owner_id,name,type,breed,gender,birthyear,weight,description,photo) "
                + " VALUES ("
                + "'" + bt.getPet_id() + "',"
                + "'" + bt.getOwner_id() + "',"
                + "'" + bt.getName() + "',"
                + "'" + bt.getType() + "',"
                + "'" + bt.getBreed() + "',"
                + "'" + bt.getGender() + "',"
                + "'" + bt.getBirthyear() + "',"
                + "'" + bt.getWeight() + "',"
                + "'" + bt.getDescription() + "',"
                + "'" + bt.getPhoto() + "'"
                + ")";

        System.out.println("Insertion query: " + insertQuery);
        try {
            stmt.executeUpdate(insertQuery);

        } catch (SQLException ex) {
            Logger.getLogger(EditPetKeepersTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        stmt.close();
    }

    public Pet findPet(String id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM pets WHERE pet_id='" + id + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Pet pet = gson.fromJson(json, Pet.class);
            return pet;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public Pet petOfKeeper(String id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Pet pet = null;

        String query = "SELECT * FROM pets WHERE pet_id IN (SELECT pet_id FROM bookings WHERE keeper_id='" + id + "' AND status='requested')";

        try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                System.out.println("JSON: " + json);
                Gson gson = new Gson();
                pet = gson.fromJson(json, Pet.class);
            }
        }
        return pet;
    }

    public void deletePet_Owner(String id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String deleteQuery = "DELETE FROM pets WHERE owner_id='" + id + "'";
        stmt.executeUpdate(deleteQuery);
        stmt.close();
        con.close();
    }

    public int getNumberCats() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        int total = 0;

        String query = "SELECT COUNT(*) AS total FROM pets WHERE type='cat'";
        try (PreparedStatement stmt = con.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                total = rs.getInt("total");
            }
        }

        return total;
    }

    public int getNumberDogs() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        int total = 0;

        String query = "SELECT COUNT(*) AS total FROM pets WHERE type='dog'";
        try (PreparedStatement stmt = con.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                total = rs.getInt("total");
            }
        }

        return total;
    }
}
