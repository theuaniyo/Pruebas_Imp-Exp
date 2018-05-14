package persistencia;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    
    private static String ultimaFechaModificacion;
    
    private static final String CONNECTION_URL = "jdbc:mysql://35.198.95.47/proyecto?" +
    "user=aplicacion&password=Apl.caci0n&useSSL=false";
    // Obtener la conexión
    
    public static Connection abrirConexion () throws SQLException {
        Connection con = DriverManager.getConnection(CONNECTION_URL); 
        return con;
    }
    
    public static void cerrarConexion (Connection con) throws SQLException {
        
        actualizarFecha();
        con.close();
    }
  
    public static void actualizarFecha() throws SQLException{
    
        Connection con = abrirConexion();

        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT ultimaModificacion FROM modificado");
        
        while (rs.next()) {

            ultimaFechaModificacion = rs.getString("ultimaModificacion");

        }
        con.close();
    
    }
    
    public String devuelveFechaUltimaModificacion(){
        
        return ultimaFechaModificacion;
    }
   
}
