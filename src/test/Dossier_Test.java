package test;

import java.sql.SQLException;

import app.patient.AdmData;
import app.patient.Dossier;

/**
 * Tests unitaires de l'interface graphique.
 */
public class Dossier_Test {
    public static void main(String[] args) throws SQLException
    {
        Dossier d = new Dossier();
        d.load("1");
        AdmData a = d.getData();
        System.out.println(a.getPrenom());

        
    }
}
