
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Estudiante;
import modelo.EstudianteDAO;
import vista.VistaEstudianteGUI;

public class ControlVistaEstudianteGUI implements ActionListener{
    
    Estudiante estudiante;
    VistaEstudianteGUI vistaEstudiante;
    EstudianteDAO estudianteDAO;

    public ControlVistaEstudianteGUI(Estudiante estudiante, VistaEstudianteGUI vistaEstudiante) {
                
        this.estudiante = new Estudiante();
        this.vistaEstudiante = new VistaEstudianteGUI();
        this.estudianteDAO = new EstudianteDAO();
        this.vistaEstudiante.setVisible(true);

        this.vistaEstudiante.btn_AgregarEstudiante.addActionListener(this);
        this.vistaEstudiante.btn_BorrarEstudiante.addActionListener(this);
        this.vistaEstudiante.btn_ConsultarEstudiante.addActionListener(this);
        this.vistaEstudiante.btn_ListarEstudiantes.addActionListener(this);
        this.vistaEstudiante.btn_ModificarEstudiante.addActionListener(this);
    }    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vistaEstudiante.btn_AgregarEstudiante){
            String cod = vistaEstudiante.jtf_codigo_estudiante.getText().trim();
            String nom = vistaEstudiante.jtf_nombre_estudiante.getText().trim();
            String ape = vistaEstudiante.jtf_apellido_estudiante.getText().trim();

            if(cod.isEmpty() || nom.isEmpty() || ape.isEmpty()){
                JOptionPane.showMessageDialog(null, "No pueden haber campos vacíos");
                return;
            }
            if(cod.contains(".") || cod.contains(",")){
                JOptionPane.showMessageDialog(null, "El código no debe llevar puntos ni comas");
                return;
            }
            if(!cod.matches("\\d+")){
                JOptionPane.showMessageDialog(null, "El código debe ser un entero válido");
                return;
            }
            int codigo = Integer.parseInt(cod);
            estudiante.setCodigo(codigo);
            estudiante.setNombre(nom);
            estudiante.setApellido(ape);

            if(estudianteDAO.insertarEstudiante(estudiante)){
                JOptionPane.showMessageDialog(null, "Estudiante registrado con éxito");
            }else{
                JOptionPane.showMessageDialog(null, "Estudiante no registrado");
            }
        }

        if(e.getSource() == vistaEstudiante.btn_ConsultarEstudiante){
            String cod = vistaEstudiante.jtf_codigo_estudiante.getText().trim();
            if(cod.isEmpty()){
                JOptionPane.showMessageDialog(null, "El código no puede estar vacío");
                return;
            }
            if(cod.contains(".") || cod.contains(",")){
                JOptionPane.showMessageDialog(null, "El código no debe llevar puntos ni comas");
                return;
            }
            if(!cod.matches("\\d+")){
                JOptionPane.showMessageDialog(null, "El código debe ser un entero válido");
                return;
            }
            int codigo = Integer.parseInt(cod);
            estudiante = estudianteDAO.consultarEstudiante(codigo);
            if(estudiante != null && estudiante.getCodigo()!=0){
                vistaEstudiante.jtf_nombre_estudiante.setText(estudiante.getNombre());
                vistaEstudiante.jtf_apellido_estudiante.setText(estudiante.getApellido());
            }else{
                JOptionPane.showMessageDialog(null, "Estudiante no encontrado");
            }
        }

        if(e.getSource() == vistaEstudiante.btn_ListarEstudiantes){
            DefaultTableModel model = (DefaultTableModel) vistaEstudiante.jTable_Estudiantes.getModel();
            model.setRowCount(0);
            List<Estudiante> lista = estudianteDAO.listarEstudiantes();
            for(Estudiante est : lista){
                model.addRow(new Object[]{est.getCodigo(), est.getNombre(), est.getApellido()});
            }
        }

        if(e.getSource() == vistaEstudiante.btn_BorrarEstudiante){
            String cod = vistaEstudiante.jtf_codigo_estudiante.getText().trim();
            if(cod.isEmpty()){
                JOptionPane.showMessageDialog(null, "El código no puede estar vacío");
                return;
            }
            if(!cod.matches("\\d+")){
                JOptionPane.showMessageDialog(null, "El código debe ser un entero válido");
                return;
            }
            int codigo = Integer.parseInt(cod);
            if(estudianteDAO.borrarEstudiante(codigo)){
                JOptionPane.showMessageDialog(null, "Estudiante borrado con éxito");
            }else{
                JOptionPane.showMessageDialog(null, "Estudiante no borrado");
            }
        }

        if(e.getSource() == vistaEstudiante.btn_ModificarEstudiante){
            String cod = vistaEstudiante.jtf_codigo_estudiante.getText().trim();
            String nom = vistaEstudiante.jtf_nombre_estudiante.getText().trim();
            String ape = vistaEstudiante.jtf_apellido_estudiante.getText().trim();

            if(cod.isEmpty() || nom.isEmpty() || ape.isEmpty()){
                JOptionPane.showMessageDialog(null, "No pueden haber campos vacíos");
                return;
            }
            if(!cod.matches("\\d+")){
                JOptionPane.showMessageDialog(null, "El código debe ser un entero válido");
                return;
            }
            int codigo = Integer.parseInt(cod);
            Estudiante existe = estudianteDAO.consultarEstudiante(codigo);
            if(existe == null || existe.getCodigo()==0){
                JOptionPane.showMessageDialog(null, "No existe un estudiante con ese código");
                return;
            }
            existe.setNombre(nom);
            existe.setApellido(ape);
            if(estudianteDAO.modificarEstudiante(existe)){
                JOptionPane.showMessageDialog(null, "Estudiante modificado con éxito");
            }else{
                JOptionPane.showMessageDialog(null, "Estudiante no modificado");
            }
        }
    }

}
