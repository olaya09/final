/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

//importación de vistas
import vista.VistaMenuGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Estudiante;
import vista.VistaEstudianteGUI;

/**
 *
 * @author PROF-REC-TEC
 */
public class ControlVistaMenu implements ActionListener {
    
    //Atributos
    VistaMenuGUI menuPrincipal;
    
    //Constructor
    public ControlVistaMenu(){
        
        this.menuPrincipal= new VistaMenuGUI();
        this.menuPrincipal.setVisible(true);
        
        //Opciones Menú Archivo
        this.menuPrincipal.jmi_salir.addActionListener(this);
        
        //Opcones Menú Gestión
        this.menuPrincipal.jmi_profesor.addActionListener(this);
        this.menuPrincipal.jmi_equipoComputo.addActionListener(this);
        this.menuPrincipal.jmi_reservas.addActionListener(this);
        
        //Opciones Menú Help
        this.menuPrincipal.jmi_acercaDe.addActionListener(this);
        
        this.menuPrincipal.jmi_estudiantes.addActionListener(this);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        //Opción Salir del menú Archivo
        if(e.getSource() == this.menuPrincipal.jmi_salir){
            JOptionPane.showMessageDialog(this.menuPrincipal, "Shaolin Soccer (-_-)");
            System.exit(0);
        }
        /*******************************************************************************************************************************/
        //Opción Profesores del menú Gestión
        if(e.getSource() == this.menuPrincipal.jmi_profesor){
            ControlVistaProfesorGUI guiVistaProfe= new ControlVistaProfesorGUI();
        }
        
        //Opción Equipos de Cómputo del menú Gestión
        if(e.getSource() == this.menuPrincipal.jmi_equipoComputo){
            ControlVistaEquipoComputoGUI guiVistaEquipoCompu= new ControlVistaEquipoComputoGUI();
        }
        
        //Opción Reservaso del menú Gestión
        if(e.getSource() == this.menuPrincipal.jmi_reservas){
            ControlVistaReservasGUI guiVistaReservas= new ControlVistaReservasGUI();
        }
        if(e.getSource() == this.menuPrincipal.jmi_estudiantes){
            ControlVistaEstudianteGUI quiVistaEstudiantes = new ControlVistaEstudianteGUI(new Estudiante(), new VistaEstudianteGUI());
        }
        /*******************************************************************************************************************************/
        //Opción "Acerca de" del menú Help
        if(e.getSource() == this.menuPrincipal.jmi_acercaDe){
            JOptionPane.showMessageDialog(this.menuPrincipal, "Sistema de Gestión de Reservas\nVersión 3\nTodos los derechos reservados");                
        }
    }
    
}
