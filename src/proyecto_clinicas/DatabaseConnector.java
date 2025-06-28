package proyecto_clinicas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;

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
             traducirError2(e);
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
           traducirError(e);
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
           traducirError(e);
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
            traducirError(e);
        }
    }
    
     private static void traducirError2(ClassNotFoundException e) {
        String mensaje = e.getMessage();
        String mensaje2;

        if (mensaje == null || mensaje.isEmpty()) {
            mensaje2 = "Ocurrió un error inesperado. Por favor, intente nuevamente.";
        } else if (mensaje.contains("Duplicate entry")) {
            mensaje2 = "Parece que ya existe un registro con este valor. Por favor, verifica e intente nuevamente.";
        } else if (mensaje.contains("Incorrect integer value")) {
            mensaje2 = "El valor ingresado no es válido. Asegúrate de ingresar un número entero correcto o de no dejar vacio los identificadores.";
        } else if (mensaje.contains("Access denied")) {
            mensaje2 = "No tienes permiso para realizar esta acción. Verifica tus credenciales e intente nuevamente.";
        } else if (mensaje.contains("SQL syntax")) {
            mensaje2 = "Hubo un problema con la consulta. Por favor, verifica la información e intente nuevamente.";
        } else if (mensaje.contains("foreign key constraint")) {
           
                mensaje2 = "No se puede dejar en blanco un identificador secundario o escoger uno que no existe, agrege uno existente e intente nuevamente.";
            

        } else if (mensaje.contains("Data too long")) {
            mensaje2 = "El dato ingresado es demasiado largo. Por favor, verifica la longitud del dato e intente nuevamente.";
        } else if (mensaje.contains("No database selected")) {
            mensaje2 = "No se ha seleccionado una base de datos. Por favor, selecciona una base de datos e intente nuevamente.";
        } else if (mensaje.contains("connection closed")) {
            mensaje2 = "No se ha seleccionado una base de datos. Por favor, Conectese a una Base de Datos e intente nuevamente.";
        } else if (mensaje.contains("Invalid date format")) {
            mensaje2 = "El formato de la fecha ingresada no es válido. Por favor, verifica el formato e intente nuevamente.";
        } else if (mensaje.contains("Cannot add or update a child row")) {
            mensaje2 = "No se puede agregar o actualizar un registro debido a una relación con otro registro. Verifica que los registros relacionados existan e intente nuevamente.";
        } else if (mensaje.contains("Data truncation")) {
            mensaje2 = "Parece que hay un problema con los datos ingresados. Por favor, revisa que estén correctos y en el formato adecuado.";
        } else if (mensaje.contains("Incorrect decimal value")) {
            mensaje2 = "El valor ingresado no es válido. Asegúrate de ingresar un número decimal correcto.";
        } else if (mensaje.contains("Check constraint")) {

            mensaje2 = "accion restringida esta tratando de ingresar un valor no permitido. Por favor, verifica los datos e intente nuevamente.";

        } else {
            mensaje2 = "Ocurrió un error inesperado: " + mensaje + ". Por favor, intente nuevamente.";
        }

      
        JOptionPane.showMessageDialog(null, mensaje2);
}
       private static void traducirError(SQLException e) {
        String mensaje = e.getMessage();
        String mensaje2;

        if (mensaje == null || mensaje.isEmpty()) {
            mensaje2 = "Ocurrió un error inesperado. Por favor, intente nuevamente.";
        } else if (mensaje.contains("Duplicate entry")) {
            mensaje2 = "Parece que ya existe un registro con este valor. Por favor, verifica e intente nuevamente.";
        } else if (mensaje.contains("Incorrect integer value")) {
            mensaje2 = "El valor ingresado no es válido. Asegúrate de ingresar un número entero correcto o de no dejar vacio los identificadores.";
        } else if (mensaje.contains("Access denied")) {
            mensaje2 = "No tienes permiso para realizar esta acción. Verifica tus credenciales e intente nuevamente.";
        } else if (mensaje.contains("SQL syntax")) {
            mensaje2 = "Hubo un problema con la consulta. Por favor, verifica la información e intente nuevamente.";
        } else if (mensaje.contains("foreign key constraint")) {
           
                mensaje2 = "No se puede dejar en blanco un identificador secundario o escoger uno que no existe, agrege uno existente e intente nuevamente.";
            

        } else if (mensaje.contains("Data too long")) {
            mensaje2 = "El dato ingresado es demasiado largo. Por favor, verifica la longitud del dato e intente nuevamente.";
        } else if (mensaje.contains("No database selected")) {
            mensaje2 = "No se ha seleccionado una base de datos. Por favor, selecciona una base de datos e intente nuevamente.";
        } else if (mensaje.contains("connection closed")) {
            mensaje2 = "No se ha seleccionado una base de datos. Por favor, Conectese a una Base de Datos e intente nuevamente.";
        } else if (mensaje.contains("Invalid date format")) {
            mensaje2 = "El formato de la fecha ingresada no es válido. Por favor, verifica el formato e intente nuevamente.";
        } else if (mensaje.contains("Cannot add or update a child row")) {
            mensaje2 = "No se puede agregar o actualizar un registro debido a una relación con otro registro. Verifica que los registros relacionados existan e intente nuevamente.";
        } else if (mensaje.contains("Data truncation")) {
            mensaje2 = "Parece que hay un problema con los datos ingresados. Por favor, revisa que estén correctos y en el formato adecuado.";
        } else if (mensaje.contains("Incorrect decimal value")) {
            mensaje2 = "El valor ingresado no es válido. Asegúrate de ingresar un número decimal correcto.";
        } else if (mensaje.contains("Check constraint")) {

            mensaje2 = "accion restringida esta tratando de ingresar un valor no permitido. Por favor, verifica los datos e intente nuevamente.";

        } else {
            mensaje2 = "Ocurrió un error inesperado: " + mensaje + ". Por favor, intente nuevamente.";
        }

        
        JOptionPane.showMessageDialog(null, mensaje2);
}
}
