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
public class ServiciosXML implements Serializable {

    private List<Servicios> serviciosHTTP;
    private Servicios servicio;

    public List<Servicios> getServiciosHTTP() {
        return serviciosHTTP;
    }

    public Servicios getServicio() {
        return servicio;
    }

    public void setServiciosHTTP(List<Servicios> serviciosHTTP) {
        this.serviciosHTTP = serviciosHTTP;
    }

    public void setServicio(Servicios servicio) {
        this.servicio = servicio;
    }

    public List<Servicios> listaServicios(int idAlojamiento) {
        serviciosHTTP = new ArrayList<>();
        try {
            // Obtener los XML desde la URL especificada
            //url de Gil y Luis
            //String xmlContent = sendGetRequest("https://7b31-187-190-159-12.ngrok-free.app/Proveedores1_Alojamientos/resources/Servicios/ServiciosDisponibles/"+idAlojamiento);
            //url de LIMON
            String xmlContent = sendGetRequest("http://10.224.1.6:8080/VuelosHSBC/resources/VentasDestinity/serviciosHabitacionesS/HappyPlace/"+idAlojamiento);
            // Parsear y mostrar el contenido del XML
            parseAndDisplayXML(xmlContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviciosHTTP;
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

        NodeList nodeList = document.getElementsByTagName("servicioAdicional");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Servicios servicio = new Servicios();
                System.out.println("metodo para imprimir servicios");
                // Obtener datos de la habitacion
                servicio.setCosto(Double.parseDouble(element.getElementsByTagName("costo").item(0).getTextContent()));
                servicio.setDescripcion(element.getElementsByTagName("descripcion").item(0).getTextContent());
                servicio.setIdAlojamiento(Integer.parseInt(element.getElementsByTagName("idAlojamiento").item(0).getTextContent()));
                servicio.setIdServicio(Integer.parseInt(element.getElementsByTagName("idServicio").item(0).getTextContent()));
                servicio.setNombreAlojamiento(element.getElementsByTagName("nombreAlojamiento").item(0).getTextContent());
                servicio.setProveedor(element.getElementsByTagName("proveedor").item(0).getTextContent());
                serviciosHTTP.add(servicio);
            }
        }
    }
    

}
