package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private String db_nombre="db_reservas"; //nombre base de datos
    private String user="root";
    private String password="univalle";
    private String url="jdbc:mysql://localhost:3306/"+this.db_nombre;
    
    Connection conexion=null;
    
    public Connection obtenerConexion(){
        try{
            //Obtener valor del driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            //Obtener conexión
            conexion= DriverManager.getConnection(this.url, this.user, this.password);
        }catch(ClassNotFoundException e){
            System.out.println("Ha ocurrido un ClassNotFoundException\n"
                    + "No ha importado el Driver Genius\nError de capa 8\n"+e.getMessage());
        }catch(SQLException e){
            System.out.println("Ha ocurrido una SQLException\n"
                    + "No se ha encontrado la base de datos\n"+e.getMessage());
        }
        return conexion;
    }
}
