/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author PROF-REC-TEC
 */
public class EquipoComputo {
    //Atributos
    int numInvetario;
    String marca;
    int anhoCompra;
    
    //Constructor
    public EquipoComputo(){}
    
    //métodos get y set

    public int getNumInvetario() {
        return numInvetario;
    }

    public void setNumInvetario(int numInvetario) {
        this.numInvetario = numInvetario;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAnhoCompra() {
        return anhoCompra;
    }

    public void setAnhoCompra(int anhoCompra) {
        this.anhoCompra = anhoCompra;
    }
    
    //Metodo que permite mostrar la información deseada en el JComboBox
    @Override
    public String toString(){
        return "noInventario: "+this.numInvetario+" - Marca: "+this.marca;
    }
    
    
}
