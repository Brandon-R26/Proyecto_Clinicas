package proyecto_clinicas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {

    // Instancias para cada base de datos
    private static Connection oracleConnection = null;
    private static Connection mysqlConnection = null;

    // Configuración para Oracle
    private static final String ORACLE_JDBC_URL = "jdbc:oracle:thin:@//20.185.224.121:1521/ORCLPDB";
    private static final String ORACLE_USER = "administrador";
    private static final String ORACLE_PASSWORD = "W8xd7YKmk9sYWwJn09SgJTMGHw0qvR";

    // Configuración para MySQL
    private static final String MYSQL_JDBC_URL = "jdbc:mysql://clinica-mysql.mysql.database.azure.com:3306/clinica?useSSL=true&requireSSL=true&verifyServerCertificate=false&zeroDateTimeBehavior=CONVERT_TO_NULL";

    private static final String MYSQL_USER = "userclinicasql";
    private static final String MYSQL_PASSWORD = "W8xd7YKmk9sYWwJn09SgJTMGHw0qvR";

    // Bloque estático para registrar drivers
    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar los drivers JDBC");
        }
    }

    // Constructor privado para evitar instanciación
    public DatabaseConnector() {
    }

    // Obtiene conexión a Oracle
    public static Connection getOracleConnection() throws SQLException {
        if (oracleConnection == null || oracleConnection.isClosed()) {
            Properties props = new Properties();
            props.setProperty("user", ORACLE_USER);
            props.setProperty("password", ORACLE_PASSWORD);
            props.setProperty("oracle.net.CONNECT_TIMEOUT", "2000");

            oracleConnection = DriverManager.getConnection(ORACLE_JDBC_URL, props);
            oracleConnection.setAutoCommit(false); // Para controlar transacciones manualmente
            System.out.println("coneccion exitosa");
        }
        return oracleConnection;
    }

    // Obtiene conexión a MySQL
    //branch?
    //hola
    public static Connection getMySQLConnection() throws SQLException {
        if (mysqlConnection == null || mysqlConnection.isClosed()) {
            Properties props = new Properties();
            props.setProperty("user", MYSQL_USER);
            props.setProperty("password", MYSQL_PASSWORD);
            props.setProperty("connectTimeout", "2000");
           // props.setProperty("useSSL", "false");

            mysqlConnection = DriverManager.getConnection(MYSQL_JDBC_URL, props);
            mysqlConnection.setAutoCommit(false);
               System.out.println("coneccion exitosa");
        }
        return mysqlConnection;
    }

    // Cierra todas las conexiones activas
    public static void closeAllConnections() {
        try {
            if (oracleConnection != null && !oracleConnection.isClosed()) {
                oracleConnection.close();
            }
            if (mysqlConnection != null && !mysqlConnection.isClosed()) {
                mysqlConnection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ejecuta commit en ambas conexiones
    public static void commitAll() {
        try {
            if (oracleConnection != null && !oracleConnection.isClosed()) {
                oracleConnection.commit();
            }
            if (mysqlConnection != null && !mysqlConnection.isClosed()) {
                mysqlConnection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            rollbackAll();
        }
    }

    // Ejecuta rollback en ambas conexiones
    public static void rollbackAll() {
        try {
            if (oracleConnection != null && !oracleConnection.isClosed()) {
                oracleConnection.rollback();
            }
            if (mysqlConnection != null && !mysqlConnection.isClosed()) {
                mysqlConnection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
