package org.betavzw.db;


import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App
{
    private static final String CONN_STRING="jdbc:mysql://localhost/testdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Brussels";
    public static void main( String[] args )
    {
        TestDBDAO dao = new TestDBDAO(CONN_STRING, "root", "VDAB");

        try {
            List<Persoon> personen = dao.getPersonen();

            for(Persoon p : personen){
                System.out.printf("%d) %s %s %n",p.getId(), p.getVoornaam(), p.getAchternaam());
            }

        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Van welke persoon wil je de leeftijd zien?: ");

        int id = Integer.parseInt(scanner.nextLine());

        try {
            Persoon p = dao.getPersoon(id);

            if (p == null){
                System.out.println("Persoon niet gevonden");
            } else {
                System.out.printf("%s %s is %d jaar oud%n", p.getVoornaam(), p.getAchternaam(), p.getLeeftijd());
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
