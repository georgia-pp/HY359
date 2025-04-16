package DataBase;

import DataBase.Tables.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static DataBase.DB_Connection.getInitialConnection;


public class DB_Initialization {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DB_Initialization init = new DB_Initialization();
//init.dropDatabase();
        init.initDatabase();
        init.initTables();
        init.addToDatabaseExamples();
    }

    public void initDatabase() throws SQLException, ClassNotFoundException {
        Connection conn = getInitialConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE DATABASE HY359_Project");
        stmt.close();
        conn.close();
        System.out.println("Data Base Successfully created");
    }

    public void dropDatabase() throws SQLException, ClassNotFoundException {
        Connection conn = getInitialConnection();
        Statement stmt = conn.createStatement();
        String query = "DROP DATABASE HY359_Project";
        stmt.executeUpdate(query);
        System.out.println("Database dropped successfully");
        stmt.close();
    }

    public void initTables() throws SQLException, ClassNotFoundException {
        EditPetOwnersTable eut = new EditPetOwnersTable();
        eut.createPetOwnersTable();

        EditPetKeepersTable editkeepers = new EditPetKeepersTable();
        editkeepers.createPetKeepersTable();

        EditPetsTable editpets = new EditPetsTable();
        editpets.createPetsTable();

        EditBookingsTable editBookings = new EditBookingsTable();
        editBookings.createBookingTable();

        EditReviewsTable editRevs = new EditReviewsTable();
        editRevs.createReviewTable();

        EditMessagesTable editMsgs = new EditMessagesTable();
        editMsgs.createMessageTable();

    }

    public void addToDatabaseExamples() throws ClassNotFoundException, SQLException {
        //Users

        EditPetOwnersTable eut = new EditPetOwnersTable();
        eut.addPetOwnerFromJSON(Resources.petOwnerJSON);
        eut.addPetOwnerFromJSON(Resources.petOwner2JSON);
        eut.addPetOwnerFromJSON(Resources.petOwner3JSON);
        eut.addPetOwnerFromJSON(Resources.petOwner4JSON);

        EditPetKeepersTable editKeepers = new EditPetKeepersTable();
        editKeepers.addPetKeeperFromJSON(Resources.petKeeper1);
        editKeepers.addPetKeeperFromJSON(Resources.petKeeper2);
        editKeepers.addPetKeeperFromJSON(Resources.petKeeper3);
        editKeepers.addPetKeeperFromJSON(Resources.petKeeper4);
        editKeepers.addPetKeeperFromJSON(Resources.petKeeper5);
        editKeepers.addPetKeeperFromJSON(Resources.petKeeper6);

        EditPetsTable ebt = new EditPetsTable();
        ebt.addPetFromJSON(Resources.pet1);
        ebt.addPetFromJSON(Resources.pet2);
        ebt.addPetFromJSON(Resources.pet3);
        ebt.addPetFromJSON(Resources.pet4);

        EditBookingsTable editbookings = new EditBookingsTable();
        editbookings.addBookingFromJSON(Resources.booking1);
        editbookings.addBookingFromJSON(Resources.booking2);
        editbookings.addBookingFromJSON(Resources.booking3);

        EditMessagesTable editmessages = new EditMessagesTable();
        editmessages.addMessageFromJSON(Resources.message1);
        editmessages.addMessageFromJSON(Resources.message2);

        EditReviewsTable editRevs = new EditReviewsTable();
        editRevs.addReviewFromJSON(Resources.review1);
    }
}
