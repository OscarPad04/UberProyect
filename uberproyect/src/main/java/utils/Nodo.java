/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import modelos.Trabajo;

/**
 *
 * @author oscardavidpadillarivas
 */
public class Nodo {
    
    private Trabajo trabajo;
    private Nodo siguiente;
    
    public Nodo(Trabajo trabajo) {
        this.trabajo = trabajo;
        siguiente = null;
    }
    
    public Nodo(){
        trabajo = null;
        siguiente = null;
    }
    
    public Trabajo getTrabajo(){
        return trabajo;
    }
    
    public void setTrabajo(Trabajo work){
        trabajo = work;
    }
    
    
    public void setSiguiente(Nodo sig){
        siguiente = sig;
    }
    
    public Nodo getSiguiente(){
        return siguiente;
    }
    
}
