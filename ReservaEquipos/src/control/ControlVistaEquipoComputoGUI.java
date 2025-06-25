package control;

import vista.VistaEquipoComputoGUI;
import modelo.EquipoComputo;
import modelo.EquipoComputoDAO;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControlVistaEquipoComputoGUI implements ActionListener {
    VistaEquipoComputoGUI unaVistaEquiComp;
    EquipoComputo unEquipoCompu;
    EquipoComputoDAO unEquipoCompuDAO;

    public ControlVistaEquipoComputoGUI() {
        unaVistaEquiComp = new VistaEquipoComputoGUI();
        unEquipoCompu = new EquipoComputo();
        unEquipoCompuDAO = new EquipoComputoDAO();
        unaVistaEquiComp.setVisible(true);
        unaVistaEquiComp.jbtn_agregarE.addActionListener(this);
        unaVistaEquiComp.jbtn_consultar.addActionListener(this);
        unaVistaEquiComp.jbtn_modificar.addActionListener(this);
        unaVistaEquiComp.jbtn_borrar.addActionListener(this);
        unaVistaEquiComp.btn_ListarEquipos.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == unaVistaEquiComp.jbtn_agregarE) {
            String inv = unaVistaEquiComp.jtf_noInventario.getText().trim();
            String marca = unaVistaEquiComp.jtf_marca.getText().trim();
            String anho = unaVistaEquiComp.jtf_anhoCompra.getText().trim();
            if (inv.isEmpty() || marca.isEmpty() || anho.isEmpty()) {
                JOptionPane.showMessageDialog(null, "complete todos los campos");
                return;
            }
            if (inv.contains(".") || inv.contains(",")) {
                JOptionPane.showMessageDialog(null, "el inventario no debe contener puntos ni comas");
                return;
            }
            if (!inv.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "el inventario debe ser un numero entero valido");
                return;
            }
            if (anho.contains(".") || anho.contains(",")) {
                JOptionPane.showMessageDialog(null, "el ano de compra no debe contener puntos ni comas");
                return;
            }
            if (!anho.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "el ano de compra debe ser un numero entero valido");
                return;
            }
            int i, a;
            try {
                i = Integer.parseInt(inv);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "error al convertir inventario");
                return;
            }
            try {
                a = Integer.parseInt(anho);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "error al convertir ano de compra");
                return;
            }
            unEquipoCompu.setNumInvetario(i);
            unEquipoCompu.setMarca(marca);
            unEquipoCompu.setAnhoCompra(a);
            if (unEquipoCompuDAO.insertarEquipoComputo(unEquipoCompu)) {
                JOptionPane.showMessageDialog(null, "equipo de computo registrado con exito");
            } else {
                JOptionPane.showMessageDialog(null, "equipo de computo no registrado");
            }
        }
        if (e.getSource() == unaVistaEquiComp.jbtn_consultar) {
            String inv = unaVistaEquiComp.jtf_noInventario.getText().trim();
            if (inv.isEmpty()) {
                JOptionPane.showMessageDialog(null, "complete todos los campos");
                return;
            }
            if (inv.contains(".") || inv.contains(",")) {
                JOptionPane.showMessageDialog(null, "el inventario no debe contener puntos ni comas");
                return;
            }
            if (!inv.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "el inventario debe ser un numero entero valido");
                return;
            }
            int i;
            try {
                i = Integer.parseInt(inv);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "error al convertir inventario");
                return;
            }
            unEquipoCompu = unEquipoCompuDAO.consultarEquipoComputo(i);
            if (unEquipoCompu != null && unEquipoCompu.getNumInvetario() != 0) {
                unaVistaEquiComp.jtf_marca.setText(unEquipoCompu.getMarca());
                unaVistaEquiComp.jtf_anhoCompra.setText(String.valueOf(unEquipoCompu.getAnhoCompra()));
            } else {
                JOptionPane.showMessageDialog(null, "no existe un equipo con ese inventario");
            }
        }
        if (e.getSource() == unaVistaEquiComp.jbtn_modificar) {
            String inv = unaVistaEquiComp.jtf_noInventario.getText().trim();
            String marca = unaVistaEquiComp.jtf_marca.getText().trim();
            String anho = unaVistaEquiComp.jtf_anhoCompra.getText().trim();
            if (inv.isEmpty() || marca.isEmpty() || anho.isEmpty()) {
                JOptionPane.showMessageDialog(null, "complete todos los campos");
                return;
            }
            if (inv.contains(".") || inv.contains(",")) {
                JOptionPane.showMessageDialog(null, "el inventario no debe contener puntos ni comas");
                return;
            }
            if (!inv.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "el inventario debe ser un numero entero valido");
                return;
            }
            if (anho.contains(".") || anho.contains(",")) {
                JOptionPane.showMessageDialog(null, "el ano de compra no debe contener puntos ni comas");
                return;
            }
            if (!anho.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "el ano de compra debe ser un numero entero valido");
                return;
            }
            int i, a;
            try {
                i = Integer.parseInt(inv);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "error al convertir inventario");
                return;
            }
            try {
                a = Integer.parseInt(anho);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "error al convertir ano de compra");
                return;
            }
            unEquipoCompu = unEquipoCompuDAO.consultarEquipoComputo(i);
            if (unEquipoCompu == null || unEquipoCompu.getNumInvetario() == 0) {
                JOptionPane.showMessageDialog(null, "no existe un equipo con ese inventario");
                return;
            }
            unEquipoCompu.setMarca(marca);
            unEquipoCompu.setAnhoCompra(a);
            if (unEquipoCompuDAO.modificarEquipoComputo(unEquipoCompu)) {
                JOptionPane.showMessageDialog(null, "equipo modificado con exito");
            } else {
                JOptionPane.showMessageDialog(null, "equipo no modificado");
            }
        }
        //Botón borrar un  Equipo de Cómputo con el código en la base de datos
        if(e.getSource() == this.unaVistaEquiComp.jbtn_borrar){
            if (this.unaVistaEquiComp.jtf_noInventario.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Complete todos los campos");
                return;
            }
            int codigoBorrar;
            try {
                codigoBorrar = Integer.parseInt(this.unaVistaEquiComp.jtf_noInventario.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Sólo números sin puntos ni comas");
                return;
            }

            if(!this.unEquipoCompuDAO.borrarEquipoComputo(codigoBorrar)){
                JOptionPane.showMessageDialog(null, "Equipo de Cómputo No Borrado!!!");
            }else{
                JOptionPane.showMessageDialog(null, "Equipo de Cómputo Borrado!!!");
            }

        }
        if (e.getSource() == unaVistaEquiComp.btn_ListarEquipos) {
            DefaultTableModel model = (DefaultTableModel) unaVistaEquiComp.jTable_Equipos.getModel();
            model.setRowCount(0);
            List<EquipoComputo> lista = unEquipoCompuDAO.listarEquipoComputo();
            for (EquipoComputo eq : lista) {
                model.addRow(new Object[]{eq.getNumInvetario(), eq.getMarca(), eq.getAnhoCompra()});
            }
        }
    }
}
