/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import vista.VistaReservasGUI;

import modelo.Reserva;
import modelo.ReservaDAO;
import modelo.Profesor;
import modelo.ProfesorDAO;
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
    
    //Listas de objetos de las clases modelo
    private List<Profesor> listadoProfesores= new ArrayList<>(); 
    private List<EquipoComputo> listadoEquiposcomputo= new ArrayList<>(); 
    
    
    
    public ControlVistaReservasGUI(){
        
        this.unaReserva= new Reserva();
        this.unaReservaDAO= new ReservaDAO();
        
        this.unProfeDAO= new ProfesorDAO();
        this.listadoProfesores= this.unProfeDAO.listarProfesores();
        
        this.unPcDAO= new EquipoComputoDAO();
        this.listadoEquiposcomputo= this.unPcDAO.listarEquipoComputo();
        
        this.vistaReservas= new VistaReservasGUI();
        
        //Se formatean los jComboBox de la GUI
        
        this.vistaReservas.jComb_profesores.removeAllItems();
        
        //Se agrega el listado de Profesores al JComboBox jComb_profesores
        for(Profesor profe : this.listadoProfesores){
            this.vistaReservas.jComb_profesores.addItem(profe);
        }
        
        this.vistaReservas.jComb_equipoComputo.removeAllItems();
        
        //Se agrega el listado de Equipos de Cómputo al JComboBox jComb_equipoComputo
        for(EquipoComputo pc: this.listadoEquiposcomputo){
            this.vistaReservas.jComb_equipoComputo.addItem(pc);
        }
        
        //Se agregan los escuchas a los botones de la GUI
        this.vistaReservas.jbtn_agregar.addActionListener(this);              
        
        this.vistaReservas.setVisible(true);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == this.vistaReservas.jbtn_agregar){
            
            this.unaReserva.setCedulaProfesor(((Profesor) this.vistaReservas.jComb_profesores.getSelectedItem()).getCedula());
            this.unaReserva.setNoInventarioPC(((EquipoComputo) this.vistaReservas.jComb_equipoComputo.getSelectedItem()).getNumInvetario());            
            
            
            if(!this.unaReservaDAO.insertarReserva(this.unaReserva)){
                JOptionPane.showMessageDialog(null, "Reserva Registrada con Éxito!!!");                
            }else{
                JOptionPane.showMessageDialog(null, "Reserva No Registrada!!!");                
            }
            
            //System.out.println(((Profesor) this.vistaReservas.jComb_profesores.getSelectedItem()).getCurso());
        }
    }
    
}
