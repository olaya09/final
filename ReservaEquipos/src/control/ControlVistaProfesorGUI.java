/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import vista.VistaProfesorGUI;
import modelo.Profesor;
import modelo.ProfesorDAO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author UNIVALLE
 */
public class ControlVistaProfesorGUI implements ActionListener{
    
    //Atributos
    private VistaProfesorGUI vistaProfesor;
    private Profesor unProfesor;
    
    //Constructor
    public ControlVistaProfesorGUI(){
        
        this.vistaProfesor= new VistaProfesorGUI();
        this.unProfesor= new Profesor();
        
        //Se hace visible la GUI
        this.vistaProfesor.setVisible(true);
        
        //Se agrega escucha a los botones
        this.vistaProfesor.jbtn_agregar.addActionListener(this);
        this.vistaProfesor.jbtn_consultar.addActionListener(this);
        this.vistaProfesor.jbtn_modificar.addActionListener(this);
        this.vistaProfesor.jbtn_borrar.addActionListener(this);
        this.vistaProfesor.btn_ListarProfesores.addActionListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Guardar Profesor en la base de datos
        if(e.getSource() == this.vistaProfesor.jbtn_agregar){
            
            String cedula  = this.vistaProfesor.jtf_cedula.getText().trim();
            String nombre  = this.vistaProfesor.jtf_nombre.getText().trim();
            String apellido  = this.vistaProfesor.jtf_apellido.getText().trim();
            String curso  = this.vistaProfesor.jtf_curso.getText().trim();
            
            if (cedula.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || curso.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No pueden haber campos vacíos");
                return;
            }
            
            if (cedula.contains(".") || cedula.contains(",")) {
            JOptionPane.showMessageDialog(null, "el campo cedula no debe llevar puntos ni comas");
            return;
            }
            
            if (!cedula.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "la cedula debe ser un numero entero valido");
            return;
            }
            
            this.unProfesor.setCedula(Integer.parseInt(cedula));
            this.unProfesor.setNombre(nombre);
            this.unProfesor.setApellido(apellido);
            this.unProfesor.setCurso(curso);
            
            ProfesorDAO unProfeDao= new ProfesorDAO();
            
            if(unProfeDao.insetarProfesor(unProfesor)){
                JOptionPane.showMessageDialog(null, "Profesor Registrado con Éxito!!!");
            }else{
                JOptionPane.showMessageDialog(null, "Profesor No Registrado!!!");
            }
            
        }
        
        // Consultar Profesor en la base de datos
        if (e.getSource() == this.vistaProfesor.jbtn_consultar) {
            // 1) se define cedTexto como String para acortar
            String cedula = this.vistaProfesor.jtf_cedula.getText().trim();

            // 2) validar vacío
            if (cedula.isEmpty()) {
                JOptionPane.showMessageDialog(null,"el campo de la cedula no puede estar vacia");
                return;
            }

            // 3) validamos que no tenga puntos ni comas
            if (cedula.contains(".") || cedula.contains(",")) {
                JOptionPane.showMessageDialog(null,"la cedula no debe contener puntos ni comas");
                return;
            }

            // 4) validar que sea solo dígitos
            if (!cedula.matches("\\d+")) {
                JOptionPane.showMessageDialog(null,"la cedula debe ser un entero");
                return;
            }

            // 5) parseamos con seguridad
            int cedulaConsulta;
            try {
                cedulaConsulta = Integer.parseInt(cedula);
            } catch (NumberFormatException ex) {
                // Esto prácticamente no ocurrirá si .matches("\\d+") ya pasó,
                // pero lo dejamos por seguridad.
                JOptionPane.showMessageDialog(null,"Error al convertir la cédula");
                return;
            }

            // 6) Ejecutar consulta
            ProfesorDAO dao = new ProfesorDAO();
            this.unProfesor = dao.consultarProfesor(cedulaConsulta);

            // 7) Mostrar resultados
            if (this.unProfesor != null && this.unProfesor.getCedula() != 0) {
                this.vistaProfesor.jtf_nombre.setText(this.unProfesor.getNombre());
                this.vistaProfesor.jtf_apellido.setText(this.unProfesor.getApellido());
                this.vistaProfesor.jtf_curso.setText(this.unProfesor.getCurso());
            } else {
                JOptionPane.showMessageDialog(null,"Profesor No Registrado!!!");
            }
        }

        
            // modificar datos profesor en la base de datos
        if (e.getSource() == this.vistaProfesor.jbtn_modificar) {
            
            String cedtexto = vistaProfesor.jtf_cedula.getText().trim();
            
            if (cedtexto.isEmpty()) {
                JOptionPane.showMessageDialog(null, "la cedula no puede estar vacia");
                return;
            }
            
            if (cedtexto.contains(".") || cedtexto.contains(",")) {
                JOptionPane.showMessageDialog(null, "la cedula no debe contener puntos ni comas");
                return;
            }            
            if (!cedtexto.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "la cedula debe ser un numero entero valido");
                return;
            }
            
            int cedula;
            try {
                cedula = Integer.parseInt(cedtexto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "error al convertir la cedula");
                return;
            }

            ProfesorDAO dao = new ProfesorDAO();
            
            Profesor existente = dao.consultarProfesor(cedula);
            if (existente == null || existente.getCedula() == 0) {
                JOptionPane.showMessageDialog(null, "no existe un profesor con esa cedula");
                return;
            }
            
            existente.setNombre(vistaProfesor.jtf_nombre.getText().trim());
            existente.setApellido(vistaProfesor.jtf_apellido.getText().trim());
            existente.setCurso(vistaProfesor.jtf_curso.getText().trim());
            
            if (dao.modificarProfesor(existente)) {
                JOptionPane.showMessageDialog(null, "profesor modificado con exito!!!");
            } else {
                JOptionPane.showMessageDialog(null, "profesor no modificado!!!");
            }
        }



        if (e.getSource() == this.vistaProfesor.jbtn_borrar) {
            int cedula = Integer.parseInt(this.vistaProfesor.jtf_cedula.getText().trim());
            ProfesorDAO dao = new ProfesorDAO();
            Profesor existente = dao.consultarProfesor(cedula);
            if (existente == null || existente.getCedula() == 0) {
                JOptionPane.showMessageDialog(null, "No existe un profesor con esa cédula");
                return;
            }
            if (dao.borrarProfesor(cedula)) {
                JOptionPane.showMessageDialog(null, "Profesor Borrado con Éxito!!!");
            } else {
                JOptionPane.showMessageDialog(null, "Profesor No Borrado!!!");
            }
        }


        if (e.getSource() == vistaProfesor.btn_ListarProfesores) {
        //se llama al modelo de la tabla que se inserto en el JForm
        DefaultTableModel modelo = (DefaultTableModel) 
            vistaProfesor.jTable_profesores.getModel();

        // Limpia las filas si tienen algo metido ahi
        modelo.setRowCount(0);

        // se traen todas las listas desde la clase DAO
        ProfesorDAO dao = new ProfesorDAO();
        List<Profesor> lista = dao.listarProfesores();

        // se rellena la tabla
        for (Profesor p : lista) {
            modelo.addRow(new Object[]{
                p.getCedula(),
                p.getNombre(),
                p.getApellido(),
                p.getCurso()
            });
        }
    }
        
    }
    
}
