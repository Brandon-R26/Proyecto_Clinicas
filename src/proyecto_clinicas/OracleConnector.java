package proyecto_clinicas;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author walte
 */
public class OracleConnector {
    Connection c = null;

    public Connection conec() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            c = DriverManager.getConnection(
                "jdbc:oracle:thin:@//oracledb.cbawooi4y27d.us-east-2.rds.amazonaws.com:1521/DBCLINIC",
                "admin",
                "BWDclinicaDB"
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return c;
    }
}
