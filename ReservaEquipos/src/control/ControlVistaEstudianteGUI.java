
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Estudiante;
import vista.VistaEstudianteGUI;

public class ControlVistaEstudianteGUI implements ActionListener{
    
    Estudiante estudiante;
    VistaEstudianteGUI vistaEstudiante;

    public ControlVistaEstudianteGUI(Estudiante estudiante, VistaEstudianteGUI vistaEstudiante) {
                
        this.estudiante = new Estudiante();
        this.vistaEstudiante = new VistaEstudianteGUI();
        this.vistaEstudiante.setVisible(true);    
        
        this.vistaEstudiante.btn_AgregarEstudiante.addActionListener(this);
        this.vistaEstudiante.btn_BorrarEstudiante.addActionListener(this);
        this.vistaEstudiante.btn_ConsultarEstudiante.addActionListener(this);
        this.vistaEstudiante.btn_ListarEstudiantes.addActionListener(this);        
    }    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
}
