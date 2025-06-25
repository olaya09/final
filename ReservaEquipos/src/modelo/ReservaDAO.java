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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        
    //Método para insertar una Reserva en la base de datos
    public boolean insertarReserva(Reserva reserva){
        String sql= "INSERT INTO reserva (cedulaProfesor, noInventarioPC, fechaRecogida, fechaEntrega) VALUES(?, ?, ?, ?)";
        Timestamp fechaHora = new Timestamp(new Date().getTime());

        try{
            pst= this.con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, reserva.getCedulaProfesor());
            pst.setInt(2, reserva.getNoInventarioPC());
            pst.setTimestamp(3, fechaHora);
            pst.setTimestamp(4, fechaHora);

            int filas = pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if(rs.next()){
                reserva.setCodigo(rs.getInt(1));
            }

            return filas > 0;
        }catch(SQLException sqlE){
            JOptionPane.showMessageDialog(null, "Error al ingresar la reserva");
            System.out.println(sqlE.getMessage());
            return false;
        }
    }

    //Consultar una reserva por código
    public Reserva consultarReserva(int codigo){
        Reserva r = new Reserva();
        String sql = "SELECT * FROM reserva WHERE codigo=?";
        try{
            pst = this.con.prepareStatement(sql);
            pst.setInt(1, codigo);
            rs = pst.executeQuery();
            if(rs.next()){
                r.setCodigo(rs.getInt("codigo"));
                r.setCedulaProfesor(rs.getInt("cedulaProfesor"));
                r.setNoInventarioPC(rs.getInt("noInventarioPC"));
                r.setFechaRecogida(String.valueOf(rs.getTimestamp("fechaRecogida")));
                r.setFechaEntrega(String.valueOf(rs.getTimestamp("fechaEntrega")));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al consultar la reserva");
        }
        return r;
    }

    //Listar todas las reservas
    public List<Reserva> listarReservas(){
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM reserva ORDER BY codigo ASC";
        try{
            pst = this.con.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                Reserva r = new Reserva();
                r.setCodigo(rs.getInt("codigo"));
                r.setCedulaProfesor(rs.getInt("cedulaProfesor"));
                r.setNoInventarioPC(rs.getInt("noInventarioPC"));
                r.setFechaRecogida(String.valueOf(rs.getTimestamp("fechaRecogida")));
                r.setFechaEntrega(String.valueOf(rs.getTimestamp("fechaEntrega")));
                lista.add(r);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al listar las reservas");
        }
        return lista;
    }

}
