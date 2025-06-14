package proyecto_clinicas;
import java.sql.DriverManager;
import java.sql.Connection;
import javax.swing.JOptionPane;

public class MySqlConnector {
     Connection c  =null;

    public Connection conec() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = (Connection) DriverManager.getConnection("jdbc:mysql://mysqldb.cbawooi4y27d.us-east-2.rds.amazonaws.com:3306/DB-Clinic?zeroDateTimeBehavior=CONVERT_TO_NULL","admin","BWDclinicaDB");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
         return  c;
    }
}