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
import java.io.File;
import java.io.FileReader;
import org.json.JSONArray;
import org.json.JSONObject;

public class LeerJSONLocal {

    public static void main(String[] args) {
        // Ruta del archivo JSON en la carpeta Descargas
        String rutaArchivo = "C:\\Users\\bet10\\Downloads\\productos.json";

        try {
            // Leer el archivo JSON
            BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo));
            StringBuilder contenidoJSON = new StringBuilder();
            String linea;

            while ((linea = lector.readLine()) != null) {
                contenidoJSON.append(linea);
            }
            lector.close();

            // Parsear el contenido JSON como un arreglo
            JSONArray jsonArray = new JSONArray(contenidoJSON.toString());

            // Recorrer cada objeto en el arreglo JSON
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject producto = jsonArray.getJSONObject(i);

                // Mostrar información de cada producto en consola
                System.out.println("Producto " + (i + 1) + ":");
                System.out.println("ID: " + producto.getString("id"));
                System.out.println("Nombre: " + producto.getString("nombre"));
                System.out.println("Descripción: " + producto.getString("descripcion"));
                System.out.println("Precio: $" + producto.getDouble("precio"));
                System.out.println("Dimensiones: " + producto.getString("dimensiones"));
                System.out.println("Capacidad: " + producto.getDouble("capacidad") + " kg");
                System.out.println("Marca: " + producto.getString("marca"));
                System.out.println("------------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
        }
    }
}
