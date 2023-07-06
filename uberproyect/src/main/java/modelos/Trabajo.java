/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.io.Serializable;

/**
 *
 * @author oscardavidpadillarivas
 */
public class Trabajo implements Serializable {
    
    String id = "";
    String dniCliente = "";
    String nombreCliente = "";
    String ubicacionActual = "";
    String direccion = "";
    String dineroOfrecido = "";
    String time = "";
    String estado = "";

    public Trabajo(String dniCliente, String nombreCliente, String ubicacionActual, String direccion, String dineroOfrecido, String estado, String id) {
        this.dniCliente = dniCliente;
        this.nombreCliente = nombreCliente;
        this.ubicacionActual = ubicacionActual;
        this.direccion = direccion;
        this.dineroOfrecido = dineroOfrecido;
        this.estado = estado;
        this.id = id;
    }
    
    public Trabajo() {
        
    }
    
    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDineroOfrecido() {
        return dineroOfrecido;
    }

    public void setDineroOfrecido(String dineroOfrecido) {
        this.dineroOfrecido = dineroOfrecido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getUbicacionActual() {
        return ubicacionActual;
    }

    public void setUbicacionActual(String ubicacionActual) {
        this.ubicacionActual = ubicacionActual;
    }

    public String getId() {
        return id;
    }

    public void setId(String idTrabajo) {
        this.id = idTrabajo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
