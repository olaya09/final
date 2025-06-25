package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class EstudianteDAO {
    private Conexion conexion;
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

    public EstudianteDAO() {
        conexion = new Conexion();
        con = conexion.obtenerConexion();
    }

    public boolean insertarEstudiante(Estudiante est){
        String sql = "INSERT INTO estudiante (codigo, nombre, apellido) VALUES (?, ?, ?)";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, est.getCodigo());
            pst.setString(2, est.getNombre());
            pst.setString(3, est.getApellido());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al ingresar el estudiante");
            return false;
        }
    }

    public Estudiante consultarEstudiante(int codigo){
        Estudiante est = new Estudiante();
        String sql = "SELECT * FROM estudiante WHERE codigo=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, codigo);
            rs = pst.executeQuery();
            if(rs.next()){
                est.setCodigo(rs.getInt("codigo"));
                est.setNombre(rs.getString("nombre"));
                est.setApellido(rs.getString("apellido"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar estudiante");
        }
        return est;
    }

    public boolean modificarEstudiante(Estudiante est){
        String sql = "UPDATE estudiante SET nombre=?, apellido=? WHERE codigo=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, est.getNombre());
            pst.setString(2, est.getApellido());
            pst.setInt(3, est.getCodigo());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar el estudiante");
            return false;
        }
    }

    public boolean borrarEstudiante(int codigo){
        String sql = "DELETE FROM estudiante WHERE codigo=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, codigo);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al borrar el estudiante");
            return false;
        }
    }

    public List<Estudiante> listarEstudiantes(){
        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT * FROM estudiante ORDER BY codigo ASC";
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                Estudiante e = new Estudiante();
                e.setCodigo(rs.getInt("codigo"));
                e.setNombre(rs.getString("nombre"));
                e.setApellido(rs.getString("apellido"));
                lista.add(e);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar estudiantes");
        }
        return lista;
    }
}
