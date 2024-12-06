/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hoteles;

/**
 *
 * @author bet10
 */
public class Habitacion {
    
    private int idHabitacion;
    private String identificador;
    private int capacidad;
    private double costo;
    private int idAlojamiento;
    private String proveedor;

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
    

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public String getIdentificador() {
        return identificador;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

   

    public int getIdAlojamiento() {
        return idAlojamiento;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    

    public void setIdAlojamiento(int idAlojamiento) {
        this.idAlojamiento = idAlojamiento;
    }

}
