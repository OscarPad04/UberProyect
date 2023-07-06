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
public class Usuario implements Serializable {
    
    private String name = "";
    private String dni = "";
    private String pass = "";
    private String edad = "";
    private String telefono = "";
    private String tipoUser = "";
    private int premium = 0;
    private int enNube = 0;

    public Usuario(String name, String dni, String pass, String edad, String telefono, String tipoUser) {
        this.name = name;
        this.dni = dni;
        this.pass = pass;
        this.edad = edad;
        this.telefono = telefono;
        this.tipoUser = tipoUser;
    }
    
    public Usuario(){
        
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }
    
    public void mostrar(){
        System.out.println("Nombre: " + name);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(String tipoUser) {
        this.tipoUser = tipoUser;
    }
    
    public String show(){
    
        return "Nombre: " + name + " " + "DNI: " + dni + " " + "Tipo de usuario: " + tipoUser;
    }
    
    
    public void setPremium(){
        premium = 1;
    }
    
    public int isPremium(){
        return premium; 
    }
    public void enNube(){
        enNube = 1;
    }
    
    
    
}
