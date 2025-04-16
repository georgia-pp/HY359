/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase.Tables;

import com.google.gson.Gson;
import mainClasses.PetKeeper;
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
public class EditPetKeepersTable {

    public void addPetKeeperFromJSON(String json) throws ClassNotFoundException, SQLException {
        PetKeeper user = jsonToPetKeeper(json);
        addNewPetKeeper(user);
    }

    public PetKeeper jsonToPetKeeper(String json) {
        Gson gson = new Gson();

        PetKeeper user = gson.fromJson(json, PetKeeper.class);
        return user;
    }

    public String petKeeperToJSON(PetKeeper user) {
        Gson gson = new Gson();

        String json = gson.toJson(user, PetKeeper.class);
        return json;
    }

    public void updatePetKeeper(String username, String type, String value) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update = "UPDATE petkeepers SET " + type + "='" + value + "' WHERE username = '" + username + "'";
        stmt.executeUpdate(update);
    }

    public void printPetKeeperDetails(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE username = '" + username + "' AND password='" + password + "'");
            while (rs.next()) {
                System.out.println("===Result===");
                DB_Connection.printResults(rs);
            }

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    public PetKeeper databaseToPetKeepers(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE username = '" + username + "' AND password='" + password + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            PetKeeper user = gson.fromJson(json, PetKeeper.class);
            return user;
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

    public PetKeeper checkusernameORemail(String username, String email) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE username = '" + username + "' OR email='" + email + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            PetKeeper user = gson.fromJson(json, PetKeeper.class);
            return user;
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

    public PetKeeper checkusername(String username) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE username = '" + username + "'");
//            System.out.println(rs.next());
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            PetKeeper user = gson.fromJson(json, PetKeeper.class);
            return user;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void deleteKeeper(String id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String deleteQuery = "DELETE FROM petkeepers WHERE keeper_id='" + id + "'";
        stmt.executeUpdate(deleteQuery);
        stmt.close();
        con.close();
    }

    /*   public ArrayList<PetKeeper> getAvailableKeepers(String type) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<PetKeeper> keepers = new ArrayList<PetKeeper>();
        ResultSet rs = null;
        try {
            //if(type=="catkeeper")
            if ("all".equals(type)) {
                rs = stmt.executeQuery("SELECT * FROM `petKeepers` WHERE  `petKeepers`.`keeper_id` not in (select keeper_id "
                        + "from `bookings` where `status`='requested' or  `status`='accepted')\n" + "");
            } else if ("catKeepers".equals(type)) {
                rs = stmt.executeQuery("SELECT * FROM `petKeepers` WHERE `petKeepers`.`catkeeper`='true' AND `petKeepers`.`keeper_id` not in (select keeper_id "
                        + "from `bookings` where `status`='requested' or  `status`='accepted')");
            } else if ("dogKeepers".equals(type)) {
                rs = stmt.executeQuery("SELECT * FROM `petKeepers` WHERE `petKeepers`.`dogkeeper`='true' AND `petKeepers`.`keeper_id` not in (select keeper_id "
                        + "from `bookings` where `status`='requested' or  `status`='accepted')");
            }

            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                PetKeeper keeper = gson.fromJson(json, PetKeeper.class);
                keepers.add(keeper);
            }
            return keepers;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }*/
    public ArrayList<PetKeeper> getAvailableKeepers(String type) throws SQLException, ClassNotFoundException {
        ArrayList<PetKeeper> keepers = new ArrayList<>();
        String query = null;

        try (Connection con = DB_Connection.getConnection(); Statement stmt = con.createStatement()) {

            if ("cat".equals(type)) {
                query = "SELECT * FROM petkeepers WHERE catkeeper = ? AND keeper_id NOT IN (SELECT keeper_id FROM bookings WHERE status='accepted' OR status='requested')";
            } else if ("dog".equals(type)) {
                query = "SELECT * FROM petkeepers WHERE dogkeeper = ? AND keeper_id NOT IN (SELECT keeper_id FROM bookings WHERE status='accepted' OR status='requested')";
            }

            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setBoolean(1, true);

                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        String json = DB_Connection.getResultsToJSON(rs);
                        Gson gson = new Gson();
                        PetKeeper keeper = gson.fromJson(json, PetKeeper.class);
                        keepers.add(keeper);
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

        return keepers;
    }

    public ArrayList<PetKeeper> getKeepers(String type) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<PetKeeper> keepers = new ArrayList<PetKeeper>();
        ResultSet rs = null;
        try {
            if ("cat".equals(type)) {
                rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE catkeeper= '" + "true" + "'");
            } else if ("dog".equals(type)) {
                rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE dogkeeper= '" + "true" + "'");
            }

            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                PetKeeper keeper = gson.fromJson(json, PetKeeper.class);
                keepers.add(keeper);
            }
            return keepers;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public String databasePetKeeperToJSON(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE username = '" + username + "' AND password='" + password + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            return json;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void createPetKeepersTable() throws SQLException, ClassNotFoundException {

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE petkeepers "
                + "(keeper_id INTEGER not NULL AUTO_INCREMENT, "
                + "    username VARCHAR(30) not null unique,"
                + "    email VARCHAR(50) not null unique,	"
                + "    password VARCHAR(32) not null,"
                + "    firstname VARCHAR(30) not null,"
                + "    lastname VARCHAR(30) not null,"
                + "    birthdate DATE not null,"
                + "    gender  VARCHAR (7) not null,"
                + "    country VARCHAR(30) not null,"
                + "    city VARCHAR(50) not null,"
                + "    address VARCHAR(50) not null,"
                + "    personalpage VARCHAR(200) not null,"
                + "    job VARCHAR(200) not null,"
                + "    telephone VARCHAR(14),"
                + "    lat DOUBLE,"
                + "    lon DOUBLE,"
                + "    property VARCHAR(10) not null,"
                + "    propertydescription VARCHAR(200),"
                + "    catkeeper VARCHAR(10) not null,"
                + "    dogkeeper VARCHAR(10) not null,"
                + "    catprice INTEGER,"
                + "    dogprice INTEGER,"
                + " PRIMARY KEY (keeper_id))";
        stmt.execute(query);
        stmt.close();
        con.close();
    }

    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public void addNewPetKeeper(PetKeeper user) throws ClassNotFoundException, SQLException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String insertQuery = "INSERT INTO "
                + " petkeepers (username,email,password,firstname,lastname,birthdate,gender,country,city,address,personalpage,"
                + "job,telephone,lat,lon,property,propertydescription,catkeeper,dogkeeper,catprice,dogprice)"
                + " VALUES ("
                + "'" + user.getUsername() + "',"
                + "'" + user.getEmail() + "',"
                + "'" + user.getPassword() + "',"
                + "'" + user.getFirstname() + "',"
                + "'" + user.getLastname() + "',"
                + "'" + user.getBirthdate() + "',"
                + "'" + user.getGender() + "',"
                + "'" + user.getCountry() + "',"
                + "'" + user.getCity() + "',"
                + "'" + user.getAddress() + "',"
                + "'" + user.getPersonalpage() + "',"
                + "'" + user.getJob() + "',"
                + "'" + user.getTelephone() + "',"
                + "'0',"
                + "'0',"
                + "'" + user.getProperty() + "',"
                + "'" + user.getPropertydescription() + "',"
                + "'" + user.getCatkeeper() + "',"
                + "'" + user.getDogkeeper() + "',"
                + "'" + user.getCatprice() + "',"
                + "'" + user.getDogprice() + "'"
                + ")";
        System.out.println("Insertion query: " + insertQuery);
        try {
            stmt.executeUpdate(insertQuery);

        } catch (SQLException ex) {
            Logger.getLogger(EditPetKeepersTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        stmt.close();
    }

    public ArrayList<PetKeeper> getAllKeepers() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<PetKeeper> keepers = new ArrayList<>();
        ResultSet rs = null;
        rs = stmt.executeQuery("SELECT * FROM petkeepers");
        while (rs.next()) {
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            PetKeeper keeper = gson.fromJson(json, PetKeeper.class);
            keepers.add(keeper);
        }
        return keepers;
    }

    public PetKeeper findPetKeeper(String username, String email) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM petkeepers WHERE username='" + username + "' OR email='" + email + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            PetKeeper keeper = gson.fromJson(json, PetKeeper.class);
            return keeper;
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

    public int getNumber() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        int total = 0;

        String query = "SELECT COUNT(*) AS total FROM petkeepers";
        try (PreparedStatement stmt = con.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                total = rs.getInt("total");
            }
        }

        return total;
    }

    public int getCost(int id, String type) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        int total = 0;
        String query = "";

        if ("cat".equals(type)) {
            query = "SELECT catprice FROM petkeepers WHERE keeper_id=?";
        } else if ("dog".equals(type)) {
            query = "SELECT dogprice FROM petkeepers WHERE keeper_id=?";
        }

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }
        }

        return total;
    }
}
