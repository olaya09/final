/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


/**
 *
 * @author UNIVALLE
 */
public class ProfesorDAO{
    //atributos
    private Connection con;
    private Conexion conexion;
    private ResultSet rs;
    private PreparedStatement pst;
    
    //Constructor
    public ProfesorDAO(){
            conexion= new Conexion();
            con= this.conexion.obtenerConexion();
    }
    
    //Metodo agregarProfesor
    public boolean insetarProfesor(Profesor unProfe){
        
        String sql ="INSERT INTO profesor (cedula, nombre, apellido, curso) VALUES(?, ?, ?, ?)";
        
        try{            
            pst= con.prepareStatement(sql);
            
            pst.setInt(1, unProfe.getCedula());
            pst.setString(2, unProfe.getNombre());
            pst.setString(3, unProfe.getApellido());
            pst.setString(4, unProfe.getCurso());
            
            pst.execute();
            
            return true;            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al ingresar el profesor");
            return false;
        }
    }
    
    //Método Consultar
    public Profesor consultarProfesor(int ced){
        Profesor unProfe= new Profesor();
        
        String sql="SELECT * FROM profesor WHERE cedula=?";
        
        try{            
            pst= con.prepareStatement(sql);
            pst.setInt(1, ced);
            
            rs= pst.executeQuery();
            
            if(rs.next()){
                unProfe.setCedula(rs.getInt("cedula"));
                unProfe.setNombre(rs.getString("nombre"));
                unProfe.setApellido(rs.getString("apellido"));
                unProfe.setCurso(rs.getString("curso"));
                
                return unProfe;
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al consultar\n"+e.getMessage());            
        }
        
        return unProfe;
    }
    
    //Método Modificar
    public boolean modificarProfesor(Profesor unProfe){
        
        String sql="UPDATE profesor set nombre=?, apellido=?, curso=? WHERE cedula=?";
        
        
        try{            
            pst= con.prepareStatement(sql);
            
            pst.setString(1, unProfe.getNombre());
            pst.setString(2, unProfe.getApellido());
            pst.setString(3, unProfe.getCurso());
            pst.setInt(4, unProfe.getCedula());
            
            pst.execute();                     
            
            return true;            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al ingresar el profesor");
            return false;
        }
        
    }        
    
    //Método Borrar
    public boolean borrarProfesor(int ced){
        
        String sql="DELETE FROM profesor  WHERE cedula=?";
        
        
        try{
            pst= con.prepareStatement(sql);
            
            pst.setInt(1, ced);
            
            pst.execute();                     
            
            return true;            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al ingresar el profesor");
            return false;
        }        
    }
    
    //Listar profesores
    public List listarProfesores(){
        List<Profesor> listaProfesores = new ArrayList();
        String query = "SELECT * FROM profesor ORDER BY nombre ASC";
                
        try{
            pst= this.con.prepareStatement(query);
            rs= pst.executeQuery();            
            
            while(rs.next()){
                Profesor unProfe= new Profesor();
                
                unProfe.setCedula(rs.getInt("cedula"));
                unProfe.setNombre(rs.getString("nombre"));
                unProfe.setApellido(rs.getString("apellido"));
                unProfe.setCurso(rs.getString("curso"));
                
                //System.out.println(unProfe.getCedula()+" "+unProfe.getNombre()+" "+unProfe.getApellido()+" "+unProfe.getCurso());
                
                listaProfesores.add(unProfe);
            }
            
        }catch(SQLException e ){
            JOptionPane.showMessageDialog(null, "Error al listar los datos: "+e);
        }
        
        //System.out.println(listaProfesores.get(0).getCedula());
        
        return listaProfesores;
    }
    
}
