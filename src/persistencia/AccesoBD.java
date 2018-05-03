package persistencia;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JJBRZ
 */
public class AccesoBD {
    private static final String CONNECTION_URL = "jdbc:mysql://35.198.95.47/proyecto?" +
    "user=aplicacion&password=Apl.caci0n&useSSL=false";
    // Obtener la conexión
    
    public Connection abrirConexion () throws SQLException {
        Connection con = DriverManager.getConnection(CONNECTION_URL); 
        return con;
    }
    
    public void cerrarConexion (Connection con) throws SQLException {
        con.close();
    }
  
        
   
}
