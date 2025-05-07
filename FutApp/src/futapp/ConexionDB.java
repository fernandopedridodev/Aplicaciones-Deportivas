/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package futapp;

/**
 *
 * @author fernando.pedridomarino
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    // Cambia estos valores según tu configuración de base de datos
    private static final String URL = "jdbc:mariadb://localhost:33006/equipo_futbol"; // Dirección de la base de datos
    private static final String USER = "admin"; // Usuario de la base de datos
    private static final String PASSWORD = "daw2pass"; // Contraseña del usuario de la base de datos

    // Instancia única de la conexión
    private static Connection connection;

    // Constructor privado para evitar instanciación directa
    private ConexionDB() {}

    // Método para conectar a la base de datos
    public static Connection conectar() {
        try {
            // Verificar si la conexión es nula o está cerrada
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión exitosa a la base de datos.");
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return connection;
    }

    // Método para cerrar la conexión
    public static void desconectar() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}