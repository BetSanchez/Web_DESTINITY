package Vuelos;

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

@Named
@SessionScoped
public class AsientoXML implements Serializable {

    private List<Asiento> asientosHTTP;
    private Asiento asiento;

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public List<Asiento> getAsientosHTTP() {
        return asientosHTTP;
    }

    public void setAsientosHTTP(List<Asiento> asientosHTTP) {
        this.asientosHTTP = asientosHTTP;
    }

    public List<Asiento> listaAsientos(int id_Vuelo, String proveedor) {
        System.out.println("Proveeddoooor: "+proveedor);
        asientosHTTP = new ArrayList<>();
        try {
            // Obtener los XML desde la URL especificada
            //direccion DARWIN
            //String xmlContent = sendGetRequest("https://021d-2806-2f0-9020-f1e5-889a-5e3c-1f0b-7ad2.ngrok-free.app/RavenAirlines/resources/Asientos/AsientosDisponibles/"+id_Vuelo);
            //direccion de LIMON 
            String xmlContent = sendGetRequest("http://10.224.1.6:8080/VuelosHSBC/resources/VentasDestinity/serviciosAsientos/"+proveedor+"/"+id_Vuelo);
            // Parsear y mostrar el contenido del XML
            parseAndDisplayXML(xmlContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return asientosHTTP;
    }

    public static String sendGetRequest(String address) throws Exception {
        StringBuilder response = new StringBuilder();

        URL url = new URL(address);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

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

        NodeList nodeList = document.getElementsByTagName("serviciosAsientos");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Asiento asiento = new Asiento();

                // Obtener datos del asiento
                asiento.setClase(element.getElementsByTagName("clase").item(0).getTextContent());
                asiento.setIdAsiento(Integer.parseInt(element.getElementsByTagName("idAsiento").item(0).getTextContent()));
                asiento.setNumeroAsiento(Integer.parseInt(element.getElementsByTagName("numeroAsiento").item(0).getTextContent()));
                asiento.setProveedor(element.getElementsByTagName("proveedor").item(0).getTextContent());

                asientosHTTP.add(asiento);
            }
        }
    }
}
