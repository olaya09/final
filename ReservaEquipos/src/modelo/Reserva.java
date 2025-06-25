/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author PROF-REC-TEC
 */
public class Reserva {
    //atributos
    int codigo;
    int cedulaProfesor;
    int noInventarioPC;
    String fechaEntrega;
    String fechaRecogida;
    
    //constructor
    public Reserva(){}
    
    //Métodos get y set

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCedulaProfesor() {
        return cedulaProfesor;
    }

    public void setCedulaProfesor(int cedulaProfesor) {
        this.cedulaProfesor = cedulaProfesor;
    }

    public int getNoInventarioPC() {
        return noInventarioPC;
    }

    public void setNoInventarioPC(int noInventarioPC) {
        this.noInventarioPC = noInventarioPC;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getFechaRecogida() {
        return fechaRecogida;
    }

    public void setFechaRecogida(String fechaRecogida) {
        this.fechaRecogida = fechaRecogida;
    }
    
}
