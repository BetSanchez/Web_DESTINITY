/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hoteles;

/**
 *
 * @author bet10
 */
public class Servicios {
    private double costo;
    private String descripcion;
    private int idAlojamiento;
    private int idServicio;
    private String nombreAlojamiento;
    private String proveedor;

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public double getCosto() {
        return costo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getIdAlojamiento() {
        return idAlojamiento;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public String getNombreAlojamiento() {
        return nombreAlojamiento;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdAlojamiento(int idAlojamiento) {
        this.idAlojamiento = idAlojamiento;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public void setNombreAlojamiento(String nombreAlojamiento) {
        this.nombreAlojamiento = nombreAlojamiento;
    }
    
}
