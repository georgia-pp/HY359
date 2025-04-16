/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase.Tables;

import com.google.gson.Gson;
import mainClasses.Booking;
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
public class EditBookingsTable {

    public void addBookingFromJSON(String json) throws ClassNotFoundException {
        Booking r = jsonToBooking(json);
        createNewBooking(r);
    }

    public Booking databaseToBooking(int id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM bookings WHERE booking_id= '" + id + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Booking bt = gson.fromJson(json, Booking.class);
            return bt;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Booking jsonToBooking(String json) {
        Gson gson = new Gson();
        Booking r = gson.fromJson(json, Booking.class);
        return r;
    }

    public String bookingToJSON(Booking r) {
        Gson gson = new Gson();

        String json = gson.toJson(r, Booking.class);
        return json;
    }

    public void updateBooking(String id, String status) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String updateQuery = "UPDATE bookings SET status='" + status + "' WHERE keeper_id= '" + id + "'";
        stmt.executeUpdate(updateQuery);
        stmt.close();
        con.close();
    }

    public void updateBooking_byOwner(String id, String status) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String updateQuery = "UPDATE bookings SET status='" + status + "' WHERE owner_id= '" + id + "'";
        stmt.executeUpdate(updateQuery);
        stmt.close();
        con.close();
    }

    public void createBookingTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE bookings "
                + "(booking_id INTEGER not NULL AUTO_INCREMENT, "
                + " owner_id INTEGER not NULL, "
                + "  pet_id VARCHAR(10) not NULL, "
                + " keeper_id INTEGER not NULL, "
                + " fromdate DATE not NULL, "
                + " todate DATE not NULL, "
                + " status VARCHAR(15) not NULL, "
                + " price INTEGER not NULL, "
                + "FOREIGN KEY (owner_id) REFERENCES petowners(owner_id) ON DELETE CASCADE, "
                + "FOREIGN KEY (pet_id) REFERENCES pets(pet_id) ON DELETE CASCADE, "
                + "FOREIGN KEY (keeper_id) REFERENCES petkeepers(keeper_id) ON DELETE CASCADE, "
                + " PRIMARY KEY (booking_id))";
        stmt.execute(sql);
        stmt.close();
        con.close();

    }

    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public void createNewBooking(Booking bor) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " bookings (owner_id,pet_id,keeper_id,fromDate,toDate,status,price)"
                    + " VALUES ("
                    + "'" + bor.getOwner_id() + "',"
                    + "'" + bor.getPet_id() + "',"
                    + "'" + bor.getKeeper_id() + "',"
                    + "'" + bor.getFromDate() + "',"
                    + "'" + bor.getToDate() + "',"
                    + "'requested',"
                    + "'" + bor.getPrice() + "'"
                    + ")";
            //stmt.execute(table);

            stmt.executeUpdate(insertQuery);
            System.out.println("# The booking was successfully added in the database.");

            /* Get the member id from the database and set it to the member */
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditBookingsTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Booking getRequest(int id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM bookings WHERE keeper_id= '" + id + "' AND status='requested'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Booking bt = gson.fromJson(json, Booking.class);
            return bt;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Booking getBooking(int id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM bookings WHERE owner_id= '" + id + "' AND status='accepted'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Booking bt = gson.fromJson(json, Booking.class);
            return bt;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Booking> GetMatching_id(int id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Booking> array = new ArrayList<>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM bookings WHERE keeper_id = '" + id + "'");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Booking bookings = gson.fromJson(json, Booking.class);
                array.add(bookings);
            }
            return array;
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            con.close();
        }
        return null;
    }

    public ArrayList<Booking> GetFinished(int id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Booking> array = new ArrayList<>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM bookings WHERE keeper_id = '" + id + "' AND status='finished'");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Booking bookings = gson.fromJson(json, Booking.class);
                array.add(bookings);
            }
            return array;
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            con.close();
        }
        return null;
    }

    public int getBookingsCountForKeeper(String id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        int total = 0;

        String query = "SELECT COUNT(*) AS total FROM bookings WHERE keeper_id='" + id + "'";
        try (PreparedStatement stmt = con.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                total = rs.getInt("total");
            }
        }

        return total;
    }

    public int total_income() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        int total = 0;

        String query = "SELECT SUM(price) AS total FROM bookings WHERE status='finished'";
        try (PreparedStatement stmt = con.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                total = rs.getInt("total");
            }
        }

        return total;
    }
}
