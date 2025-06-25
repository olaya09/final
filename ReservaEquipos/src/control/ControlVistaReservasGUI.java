package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
    private List<Profesor> listadoProfesores;
    private List<Estudiante> listadoEstudiantes;
    private List<EquipoComputo> listadoEquiposComputo;

    public ControlVistaReservasGUI() {
        unaReserva = new Reserva();
        unaReservaDAO = new ReservaDAO();
        unProfeDAO = new ProfesorDAO();
        listadoProfesores = unProfeDAO.listarProfesores();
        unEstDAO = new EstudianteDAO();
        listadoEstudiantes = unEstDAO.listarEstudiantes();
        unPcDAO = new EquipoComputoDAO();
        listadoEquiposComputo = unPcDAO.listarEquipoComputo();

        vistaReservas = new VistaReservasGUI();

        // cargar combos con placeholder
        vistaReservas.jComb_profesores.removeAllItems();
        vistaReservas.jComb_estudiantes.removeAllItems();
        vistaReservas.jComb_equipoComputo.removeAllItems();

        Profesor pSel = new Profesor();
        pSel.setCedula(0);
        pSel.setNombre("Seleccione");
        pSel.setApellido("");
        vistaReservas.jComb_profesores.addItem(pSel);
        for (Profesor profe : listadoProfesores) {
            vistaReservas.jComb_profesores.addItem(profe);
        }

        Estudiante eSel = new Estudiante();
        eSel.setCodigo(0);
        eSel.setNombre("Seleccione");
        eSel.setApellido("");
        vistaReservas.jComb_estudiantes.addItem(eSel);
        for (Estudiante est : listadoEstudiantes) {
            vistaReservas.jComb_estudiantes.addItem(est);
        }

        EquipoComputo pcSel = new EquipoComputo();
        pcSel.setNumInvetario(0);
        pcSel.setMarca("Seleccione");
        vistaReservas.jComb_equipoComputo.addItem(pcSel);
        for (EquipoComputo pc : listadoEquiposComputo) {
            vistaReservas.jComb_equipoComputo.addItem(pc);
        }

        vistaReservas.jComb_profesores.setSelectedIndex(0);
        vistaReservas.jComb_estudiantes.setSelectedIndex(0);
        vistaReservas.jComb_equipoComputo.setSelectedIndex(0);

        // listeners para alternar selección
        vistaReservas.jComb_profesores.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    vistaReservas.jComb_estudiantes.setSelectedIndex(0);
                }
            }
        });
        vistaReservas.jComb_estudiantes.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    vistaReservas.jComb_profesores.setSelectedIndex(0);
                }
            }
        });

        // botones
        vistaReservas.jbtn_agregar.addActionListener(this);
        vistaReservas.btn_ConsultarReservas.addActionListener(this);
        vistaReservas.btn_ListarReservas.addActionListener(this);

        listarReservasEnTabla();
        vistaReservas.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaReservas.jbtn_agregar) {
            boolean profSel = vistaReservas.jComb_profesores.getSelectedIndex() > 0;
            boolean estSel  = vistaReservas.jComb_estudiantes.getSelectedIndex() > 0;
            if (profSel == estSel) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar solo un profesor o un estudiante");
                return;
            }
            Reserva r = new Reserva();
            if (estSel) {
                r.setCodigoEstudiante(((Estudiante) vistaReservas.jComb_estudiantes.getSelectedItem()).getCodigo());
            } else {
                r.setCedulaProfesor(((Profesor) vistaReservas.jComb_profesores.getSelectedItem()).getCedula());
            }
            r.setNoInventarioPC(((EquipoComputo) vistaReservas.jComb_equipoComputo.getSelectedItem()).getNumInvetario());
            if (unaReservaDAO.insertarReserva(r)) {
                JOptionPane.showMessageDialog(null, "Reserva Registrada con Éxito!!!\nCódigo: " + r.getCodigo());
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
            // setear combos según reserva
            vistaReservas.jComb_profesores.setSelectedIndex(0);
            vistaReservas.jComb_estudiantes.setSelectedIndex(0);
            for (int i = 0; i < listadoProfesores.size(); i++) {
                if (listadoProfesores.get(i).getCedula() == r.getCedulaProfesor()) {
                    vistaReservas.jComb_profesores.setSelectedIndex(i + 1);
                    break;
                }
            }
            for (int i = 0; i < listadoEstudiantes.size(); i++) {
                if (listadoEstudiantes.get(i).getCodigo() == r.getCodigoEstudiante()) {
                    vistaReservas.jComb_estudiantes.setSelectedIndex(i + 1);
                    break;
                }
            }
            for (int i = 0; i < listadoEquiposComputo.size(); i++) {
                if (listadoEquiposComputo.get(i).getNumInvetario() == r.getNoInventarioPC()) {
                    vistaReservas.jComb_equipoComputo.setSelectedIndex(i + 1);
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
            String cedulaProf = "", codigoEst = "";
            Profesor p = unProfeDAO.consultarProfesor(r.getCedulaProfesor());
            if (p != null && p.getCedula() != 0) {
                cedulaProf = String.valueOf(p.getCedula());
                nombre = p.getNombre();
                apellido = p.getApellido();
            } else {
                Estudiante es = unEstDAO.consultarEstudiante(r.getCodigoEstudiante());
                if (es != null && es.getCodigo() != 0) {
                    codigoEst = String.valueOf(es.getCodigo());
                    nombre = es.getNombre();
                    apellido = es.getApellido();
                }
            }
            EquipoComputo pc = unPcDAO.consultarEquipoComputo(r.getNoInventarioPC());
            if (pc != null) {
                marca = pc.getMarca();
            }
            modelo.addRow(new Object[]{
                r.getCodigo(), cedulaProf, codigoEst, nombre,
                apellido, marca, r.getFechaRecogida(), r.getFechaEntrega()
            });
        }
    }
}
