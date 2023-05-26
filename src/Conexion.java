/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    //la conexion debe de ser privada y debe de estar instanciada
    private static Conexion instance;  //se crea la variable intanciada
    private Connection conexion;  //se crea la variable para hacer la conexion 

    // Librería de MySQL
    private final String driver = "com.mysql.jdbc.Driver";
    // Nombre de la base de datos
    private final String database = "";
    // Host
    private final String hostname = "root";
    // Puerto
    private final String port = "3306";
    // Ruta de nuestra base de datos (desactivamos el uso de SSL con "?useSSL=false")
    private final String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";
    // Nombre de usuario
    private final String username = "root";
    // Clave de usuario
    private final String password = "root";

    private Conexion() {  //metodo contructor de nuestra conexion 
        conexion = conectarMySQL(); //manda a llamar a nuestro metodo conexion 
    }

    //comprueba si existe la conexion 
    public static Conexion getInstance() {
        if (instance == null) {
            instance = new Conexion();
        }
        return instance;
    }

    private Connection conectarMySQL() {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, url, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (conn == null) { //comprueba si hay conexion a la BD
            System.out.println("No se creo la conexion");
        } else {
            //System.out.println("Conexion creada");
        }

        return conn;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
}
