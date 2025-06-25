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

public class ControlVistaReservasGUI implements ActionListener {
    private VistaReservasGUI vistaReservas;
    private Reserva unaReserva;
    private ReservaDAO unaReservaDAO;
    private ProfesorDAO unProfeDAO;
    private EstudianteDAO unEstDAO;
    private EquipoComputoDAO unPcDAO;
    private List<Profesor> listadoProfesores = new ArrayList<>();
    private List<Estudiante> listadoEstudiantes = new ArrayList<>();
    private List<EquipoComputo> listadoEquiposcomputo = new ArrayList<>();

    public ControlVistaReservasGUI() {
        unaReserva = new Reserva();
        unaReservaDAO = new ReservaDAO();
        unProfeDAO = new ProfesorDAO();
        listadoProfesores = unProfeDAO.listarProfesores();
        unEstDAO = new EstudianteDAO();
        listadoEstudiantes = unEstDAO.listarEstudiantes();
        unPcDAO = new EquipoComputoDAO();
        listadoEquiposcomputo = unPcDAO.listarEquipoComputo();
        vistaReservas = new VistaReservasGUI();

        vistaReservas.jComb_profesores.removeAllItems();
        vistaReservas.jComb_estudiantes.removeAllItems();
        vistaReservas.jComb_equipoComputo.removeAllItems();

        for (Profesor profe : listadoProfesores) {
            vistaReservas.jComb_profesores.addItem(profe);
        }
        for (Estudiante est : listadoEstudiantes) {
            vistaReservas.jComb_estudiantes.addItem(est);
        }
        for (EquipoComputo pc : listadoEquiposcomputo) {
            vistaReservas.jComb_equipoComputo.addItem(pc);
        }

        vistaReservas.jComb_profesores.setSelectedIndex(-1);
        vistaReservas.jComb_estudiantes.setSelectedIndex(-1);

        vistaReservas.jbtn_agregar.addActionListener(this);
        vistaReservas.btn_ConsultarReservas.addActionListener(this);
        vistaReservas.btn_ListarReservas.addActionListener(this);

        listarReservasEnTabla();
        vistaReservas.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaReservas.jbtn_agregar) {
            boolean profesorSel = vistaReservas.jComb_profesores.getSelectedIndex() >= 0;
            boolean estudianteSel = vistaReservas.jComb_estudiantes.getSelectedIndex() >= 0;
            if (profesorSel == estudianteSel) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar solo un profesor o un estudiante");
                return;
            }
            int idPersona;
            if (estudianteSel) {
                idPersona = ((Estudiante) vistaReservas.jComb_estudiantes.getSelectedItem()).getCodigo();
            } else {
                idPersona = ((Profesor) vistaReservas.jComb_profesores.getSelectedItem()).getCedula();
            }
            int inventario = ((EquipoComputo) vistaReservas.jComb_equipoComputo.getSelectedItem()).getNumInvetario();
            unaReserva.setCedulaProfesor(idPersona);
            unaReserva.setNoInventarioPC(inventario);
            if (unaReservaDAO.insertarReserva(unaReserva)) {
                JOptionPane.showMessageDialog(null, "Reserva Registrada con Éxito!!!\nCódigo: " + unaReserva.getCodigo());
                listarReservasEnTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Reserva No Registrada!!!");
            }
        }
        if (e.getSource() == vistaReservas.btn_ConsultarReservas) {
            String cod = vistaReservas.Jtf_ConsultarReservas.getText().trim();
            if (!cod.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Código inválido");
                return;
            }
            int codigo = Integer.parseInt(cod);
            Reserva r = unaReservaDAO.consultarReserva(codigo);
            if (r.getCodigo() == 0) {
                JOptionPane.showMessageDialog(null, "Reserva no encontrada");
                return;
            }
            for (int i = 0; i < listadoProfesores.size(); i++) {
                if (listadoProfesores.get(i).getCedula() == r.getCedulaProfesor()) {
                    vistaReservas.jComb_profesores.setSelectedIndex(i);
                    vistaReservas.jComb_estudiantes.setSelectedIndex(-1);
                    break;
                }
            }
            for (int i = 0; i < listadoEstudiantes.size(); i++) {
                if (listadoEstudiantes.get(i).getCodigo() == r.getCedulaProfesor()) {
                    vistaReservas.jComb_estudiantes.setSelectedIndex(i);
                    vistaReservas.jComb_profesores.setSelectedIndex(-1);
                    break;
                }
            }
            for (int i = 0; i < listadoEquiposcomputo.size(); i++) {
                if (listadoEquiposcomputo.get(i).getNumInvetario() == r.getNoInventarioPC()) {
                    vistaReservas.jComb_equipoComputo.setSelectedIndex(i);
                    break;
                }
            }
        }
        if (e.getSource() == vistaReservas.btn_ListarReservas) {
            listarReservasEnTabla();
        }
    }

    private void listarReservasEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) vistaReservas.jTable_Reservas.getModel();
        modelo.setRowCount(0);
        List<Reserva> lista = unaReservaDAO.listarReservas();
        for (Reserva r : lista) {
            String nombre = "", apellido = "", marca = "";
            Profesor p = unProfeDAO.consultarProfesor(r.getCedulaProfesor());
            if (p != null && p.getCedula() != 0) {
                nombre = p.getNombre();
                apellido = p.getApellido();
            } else {
                Estudiante es = unEstDAO.consultarEstudiante(r.getCedulaProfesor());
                if (es != null && es.getCodigo() != 0) {
                    nombre = es.getNombre();
                    apellido = es.getApellido();
                }
            }
            EquipoComputo pc = unPcDAO.consultarEquipoComputo(r.getNoInventarioPC());
            if (pc != null) {
                marca = pc.getMarca();
            }
            modelo.addRow(new Object[]{
                r.getCodigo(), r.getCedulaProfesor(), nombre,
                apellido, marca, r.getFechaRecogida(), r.getFechaEntrega()
            });
        }
    }
}
