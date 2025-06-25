/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author UNIVALLE
 */
public class Profesor {
    
    //Atributos
    private int cedula;
    private String nombre;
    private String apellido;
    private String curso;
    
    //Constructor vacío
    public Profesor(){}
    
    //Métodos get() y set()

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
    
    //Metodo que permite mostrar la información deseada en el JComboBox
    @Override
    public String toString(){
        return "Céula: "+this.getCedula()+" - Nombre: "+this.nombre+" "+this.apellido;
    }
}
