/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author PROF-REC-TEC
 */
public class ReservaDAO {
    //Atributos
    Conexion conexion;
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    public ReservaDAO(){
        conexion = new Conexion();
        con= conexion.obtenerConexion();
    }
        
    //Método para insertar Equipos de Cómputo en la base de datos
    public boolean insertarReserva(Reserva reserva){
        
        String sql= "INSERT INTO reserva (cedulaProfesor, noInventarioPC, fechaRecogida, fechaEntrega) VALUES(?, ?, ?, ?)";
        boolean insertado=true;
        
        Timestamp fechaHora = new Timestamp(new Date().getTime());//Se almacenara la fecha y hora en la que se guarda el registro
        
        try{
            pst= this.con.prepareStatement(sql);
            
            pst.setInt(1, reserva.getCedulaProfesor());
            pst.setInt(2, reserva.getNoInventarioPC());
            pst.setTimestamp(3, fechaHora);
            pst.setTimestamp(4, fechaHora);
            
            insertado= pst.execute();
            System.out.println(insertado);
        }catch(SQLException sqlE){
            JOptionPane.showMessageDialog(null, "Error al ingresar la reserva");            
            System.out.println(sqlE.getMessage());
        }
        return insertado;    
    }
    
    
}
