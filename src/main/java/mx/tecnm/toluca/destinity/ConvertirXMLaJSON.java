/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.tecnm.toluca.destinity;

/**
 *
 * @author bet10
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import org.json.XML;


public class ConvertirXMLaJSON {

    public static void main(String[] args) throws Exception {
        //URL url = new URL("http://localhost:8080/DESTINITY/resources/mx.tecnm.toluca.destinity.clientes");
        URL url = new URL("http://localhost:8080/CRM_2/resources/mx.tecnm.toluca.crm_2.tarjeta");
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("GET");

        int codigoEstado = conexion.getResponseCode();

        if (codigoEstado == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            String inputLine;
            StringBuilder contenido = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                contenido.append(inputLine);
            }
            in.close();

            // Convertir el contenido XML a JSON
            String xmlString = contenido.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlString);

            // Imprimir el JSON resultante
            System.out.println(jsonObject.toString(4)); // Imprimir con indentación de 4 espacios
        } else {
            System.out.println("Error al conectar con el servidor");
        }
    }
}

