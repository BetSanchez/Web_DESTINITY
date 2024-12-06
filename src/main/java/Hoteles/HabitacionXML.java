/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hoteles;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author bet10
 */
@Named
@SessionScoped
public class HabitacionXML implements Serializable {
    
    private List<Habitacion> habitacionesHTTP;
    private Habitacion habitacion;

    public List<Habitacion> getHabitacionHTTP() {
        return habitacionesHTTP;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacionHTTP(List<Habitacion> habitacionHTTP) {
        this.habitacionesHTTP = habitacionHTTP;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }
    
    public List<Habitacion> listaHabitaciones(int idAlojamiento) {
        habitacionesHTTP = new ArrayList<>();
        try {
            // Obtener los XML desde la URL especificada
            // url de Gil y Luis
            //String xmlContent = sendGetRequest("https://7b31-187-190-159-12.ngrok-free.app/Proveedores1_Alojamientos/resources/Habitaciones/HabitacionesDisponibles/"+idAlojamiento);
            //url de Limon
            String xmlContent = sendGetRequest("http://10.224.1.6:8080/VuelosHSBC/resources/VentasDestinity/serviciosHabitaciones/HappyPlace/"+idAlojamiento);
            // Parsear y mostrar el contenido del XML
            parseAndDisplayXML(xmlContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return habitacionesHTTP;
    }
    
    public static String sendGetRequest(String address) throws Exception {
        StringBuilder response = new StringBuilder();

        URL url = new URL(address);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("GET Response Codeeeee :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            //BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } else {
            System.out.println("GET request not worked");
        }

        return response.toString();
    }
    
    public void parseAndDisplayXML(String xmlContent) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new InputSource(new StringReader(xmlContent)));
        document.getDocumentElement().normalize();

        NodeList nodeList = document.getElementsByTagName("habitaciones");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Habitacion habitacion = new Habitacion();
                System.out.println("metodo para imprimir habitaciones");
                // Obtener datos de la habitacion
                habitacion.setIdHabitacion(Integer.parseInt(element.getElementsByTagName("idHabitacion").item(0).getTextContent()));
                habitacion.setIdentificador(element.getElementsByTagName("identificador").item(0).getTextContent());
                habitacion.setCapacidad(Integer.parseInt(element.getElementsByTagName("capacidad").item(0).getTextContent()));
                habitacion.setCosto(Double.parseDouble(element.getElementsByTagName("costo").item(0).getTextContent()));
                habitacion.setIdAlojamiento(Integer.parseInt(element.getElementsByTagName("idAlojamiento").item(0).getTextContent()));
                habitacion.setProveedor(element.getElementsByTagName("proveedor").item(0).getTextContent());
                habitacionesHTTP.add(habitacion);
            }
        }
    }
  
    
}
