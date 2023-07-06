/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.sql.Statement;
import javax.swing.JOptionPane;
import modelos.Usuario;

/**
 *
 * @author oscardavidpadillarivas
 */
public class DatabaseManager {
        
    public DatabaseManager() {
        
    }
    
    //VALIDACION DE CONEXION
    public boolean conexion(){
        
        boolean con = false;
        String ip = "8.8.8.8"; // Dirección IP conocida, en este caso, la dirección IP de Google DNS

        try {
            int timeoutMs = 5000; // Tiempo máximo de espera en milisegundos

            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, 53), timeoutMs);

            System.out.println("¡Conexión a Internet exitosa!");
            con = true;
        } catch (IOException e) {
            System.out.println("No se pudo establecer una conexión a Internet: " + e.getMessage());
            con = false;
        }
        
        return con;
    }
    
    //VALIDACION INCOMPLETA
    public void consulta(){
        HttpURLConnection con;
        Statement statement = null;

        try {

            String apiurl = "http://161.35.5.30/uber_proyect/apis/api.php";

            String jsonData = "{\"apicall\":\"getUsers\", \"dni\":\"1073968863\", \"pass\":\"oscar123\"}";

            URL url = new URL(apiurl);

            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");

            con.setRequestProperty("Content-Type", "application/json");

            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            os.write(jsonData.getBytes("UTF-8"));
            os.close();

            int responseCode = con.getResponseCode();

            System.out.println("RESPUESTA DEL SERVER: " + responseCode);


            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;

            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            System.out.println("RESPUESTA DEL SERVER: " + response.toString());

            con.disconnect();

        } catch(Exception e) {
            System.out.println("Ha ocurrido un error: " + e);
            e.printStackTrace();
        }
    }
    
    //INSERTA EN BASE DE DATOS
    public void insertUserDB(String name, String pass, String edad, String dni, String tel, String tipoU){
        
        HttpURLConnection con;
        Statement statement = null;

        try {

            String apiurl = "http://161.35.5.30/uber_proyect/apis/api.php";

            String jsonData = "{\"apicall\":\"insertUser\", \"nombre\":\"" + name + "\", \"dni\":\"" + dni + "\", \"pass\":\"" + pass + "\", \"edad\":\"" + edad + "\", \"telefono\":\"" + tel + "\", \"tipo_u\":\"" + tipoU + "\"}";

            URL url = new URL(apiurl);

            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");

            con.setRequestProperty("Content-Type", "application/json");

            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            os.write(jsonData.getBytes("UTF-8"));
            os.close();

            int responseCode = con.getResponseCode();

            System.out.println("RESPUESTA DEL SERVER: " + responseCode);


            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;

            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            System.out.println("RESPUESTA DEL SERVER: " + response.toString());

            con.disconnect();

        } catch(Exception e) {
            System.out.println("Ha ocurrido un error: " + e);
            e.printStackTrace();
        }
        
        
    }

    //JOPTION MESSAGE
    private void aviso(String aviso) { JOptionPane.showMessageDialog(null, aviso);}
    
    //OBTIENE DATOS DE USUARIO APARTIR DE DNI
    public Usuario get(String dni, String pass){
        HttpURLConnection con;
        Statement statement = null;
        
        Usuario user = new Usuario();

        try {

            String apiurl = "http://161.35.5.30/uber_proyect/apis/api.php";

            String jsonData = "{\"apicall\":\"getUsers\", \"dni\":\"" + dni + "\", \"pass\":\"" + pass + "\"}";

            URL url = new URL(apiurl);

            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");

            con.setRequestProperty("Content-Type", "application/json");

            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            os.write(jsonData.getBytes("UTF-8"));
            os.close();

            int responseCode = con.getResponseCode();

            System.out.println("RESPUESTA DEL SERVER: " + responseCode);


            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;

            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            Gson json = new Gson();
            
            String respuesta = "";
            JsonElement responseJson = json.fromJson(response.toString(), JsonElement.class);
            
            if(responseJson.isJsonObject()){
                JsonObject jsonObject = responseJson.getAsJsonObject();
                
                for(String campo: jsonObject.keySet()){
                    JsonElement valor = jsonObject.get("name");
                    respuesta = (valor != null && !valor.isJsonNull()) ? valor.getAsString() : "";
                    System.out.println("RESPUESTA: " + valor);
                }
            }
            
            if(!respuesta.equalsIgnoreCase("Inexistente")) {
                user = json.fromJson(response.toString(), Usuario.class);
                
            } else {
                aviso("El usuario no existe");
            }

            System.out.println("RESPUESTA DEL SERVER: " + response.toString());
            

            con.disconnect();

        } catch(Exception e) {
            System.out.println("Ha ocurrido un error: " + e);
            e.printStackTrace();
        }
        
        return user;
    }
    
    //WORKS
    public String getWorks(){
        HttpURLConnection con;
        Statement statement = null;
        String respuesta = "";

        try {

            String apiurl = "http://161.35.5.30/uber_proyect/apis/api.php";

            String jsonData = "{\"apicall\":\"getAllWorks\"}";

            URL url = new URL(apiurl);

            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");

            con.setRequestProperty("Content-Type", "application/json");

            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            os.write(jsonData.getBytes("UTF-8"));
            os.close();

            int responseCode = con.getResponseCode();

            System.out.println("RESPUESTA DEL SERVER: " + responseCode);


            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;

            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            respuesta = response.toString();
            

            System.out.println("RESPUESTA DEL SERVER: " + response.toString());

            con.disconnect();

        } catch(Exception e) {
            System.out.println("Ha ocurrido un error: " + e);
            e.printStackTrace();
        }
        return (respuesta);
    }

    public String insertWork(String nombre_cliente, String dni_cliente, String ubicacion_actual, String ubicacion_direccion, String dineroO, String time, String estado) {
        HttpURLConnection con;
        Statement statement = null;
        String respuesta = "";

        try {

            String apiurl = "http://161.35.5.30/uber_proyect/apis/api.php";

            String jsonData = "{\"apicall\":\"insertWork\", \"nombre_cliente\":\"" + nombre_cliente + "\", \"dni_cliente\":\"" + dni_cliente + "\", \"ubicacion_actual\":\"" + ubicacion_actual + "\", \"ubicacion_direccion\":\"" + ubicacion_direccion + "\", \"dinero_ofrecido\":\"" + dineroO + "\", \"time\":\"" + time + "\", \"estado\":\"" + estado + "\"}";

            URL url = new URL(apiurl);

            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");

            con.setRequestProperty("Content-Type", "application/json");

            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            os.write(jsonData.getBytes("UTF-8"));
            os.close();

            int responseCode = con.getResponseCode();

            System.out.println("RESPUESTA DEL SERVER: " + responseCode);


            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;

            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            respuesta = response.toString();
            

            System.out.println("RESPUESTA DEL SERVER: " + response.toString());

            con.disconnect();

        } catch(Exception e) {
            System.out.println("Ha ocurrido un error: " + e);
            e.printStackTrace();
        }
        return respuesta;
    }

    public boolean ifExiste(String dni) {
        return get(dni, "").getTipoUser() != null;
    }
    
    //ACTUALIZAR WORK
    public String updateStateWork(String id, String value) {
        HttpURLConnection con;
        Statement statement = null;
        String respuesta = "";

        try {

            String apiurl = "http://161.35.5.30/uber_proyect/apis/api.php";

            String jsonData = "{\"apicall\":\"updateStateWork\", \"id\":\"" + id + "\", \"value\":\"" + value + "\"}";

            URL url = new URL(apiurl);

            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");

            con.setRequestProperty("Content-Type", "application/json");

            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            os.write(jsonData.getBytes("UTF-8"));
            os.close();

            int responseCode = con.getResponseCode();

            System.out.println("RESPUESTA DEL SERVER: " + responseCode);


            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;

            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            respuesta = response.toString();
            

            System.out.println("RESPUESTA DEL SERVER: " + response.toString());

            con.disconnect();

        } catch(Exception e) {
            System.out.println("Ha ocurrido un error: " + e);
            e.printStackTrace();
        }
        return respuesta;
    }
}