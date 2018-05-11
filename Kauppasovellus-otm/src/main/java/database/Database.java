package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }
    
    /**
     * Initialises the database by using sqliteCommands(). 
     */
    public void init() {
        List<String> commands = sqliteCommands();

        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            for (String lause : commands) {
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
        }
    }

    /**
     * Creates sqlite-commands for database creation and insertion of test data to the database. 
     * @return List<String> of database creation and data insertion commands. 
     */
    private List<String> sqliteCommands() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("DROP TABLE Kayttaja");
        commands.add("DROP TABLE Tuote");
        commands.add("DROP TABLE Ostos");
        commands.add("CREATE TABLE Tuote ("
                + "tuote_id integer PRIMARY KEY,"
                + "nimi varchar(100),"
                + "hinta integer,"
                + "maara integer,"
                + "info varchar (100)"
                + ");");
        commands.add("CREATE TABLE Kayttaja ("
                + "kayttaja_id integer PRIMARY KEY, "
                + "nimi varchar(100), "
                + "saldo integer"
                + ");");
        commands.add("CREATE TABLE OSTOS ("
                + "kayttaja_id integer,"
                + "tuote_id integer,"
                + "date integer,"
                + "FOREIGN KEY (kayttaja_id) REFERENCES Kayttaja (kayttaja_id),"
                + "FOREIGN KEY (tuote_id) REFERENCES Tuote (tuote_id)"
                + ");");
        commands.add("INSERT INTO Kayttaja (kayttaja_id, nimi, saldo) VALUES (1, \"Kalle\", 10.0);");
        commands.add("INSERT INTO Tuote (tuote_id, nimi, hinta, maara, info) VALUES (1, \"Pulla\", 1.0, 1, \"pullaa\");");
        commands.add("INSERT INTO Ostos (kayttaja_id, tuote_id, date) VALUES (1, 1, 1000);");

        return commands;
    }
}
