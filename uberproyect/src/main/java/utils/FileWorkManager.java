/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import modelos.Trabajo;
import modelos.Usuario;

/**
 *
 * @author oscardavidpadillarivas
 */
public class FileWorkManager {
    
    private String archivoTrabajos;
    private File archivo;
    
    public FileWorkManager(){
        
        archivoTrabajos = "Works.dat";
        
    }
    
    private String formato(Trabajo work){
        
        return work.getDniCliente()+"---"
              +work.getNombreCliente()+"---"
              +work.getUbicacionActual()+"---"
              +work.getDireccion()+"---"
              +work.getDineroOfrecido()+"---"
              +work.getEstado()+"---"
              +work.getId();
        
    }
    
    private File getFile(String nombre){
        
        archivo = new File(nombre);
        return archivo;
        
    }
    
    public String crearID(String idCliente){
        Date fecha = new Date();
        System.out.println(fecha.toString());
        SimpleDateFormat formatoFecha = new SimpleDateFormat("ddMMyyyyHHmmss");
        String id = formatoFecha.format(fecha);
        System.out.println(id);
        
        return id+idCliente;
    }
    
    public void agregarTrabajo(Trabajo work) throws IOException{
        
        BufferedWriter writ;
        
        if( getFile(archivoTrabajos).exists() ) {
            
            writ  = new BufferedWriter(new FileWriter(getFile(archivoTrabajos), true));
            
        }

        else
            
            writ  = new BufferedWriter(new FileWriter(getFile(archivoTrabajos)));
            writ.write(formato(work));
            writ.newLine();
            
        try {
            
            writ.close();
        } catch (IOException e) {
            
            JOptionPane.showMessageDialog(null, "Haa ocurrido un error: "+e);
            
        }writ.close();
    } 
    
    public void limpiarArchivo() {
        
        try {
            FileWriter fileWriter = new FileWriter(archivoTrabajos);
            fileWriter.write("");
            fileWriter.close();
            System.out.println("El archivo se ha limpiado y está vacío.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public Trabajo leer(){
        
        Trabajo work = new Trabajo();

        try (BufferedReader br = new BufferedReader(new FileReader(archivoTrabajos))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("---");
                
                work = new Trabajo(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], datos[6]);

                return work;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return work;
    }
    
}
