/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uberproyect.controlador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import vistas.VistaPrincipal;
/**
 *
 * @author oscardavidpadillarivas
 */

public class Main {
 
    public static void main(String[] args) {
        
        archivo();
        
        VistaPrincipal vista = new VistaPrincipal();
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
    }
    
    public static void archivo(){
        File archivo1 = new File("UsersClientes.dat");
        File archivo2 = new File("UsersConductores.dat");
        File archivoW = new File("Works.dat");
        
        if (!archivo1.exists()) {
            
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(archivo1));
                BufferedWriter bwC = new BufferedWriter(new FileWriter(archivo2));
                BufferedWriter bw2 = new BufferedWriter(new FileWriter(archivoW));
                bw.close();
                bwC.close();
                bw2.close();
                System.out.println("Archivos creados exitosamente.");

            } catch (IOException ex) {
                System.out.println("Error al crear el archivo: " + ex.getMessage());
            }
            
        }
    }
    
}
