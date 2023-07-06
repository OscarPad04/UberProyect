/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelos.Usuario;

/**
 *
 * @author oscardavidpadillarivas
 */
public class FileUserManager {

    private String tag;
    private String fileUserCliente;
    private String fileUserConductor;
    private File archivo;
    private String separador = "***";

    //CONSTRUCTOR
    public FileUserManager() {
        tag = "FILE_USER_MANAGER: ";
        fileUserCliente = "UsersClientes.dat";
        fileUserConductor = "UsersConductores.dat";
    }
    
    
    //PROBANDO
    public void leerBinario() throws FileNotFoundException, IOException{
        String tagPersonal = "METHOD leedBinario: ";
        try{
            Usuario user = new Usuario();
            FileInputStream archivo = new FileInputStream(fileUserCliente);
            ObjectInputStream lectura = new ObjectInputStream(archivo);

            while (true) {
                user = (Usuario) lectura.readObject();
                user.mostrar();
            }
        } catch (EOFException ex){
            System.out.println( tag + tagPersonal + "Ha ocurrido un error" + ex);
        } catch (IOException e) {
            System.out.println( tag + tagPersonal + "Ha ocurrido un error" + e);
        } catch (ClassNotFoundException ex) {
            System.out.println( tag + tagPersonal + "Ha ocurrido un error" + ex);
        }
        
    }

