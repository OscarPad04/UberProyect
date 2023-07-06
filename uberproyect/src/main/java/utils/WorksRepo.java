/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.List;
import javax.swing.JOptionPane;
import modelos.Trabajo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 *
 * @author oscardavidpadillarivas
 */
public class WorksRepo {
    
    private String tag = "Class WorkRepo";
    private Cola cola;
    private DatabaseManager dbNube;
    private FileWorkManager dbLocal;
    
    
    public WorksRepo() {
        dbNube = new DatabaseManager();
        dbLocal  = new FileWorkManager();
        cola = new Cola();
    
    }
    
    public void init(){
        if(dbNube.conexion()) {
            
            String response = dbNube.getWorks();
            System.out.println(dbNube.getWorks());
            
            try {
                dbLocal.limpiarArchivo();
                addWorks(response);
            } catch (Exception e){
                e.printStackTrace();
            }
            
            
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Trabajo>>() {}.getType();
            List<Trabajo> listaWorks = gson.fromJson(response, listType);
            
            
        } else {
            JOptionPane.showMessageDialog(null, "No tiene conexion a internet");
        }
    }
    
    public boolean conexion() {
        return dbNube.conexion();
    }
    
    public boolean insertDb(Trabajo work){
        
        boolean saved = false;
        
        String response = dbNube.insertWork(work.getNombreCliente(), work.getDniCliente(), work.getUbicacionActual(), work.getDireccion(), work.getDineroOfrecido(), work.getTime(), work.getEstado());
        
        JsonObject jsonObject = parseJson(response);
        
        String respuesta = getJsonBykey(jsonObject, "mensaje");
        
        saved = respuesta.equalsIgnoreCase("Guardado");
        
        return saved;
    }

    public void addWorks(String reponse) throws IOException{
        JsonArray jsonArray = parseJsonArray(reponse);
        Trabajo work = new Trabajo();
        Nodo temp = new Nodo();
        
        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            // Ejecutar el método deseado para obtener información específica del JSON
            // Por ejemplo, obtener el valor de una propiedad llamada "nombre"
            
            String id = getJsonBykey(jsonObject, "id");
            String dni = getJsonBykey(jsonObject, "dni_cliente");
            String nombre = getJsonBykey(jsonObject, "nombre_cliente");
            String ubicacion_actual = getJsonBykey(jsonObject, "ubicacion_actual");
            String direccion = getJsonBykey(jsonObject, "ubicacion_direccion");
            String dineroO = getJsonBykey(jsonObject, "dinero_ofrecido");
            String time = getJsonBykey(jsonObject, "time");
            String estado = getJsonBykey(jsonObject, "estado");
            
            work.setId(id);
            work.setDniCliente(dni);
            work.setNombreCliente(nombre);
            work.setUbicacionActual(ubicacion_actual);
            work.setDireccion(direccion);
            work.setDineroOfrecido(dineroO);
            work.setTime(time);
            work.setEstado(estado);
            temp.setTrabajo(work);
            
            //cola.addCola(temp);
            //cola.mostrarCola();
            dbLocal.agregarTrabajo(work);
            
        }
        
        
    }
    
    private JsonObject parseJson(String response){
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(response);
        if (jsonElement.isJsonObject()) {
            return jsonElement.getAsJsonObject();
        } else {
            throw new IllegalArgumentException("La cadena de texto no representa un objeto JSON válido");
        }
    }
    
    private JsonArray parseJsonArray(String jsonResponse) {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(jsonResponse);
        return jsonElement.getAsJsonArray();
    }
    
    private String getJsonBykey(JsonObject jsonObject, String key) {
        return jsonObject.get(key).getAsString();
    }

    public boolean updateWork(String id, String dni_conductor) {
        boolean update = false;
        String value = "Procesado";
        String response = dbNube.updateStateWork(id, value);
        
        JsonObject jsonObject = parseJson(response);
        
        String respuesta = getJsonBykey(jsonObject, "mensaje");
        
        update = respuesta.equalsIgnoreCase("Actualizado");
        
        return update;
    }
}
