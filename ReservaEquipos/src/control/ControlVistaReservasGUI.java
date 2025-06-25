/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

import vista.VistaReservasGUI;

import modelo.Reserva;
import modelo.ReservaDAO;
import modelo.Profesor;
import modelo.ProfesorDAO;
import modelo.Estudiante;
import modelo.EstudianteDAO;
import modelo.EquipoComputo;
import modelo.EquipoComputoDAO;

/**
 *
 * @author PROF-REC-TEC
 */
public class ControlVistaReservasGUI implements ActionListener{
    //Atributos
    private VistaReservasGUI vistaReservas;
    
    private Reserva unaReserva;
    private ReservaDAO unaReservaDAO;
    
    private ProfesorDAO unProfeDAO;
    private EquipoComputoDAO unPcDAO;
    private EstudianteDAO unEstDAO;
    
    //Listas de objetos de las clases modelo
    private List<Profesor> listadoProfesores= new ArrayList<>();
    private List<EquipoComputo> listadoEquiposcomputo= new ArrayList<>();
    private List<Estudiante> listadoEstudiantes = new ArrayList<>();
    
    
    
    public ControlVistaReservasGUI(){
        
        this.unaReserva= new Reserva();
        this.unaReservaDAO= new ReservaDAO();
        
        this.unProfeDAO= new ProfesorDAO();
        this.listadoProfesores= this.unProfeDAO.listarProfesores();

        this.unEstDAO = new EstudianteDAO();
        this.listadoEstudiantes = this.unEstDAO.listarEstudiantes();
        
        this.unPcDAO= new EquipoComputoDAO();
        this.listadoEquiposcomputo= this.unPcDAO.listarEquipoComputo();
        
        this.vistaReservas= new VistaReservasGUI();
        
        //Se formatean los jComboBox de la GUI
        this.vistaReservas.jComb_profesores.removeAllItems();
        this.vistaReservas.jComb_estudiantes.removeAllItems();

        //Se agrega el listado de Profesores al JComboBox jComb_profesores
        for(Profesor profe : this.listadoProfesores){
            this.vistaReservas.jComb_profesores.addItem(profe);
        }

        //Se agrega el listado de Estudiantes al JComboBox jComb_estudiantes
        for(Estudiante est : this.listadoEstudiantes){
            this.vistaReservas.jComb_estudiantes.addItem(est);
        }
        
        this.vistaReservas.jComb_equipoComputo.removeAllItems();
        
        //Se agrega el listado de Equipos de Cómputo al JComboBox jComb_equipoComputo
        for(EquipoComputo pc: this.listadoEquiposcomputo){
            this.vistaReservas.jComb_equipoComputo.addItem(pc);
        }
        
        //Se agregan los escuchas a los botones de la GUI
        this.vistaReservas.jbtn_agregar.addActionListener(this);
        this.vistaReservas.btn_ConsultarReservas.addActionListener(this);
        this.vistaReservas.btn_ListarReservas.addActionListener(this);

        //Por defecto no hay elemento seleccionado en los combos
        this.vistaReservas.jComb_profesores.setSelectedIndex(-1);
        this.vistaReservas.jComb_estudiantes.setSelectedIndex(-1);

        this.vistaReservas.setVisible(true);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == this.vistaReservas.jbtn_agregar){
            boolean profesorSel = this.vistaReservas.jComb_profesores.getSelectedIndex() >= 0;
            boolean estudianteSel = this.vistaReservas.jComb_estudiantes.getSelectedIndex() >= 0;

            if(profesorSel == estudianteSel){
                JOptionPane.showMessageDialog(null, "Debe seleccionar solo un profesor o un estudiante");
                return;
            }

            int idPersona;
            if(estudianteSel){
                idPersona = ((Estudiante)this.vistaReservas.jComb_estudiantes.getSelectedItem()).getCodigo();
            }else{
                idPersona = ((Profesor)this.vistaReservas.jComb_profesores.getSelectedItem()).getCedula();
            }

            this.unaReserva.setCedulaProfesor(idPersona);
            this.unaReserva.setNoInventarioPC(((EquipoComputo) this.vistaReservas.jComb_equipoComputo.getSelectedItem()).getNumInvetario());

            if(this.unaReservaDAO.insertarReserva(this.unaReserva)){
                JOptionPane.showMessageDialog(null, "Reserva Registrada con Éxito!!!\nCódigo: " + this.unaReserva.getCodigo());
            }else{
                JOptionPane.showMessageDialog(null, "Reserva No Registrada!!!");
            }
        }

        if(e.getSource() == this.vistaReservas.btn_ConsultarReservas){
            String cod = this.vistaReservas.Jtf_ConsultarReservas.getText().trim();
            if(!cod.matches("\\d+")){
                JOptionPane.showMessageDialog(null, "Código inválido");
                return;
            }
            int codigo = Integer.parseInt(cod);
            Reserva r = this.unaReservaDAO.consultarReserva(codigo);
            if(r.getCodigo()==0){
                JOptionPane.showMessageDialog(null, "Reserva no encontrada");
                return;
            }
            for(int i=0;i<this.listadoProfesores.size();i++){
                if(this.listadoProfesores.get(i).getCedula() == r.getCedulaProfesor()){
                    this.vistaReservas.jComb_profesores.setSelectedIndex(i);
                    this.vistaReservas.jComb_estudiantes.setSelectedIndex(-1);
                    break;
                }
            }
            for(int i=0;i<this.listadoEstudiantes.size();i++){
                if(this.listadoEstudiantes.get(i).getCodigo() == r.getCedulaProfesor()){
                    this.vistaReservas.jComb_estudiantes.setSelectedIndex(i);
                    this.vistaReservas.jComb_profesores.setSelectedIndex(-1);
                    break;
                }
            }
            for(int i=0;i<this.listadoEquiposcomputo.size();i++){
                if(this.listadoEquiposcomputo.get(i).getNumInvetario() == r.getNoInventarioPC()){
                    this.vistaReservas.jComb_equipoComputo.setSelectedIndex(i);
                    break;
                }
            }
        }

        if(e.getSource() == this.vistaReservas.btn_ListarReservas){
            listarReservasEnTabla();
        }
    }

    private void listarReservasEnTabla(){
        DefaultTableModel modelo = (DefaultTableModel) this.vistaReservas.jTable_Reservas.getModel();
        modelo.setRowCount(0);
        List<Reserva> lista = this.unaReservaDAO.listarReservas();
        for(Reserva r : lista){
            String nombre="", apellido="", marca="";
            Profesor p = this.unProfeDAO.consultarProfesor(r.getCedulaProfesor());
            if(p != null && p.getCedula()!=0){
                nombre = p.getNombre();
                apellido = p.getApellido();
            }else{
                Estudiante es = this.unEstDAO.consultarEstudiante(r.getCedulaProfesor());
                if(es != null && es.getCodigo()!=0){
                    nombre = es.getNombre();
                    apellido = es.getApellido();
                }
            }
            EquipoComputo pc = this.unPcDAO.consultarEquipoComputo(r.getNoInventarioPC());
            if(pc != null){
                marca = pc.getMarca();
            }
            modelo.addRow(new Object[]{r.getCodigo(), r.getCedulaProfesor(), nombre, apellido, marca, r.getFechaRecogida(), r.getFechaEntrega()});
        }
    }

}
