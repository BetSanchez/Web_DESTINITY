package Vuelos;

import jakarta.enterprise.context.RequestScoped;
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
@RequestScoped
public class AsientoXML1 implements Serializable {

    private List<Asiento1> asientosHTTP;
    private Asiento1 asiento;

    public Asiento1 getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento1 asiento) {
        this.asiento = asiento;
    }

    public List<Asiento1> getAsientosHTTP() {
        return asientosHTTP;
    }

    public void setAsientosHTTP(List<Asiento1> asientosHTTP) {
        this.asientosHTTP = asientosHTTP;
    }

    public List<Asiento1> listaAsientos(int id_Vuelo) {
        asientosHTTP = new ArrayList<>();
        try {
            // Obtener los XML desde la URL especificada
            String xmlContent = sendGetRequest("https://188c-2806-2f0-9020-f1e5-dfb-b698-bad6-bd43.ngrok-free.app/RavenAirlines/resources/raven.ravenairlines.asientos/AsientosDisponibles/"+id_Vuelo);
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
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
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
                Asiento1 asiento = new Asiento1();

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
