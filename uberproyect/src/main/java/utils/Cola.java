/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelos.Trabajo;

/**
 *
 * @author oscardavidpadillarivas
 */
// COLA CON PRIORIDAD DE TIEMPO
public class Cola {
    
    private Nodo cabeza;
    private Nodo cola;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
    
    public void addToCola(Nodo nodo){
        if(cabeza == null) {
            
            cabeza = nodo;
            cola = nodo;
            
        } else {
            Nodo temp = cabeza;
            try {
                if(calcMillis(nodo).before(calcMillis(temp))){
                    nodo.setSiguiente(cabeza);
                    cabeza = nodo;
                } else if(calcMillis(nodo).compareTo(calcMillis(temp)) > 0){
                    if(calcMillis(nodo).compareTo(calcMillis(temp.getSiguiente())) > 0) {
                        temp = temp.getSiguiente();
                        addToCola(nodo);
                    } else {
                        nodo.setSiguiente(temp.getSiguiente());
                        temp.setSiguiente(nodo);
                    }
                } else {
                    if(temp == cola) {
                        cola = nodo;
                    } else {
                        nodo.setSiguiente(temp.getSiguiente());
                    }
                    temp.setSiguiente(nodo);
                    
                    
                }
                
            } catch (ParseException ex) {
                System.out.println("Ha ocurrido un error: ");
                ex.printStackTrace();
            }
            
            
            cola.setSiguiente(nodo);
            cola = nodo;
        }
    }
    
    public void addCola(Nodo nuevoNodo) {
        if (cabeza == null) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            try {
                Nodo actual = cabeza;
                Nodo anterior = null;
                Date nuevoTiempo = sdf.parse(nuevoNodo.getTrabajo().getTime());

                while (actual != null && sdf.parse(actual.getTrabajo().getTime()).compareTo(nuevoTiempo) >= 0) {
                    anterior = actual;
                    actual = actual.getSiguiente();
                }

                if (anterior == null) {
                    nuevoNodo.setSiguiente(cabeza);
                    cabeza = nuevoNodo;
                } else {
                    anterior.setSiguiente(nuevoNodo);
                    nuevoNodo.setSiguiente(actual);
                }

                if (actual == null) {
                    cola = nuevoNodo;
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void agregarTrabajo(Trabajo trabajo) {
        Nodo nuevoNodo = new Nodo(trabajo);
        
        if (cabeza == null) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            cola.setSiguiente(nuevoNodo);
            cola = nuevoNodo;
        }
    }
    
    public int getSize() {
        int size = 0;
        Nodo actual = cabeza;
        
        while (actual != null) {
            size++;
            actual = actual.getSiguiente();
        }
        
        return size;
    }
    
    public void mostrarCola() {
        if (cabeza != null) {
            mostrarRecursivo(cabeza);
        } else {
            System.out.println("La cola está vacía.");
        }
    }
    
    private void mostrarRecursivo(Nodo nodo) {
        if (nodo != null) {
            System.out.println(nodo.getTrabajo().getId() + " " + nodo.getTrabajo().getNombreCliente());
            mostrarRecursivo(nodo.getSiguiente());
        }
    }
    
    public Trabajo buscarPorIndice(int indice) {
        int contador = 0;
        Nodo actual = cabeza;
        
        while (actual != null) {
            if (contador == indice) {
                return actual.getTrabajo();
            }
            
            actual = actual.getSiguiente();
            contador++;
        }
        
        return null; // Si no se encuentra el elemento en el índice especificado
    }
    
    public void addNormal(Nodo nodo) {
        if(vacio()) {
            cabeza = nodo;
            cola = nodo;
            
        } else {
            cola.setSiguiente(nodo);
            cola = nodo;
        }
    }
    
    public Trabajo getColaByIndex(int index){
        Trabajo work = new Trabajo();
        Nodo temp = cabeza;
        for(int i = 0; i <= index; i++){
            
            if(i == index) {
                work = temp.getTrabajo();
            }
            temp = temp.getSiguiente();
        }
        return work;
    }
    
    public Trabajo obtenerValorPorIndice(int indice) {
        if (vacio()) {
            throw new NoSuchElementException("La cola está vacía");
        }

        if (indice < 0) {
            throw new IndexOutOfBoundsException("El índice es negativo");
        }

        Nodo actual = cabeza;
        for (int i = 0; i < indice; i++) {
            if (actual == null) {
                throw new IndexOutOfBoundsException("El índice está fuera de rango");
            }
            actual = actual.getSiguiente();
        }

        if (actual == null) {
            throw new IndexOutOfBoundsException("El índice está fuera de rango");
        }

        return actual.getTrabajo();
    }
    
    public boolean vacio(){
        return cabeza == null;
    }
            
    public void mostrarCla() {
        Nodo actual = cabeza;
        while (actual != null) {
            System.out.println(actual.getTrabajo().getNombreCliente() + " - Tiempo: " + actual.getTrabajo().getTime());
            actual = actual.getSiguiente();
        }
    }

    
    private Date parseDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ssss");
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro. Vaor null");
            return null;
        }
    }
    
    public Nodo getCabeza() {
        return cabeza;
    }
    
    public Nodo getCola() {
        return cola;
    }
    
    public boolean colaVacia(){
        return cabeza != null;
    }
    
    
    public Date calcMillis(Nodo nodo) throws ParseException {
        return sdf.parse(nodo.getTrabajo().getTime());
    }
    
}
