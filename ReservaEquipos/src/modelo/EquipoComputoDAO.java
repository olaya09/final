package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class EquipoComputoDAO {
    Conexion conexion;
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    public EquipoComputoDAO(){
        conexion = new Conexion();
        con = conexion.obtenerConexion();
    }

    public boolean insertarEquipoComputo(EquipoComputo comp) {
        String sql = "INSERT INTO equipocomputo (noInventario, marca, anhoCompra) VALUES(?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, comp.getNumInvetario());
            pst.setString(2, comp.getMarca());
            pst.setInt(3, comp.getAnhoCompra());
            int filas = pst.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al ingresar equipo de computo");
            return false;
        }
    }

    public EquipoComputo consultarEquipoComputo(int codInventario) {
        String sql = "SELECT * FROM equipocomputo WHERE noInventario = ?";
        EquipoComputo unEquipoCompu = new EquipoComputo();
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, codInventario);
            rs = pst.executeQuery();
            if (rs.next()) {
                unEquipoCompu.setNumInvetario(rs.getInt("noInventario"));
                unEquipoCompu.setMarca(rs.getString("marca"));
                unEquipoCompu.setAnhoCompra(rs.getInt("anhoCompra"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al consultar equipo de computo");
        }
        return unEquipoCompu;
    }

    public boolean modificarEquipoComputo(EquipoComputo comp) {
        String sql = "UPDATE equipocomputo SET marca = ?, anhoCompra = ? WHERE noInventario = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, comp.getMarca());
            pst.setInt(2, comp.getAnhoCompra());
            pst.setInt(3, comp.getNumInvetario());
            int filas = pst.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al modificar equipo de computo");
            return false;
        }
    }

    //Método para borrar un Equipo de Cómputo
    // método para borrar un equipo de cómputo
public boolean borrarEquipoComputo(int codInventario) {
    // primero eliminar las reservas que referencian este equipo
    String sqlRes = "DELETE FROM reserva WHERE noInventarioPC = ?";
    String sqlEq  = "DELETE FROM equipocomputo WHERE noInventario = ?";
    try (
        PreparedStatement pstRes = con.prepareStatement(sqlRes);
        PreparedStatement pstEq  = con.prepareStatement(sqlEq);
    ) {
        pstRes.setInt(1, codInventario);
        pstRes.executeUpdate();

        pstEq.setInt(1, codInventario);
        int filas = pstEq.executeUpdate();
        return filas > 0;
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "error al borrar equipo de computo:\n" + e.getMessage());
        return false;
    }
}


    public List<EquipoComputo> listarEquipoComputo() {
        List<EquipoComputo> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipocomputo ORDER BY noInventario ASC";
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                EquipoComputo e = new EquipoComputo();
                e.setNumInvetario(rs.getInt("noInventario"));
                e.setMarca(rs.getString("marca"));
                e.setAnhoCompra(rs.getInt("anhoCompra"));
                lista.add(e);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al listar equipos de computo");
        }
        return lista;
    }
}
