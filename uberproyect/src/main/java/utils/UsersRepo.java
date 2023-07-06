/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.io.IOException;
import javax.swing.JOptionPane;
import modelos.Usuario;

/**
 *
 * @author oscardavidpadillarivas
 */
public class UsersRepo {
    
    private String tag;
    private DatabaseManager dbNube;
    private FileUserManager dbLocal;
    
    public UsersRepo(){
        tag = "UsersRepo";
        dbNube = new DatabaseManager();
        dbLocal = new FileUserManager();
    }
    
    //INIT SERVICES
    public void init(){
        if (dbNube.conexion()){
            
        }
    }
    
    //DB NUBE
    public void insertarUser(Usuario user){
        
        if(dbNube.conexion()){
            //Si hay conexion agregar a la nube
            dbNube.insertUserDB(user.getName(), user.getPass(), user.getEdad(), user.getDni(), user.getTelefono(), user.getTipoUser());
            aviso("Usuario agregado correctamente");
            
        } else {
            //si no agregar a archivo
            
            registrarUsuario(user);
        }
    }
    
    private void registrarUsuario(Usuario user) {
        String tagMethod = "METHOD: registrarUsuario(): ";
        
        try {
            
            if(user.getTipoUser().equalsIgnoreCase("Conductor")) {
                
                dbLocal.agregarConductor(user);
                System.out.println(tag + tagMethod + "Conductor registrado exitosamente.");
                
            } else if(user.getTipoUser().equalsIgnoreCase("Cliente")) {
                
                dbLocal.agregarCliente(user);
                System.out.println(tag + tagMethod + "Cliente registrado exitosamente.");
                
            } else {
                
                System.out.println(tag + tagMethod + "Se ha presentado un error: Tipo de usuario no asignado");
                
            }
            
            
        } catch (IOException e) {
            
            System.out.println(tag + tagMethod + "Error al registrar usuario: " + e.getMessage());
            
        }
    }
    
    public boolean ifExist(String dni, String cel){
        
        boolean resp = false;
        
        if(dbNube.conexion()) {
            if(dbNube.ifExiste(dni)){

                resp = true;
                
            } else {
                return dbLocal.siExiste(dni, cel);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Conectate a internet por favor");
        }
        return resp;
    }
    
    public Usuario loguear(String dni, String pass){
        Usuario user = new Usuario();
        if(dbNube.conexion()){
            user = dbNube.get(dni, pass);
            
            if(user != null) {
                return user;
            } else {
                JOptionPane.showMessageDialog(null, "No existe ese usuario");
            }
            return user != null ? user: new Usuario() ;
        } else {
            return dbLocal.loguear(dni, pass);
        }
        
        
    }
    
    public boolean reading(String dniTF, String edadTF, String tellTF) {
        long dni = Long.parseLong(dniTF);
        long dniLB = 1000000;
        long dniLA = Long.parseLong("9999999999");
        int edad = Integer.parseInt(edadTF);
        long cel = Long.parseLong(tellTF);
        long celLB = 1000000000;
        long celLA = Long.parseLong("10000000000");
        
        if(dni >= dniLB && dni < dniLA){
            if(edad >= 16) {
                if(cel >= celLB && cel < celLA) {
                    
                    return true;

                } else aviso("Numero de telefono invalido");return false;
                
            } else aviso("No tiene la edad suficiente");return false;
            
        } else aviso("DNI invalido"); return false;
        
    }
    
    private void aviso(String aviso){
        JOptionPane.showMessageDialog(null, aviso);
    }

    public String get(String dni, String pass){
        return dbNube.get(dni, pass).getName();
    }
}




