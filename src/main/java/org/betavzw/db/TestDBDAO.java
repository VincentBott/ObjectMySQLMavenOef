package org.betavzw.db;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestDBDAO {


    private String connectionString;

    private String username;
    private String password;

    private static final String SELECT = "SELECT id, voornaam, achternaam, geboortedatum FROM persoon";
    private static final String SELECT_By_ID = "SELECT id, voornaam, achternaam, geboortedatum FROM persoon WHERE id = ?";

    public TestDBDAO(String connectionString, String username, String password) {
        this.connectionString = connectionString;
        this.username = username;
        this.password = password;
    }


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }


    public List<Persoon> getPersonen() throws SQLException {

        List<Persoon> lijst = new ArrayList<>();

        try (Connection conn = getConnection();

             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(SELECT)) {

            while (rs.next()) {
                Persoon p = extractPersoon(rs);
                lijst.add(p);
            }
        }
        return lijst;
    }


    public Persoon getPersoon(int anId) throws SQLException{

        Persoon p = null;

        try(Connection conn = getConnection();
            PreparedStatement stm = conn.prepareStatement(SELECT_By_ID)){
            stm.setInt(1, anId);
            try(ResultSet rs = stm.executeQuery()){
                if (rs.next()){
                    p = extractPersoon(rs);
                }
            }
        }

        return p;
    }

    private Persoon extractPersoon(ResultSet rs) throws SQLException {

        Persoon p;

        int id = rs.getInt("id");

        String voornaam = rs.getString("voornaam");
        String achternaam = rs.getString("achternaam");
        LocalDate geboortedatum = rs.getDate("geboortedatum").toLocalDate();
        p = new Persoon(id, voornaam, achternaam, geboortedatum);

        return p;
    }

}