    //USANDO
    public void añadirConductor(Usuario user) throws IOException {
        String tagPersonal = "METHOD agregarConductor: ";
        RandomAccessFile file = new RandomAccessFile(fileUserConductor, "rw");
            file.seek(file.length());
            
            try {
                // opening file in append mode
                FileOutputStream fileOut = new FileOutputStream(fileUserConductor, true);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);

                // writing user objects to file
                out.writeObject(user);

                // closing file
                out.close();
                fileOut.close();
                    
            } catch(Exception e) {
                System.out.println(tag + tagPersonal + "Error insertan user: " + e);
            }
        }
            
    //USANDO
    public void añadirCliente(Usuario user) throws IOException {
        String tagPersonal = "METHOD agregarCliente: "; 
            
            
            try {
                // opening file in append mode
            FileOutputStream fileOut = new FileOutputStream(fileUserCliente, true);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            // writing user objects to file
            out.writeObject(user);

            // closing file
            out.close();
            fileOut.close();
                    
            } catch(Exception e) {
                System.out.println(tag + tagPersonal + "Error insertan user: " + e);
            }
        }
        
    //NUEVO
    public Usuario login(String dni, String pass){
        String tagPersonal = "METHOD login: ";
        int fin = 0;
        
        try{
            FileInputStream fis = new FileInputStream(fileUserCliente);
            ObjectInputStream ois;
            
            while(fis.available()>0){
                ois = new ObjectInputStream(fis);
                Usuario user = (Usuario) ois.readObject();
                
                if((user.getDni()).equals(dni) && user.getPass().equals(pass)){
                    JOptionPane.showMessageDialog(null, "Bienvenido");
                    fin++;
                    return user;
                }
            }
            fis.close();
            
        }catch(Exception e){
            
            System.out.println(tag + tagPersonal + "Ha ocurrido un error " + e);
        }
        return null;
    }
    
    public void loquesea(Usuario user) {
    try {
            
            File archivos = new File(fileUserCliente);
  
            // Datos a guardar
            
            // Crear el flujo de salida de archivo binario
            FileOutputStream archivo = new FileOutputStream(archivos, true);
            
            // Crear el objeto ObjectOutputStream
            ObjectOutputStream data = new ObjectOutputStream(archivo);
            
            // Escribir los datos serializados en el archivo
            data.writeObject(user);
       
            // Cerrar el flujo de salida y el objeto ObjectOutputStream
            data.close();
            archivo.close();
            
            System.out.println("Los datos han sido guardados en el archivo.");
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void mostrar() throws FileNotFoundException, IOException, ClassNotFoundException {
        File archivos = new File(fileUserCliente);
        FileInputStream archivo = new FileInputStream(archivos);
        ObjectInputStream data = new ObjectInputStream(archivo);
        
        try {
            
            
            
            while (true) {
                Usuario user = (Usuario) data.readObject();
                System.out.println("Nombre: " + user.getName());
                
            }
            
        } catch (EOFException ex){
            data.close();
            archivo.close();
            System.out.println("Fin del archivo");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    //USANDO
    public void leerFichero() throws FileNotFoundException, IOException{
        
        String tagPersonal = "METHOD leerFichero: ";
        
        
        try{
           // opening file in read mode
            FileInputStream fileIn = new FileInputStream("user.bin");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            // reading user objects from file
            Usuario user = (Usuario) in.readObject();
            
            System.out.println("Nombre: " + user.getName() + " " + "DNI: " + user.getDni());
            
            // closing file
            in.close();
            fileIn.close();
            
        } catch (EOFException ex){
            System.out.println(tag + tagPersonal + "Fin del archivo");
            return;
        }catch(Exception e){
            System.out.println(tag + tagPersonal + "Ha ocurrido un error " + e);
        } 
    }
    
    //USANDO
    public boolean validar(String dni) throws IOException, FileNotFoundException, ClassNotFoundException {
        String tagPersonal = "METHOD validar: ";
        
        if(validarDNICliente(dni) || validarDNIConductor(dni)) {
            
            System.out.println(tag + tagPersonal + "Se ha detectado un usuario con ese DNI");
            return true;
            
        } else {
            System.out.println(tag + tagPersonal + "Via libre para registrar usuario");
            return false;
            
        }
        
    }
   
    //USANDO
    public boolean validarDNICliente(String dni) throws FileNotFoundException, IOException, ClassNotFoundException{
        
        boolean valida = false;
        String tagPersonal = "METHOD validarDNICliente: ";
        
        
        RandomAccessFile raf = new RandomAccessFile(fileUserCliente, "rw");
        ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(raf.getFD()));
        
        try {
            
            raf.seek(0);
            
            Usuario usuario;
            while (true) {
                usuario = (Usuario) entrada.readObject();
                if (usuario.getDni().equals(dni)) {
                    valida = true;
                    entrada.close();
                    raf.close();
                    return true;
                }
            }
            
        } catch (EOFException e) {
            entrada.close();
            raf.close();
            return false;
            
        }
    }
    
    //USANDO
    public boolean validarDNIConductor(String dni) throws FileNotFoundException, IOException, ClassNotFoundException{
        
        String tagPersonal = "METHOD validarDNIConductor: ";
        
        RandomAccessFile raf = new RandomAccessFile(fileUserConductor, "rw");
        ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(raf.getFD()));
        
        try  {
            
            raf.seek(0);
            
            Usuario usuario;
            while (true) {
                usuario = (Usuario) entrada.readObject();
                if (usuario.getDni().equals(dni)) {
                    entrada.close();
                    raf.close();
                    return true;
                }
            }
            
        } catch (EOFException e) {
            return false;
            
        }
        
        
    }
    
    /////////////////////////////CODIGO EXPERIMENTAL////////////////////////////////////////////////
    
    public boolean siExiste(String dni, String cell){
        boolean existe = false;
        try (BufferedReader br = new BufferedReader(new FileReader(fileUserCliente))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("---");

                String nombre = datos[0];
                
                Usuario user = new Usuario(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5]);
                System.out.println("DNI: " + user.getDni() + " Tell: " + user.getTelefono());
                
                if(dni.equals(user.getDni()) || cell.equals(user.getTelefono())) {
                    existe = true;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if(!existe) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileUserConductor))) {
            String linea;

                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split("---");

                    String nombre = datos[0];

                    Usuario user = new Usuario(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5]);
                    System.out.println("DNI: " + user.getDni() + " Tell: " + user.getTelefono());

                    if(dni.equals(user.getDni()) || cell.equals(user.getTelefono())) {
                        existe = true;
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return existe;
    }
    
    private String formato(Usuario datos){
        
        return datos.getName()+"---"
              +datos.getDni()+"---"
              +datos.getPass()+"---"
              +datos.getEdad()+"---"
              +datos.getTelefono()+"---"
              +datos.getTipoUser();
        
    }
    
    private File getFile(String nombre){
        
        archivo = new File(nombre);
        return archivo;
        
    }
    
    public void agregarConductor(Usuario user) throws IOException{
        
        BufferedWriter writ;
        
        if( getFile(fileUserConductor).exists() ) {
            
            writ  = new BufferedWriter(new FileWriter(getFile(fileUserConductor), true));
            
        }

        else
            
            writ  = new BufferedWriter(new FileWriter(getFile(fileUserConductor)));
            writ.write(formato(user));
            writ.newLine();
        try {
            
            writ.close();
        } catch (IOException e) {
            
            JOptionPane.showMessageDialog(null, "Haa ocurrido un error: "+e);
            
        }writ.close();
    }
    
    public void agregarCliente(Usuario user) throws IOException{
        
        BufferedWriter writ;
        
        if( getFile(fileUserCliente).exists() ) {
            
            writ  = new BufferedWriter(new FileWriter(getFile(fileUserCliente), true));
            
        }

        else
            
            writ  = new BufferedWriter(new FileWriter(getFile(fileUserCliente)));
            writ.write(formato(user));
            writ.newLine();
        try {
            
            writ.close();
        } catch (IOException e) {
            
            JOptionPane.showMessageDialog(null, "Haa ocurrido un error: "+e);
            
        }writ.close();
    }
    
    public void leer(){

        try (BufferedReader br = new BufferedReader(new FileReader(fileUserCliente))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("---");

                String nombre = datos[0];
                
                Usuario user = new Usuario(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5]);

                System.out.println("Nombre: " + user.getName() + " DNI: " + user.getDni() + " Tipo: " + user.getTipoUser());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Usuario loguear(String dni, String pass){
        Usuario usu = null;
        boolean listo = false;
        try (BufferedReader br = new BufferedReader(new FileReader(fileUserCliente))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("---");

                String nombre = datos[0];
                
                Usuario user = new Usuario(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5]);
                
                if(dni.equals(user.getDni()) && pass.equals(user.getPass())) {
                    usu = user;
                    listo = true;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if(!listo) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileUserConductor))) {
            String linea;

                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split("---");

                    String nombre = datos[0];

                    Usuario user = new Usuario(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5]);

                    if(dni.equals(user.getDni()) && pass.equals(user.getPass())) {
                        usu = user;
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return usu;
    }
    
}
