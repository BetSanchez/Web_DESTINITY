package Vuelos;

//import com.mycompany.probarvuelos.ProveedorVuelosAsientos.Asiento;
//import com.mycompany.probarvuelos.ProveedorVuelosAsientos.AsientoXML;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
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
import java.util.ArrayList;
import java.util.List;
//import static mx.tecnm.toluca.destinity.ProveedorVuelosDarwin.VuelosRevenXML.sendGetRequest;

@Named
@SessionScoped
public class EjemploXML1 implements Serializable {

    private List<ServiciosVuelos1> vuelosHTTP;
    private ServiciosVuelos1 vuelo;
    
    private List<Asiento1> detallesAsientos;
    private List<Integer> boletosOferta, boletosSelec;
    
    String comprarBoletos="";

    public List<Integer> getBoletosOferta() {
        return boletosOferta;
    }

    public void setBoletosOferta(ArrayList<Integer> boletosOferta) {
        this.boletosOferta = boletosOferta;
    }

    public List<Integer> getBoletosSelec() {
        return boletosSelec;
    }

    public void setBoletosSelec(ArrayList<Integer> boletosSelec) {
        this.boletosSelec = boletosSelec;
    }

    public String getComprarBoletos() {
        return comprarBoletos;
    }

    public void setComprarBoletos(String comprarBoletos) {
        this.comprarBoletos = comprarBoletos;
    }

    public List<Asiento1> getDetallesAsientos() {
        return detallesAsientos;
    }

    public void setDetallesAsientos(List<Asiento1> detalleAsiento) {
        this.detallesAsientos = detalleAsiento;
    }

    public ServiciosVuelos1 getVuelo() {
        return vuelo;
    }

    public void setVuelo(ServiciosVuelos1 vuelo) {
        this.vuelo = vuelo;
    }

    public List<ServiciosVuelos1> getVuelosHTTP() {
        return vuelosHTTP;
    }

    public void setVuelosHTTP(List<ServiciosVuelos1> vuelosHTTP) {
        this.vuelosHTTP = vuelosHTTP;
    }

    public List<ServiciosVuelos1> listaVuelos() {
        vuelo = new ServiciosVuelos1();
        vuelosHTTP = new ArrayList<>();
        try {
            // Obtener los XML desde la URL especificada
            String xmlContent = sendGetRequest("https://188c-2806-2f0-9020-f1e5-dfb-b698-bad6-bd43.ngrok-free.app/RavenAirlines/resources/raven.ravenairlines.vuelos/vuelos");
            // Parsear y mostrar el contenido del XML
            parseAndDisplayXML(xmlContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vuelosHTTP;
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

        NodeList nodeList = document.getElementsByTagName("serviciosVuelos");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                ServiciosVuelos1 vuelo = new ServiciosVuelos1();

                // Obtener datos del vuelo
                vuelo.setOrigenCiudad(element.getElementsByTagName("ciudadOrigen").item(0).getTextContent());
                vuelo.setDestinoCiudad(element.getElementsByTagName("ciudadDestino").item(0).getTextContent());
                vuelo.setDuracion(element.getElementsByTagName("duracion").item(0).getTextContent());
                vuelo.setFechaSalida(element.getElementsByTagName("fechaSalida").item(0).getTextContent());
                vuelo.setIdAvion(Integer.parseInt(element.getElementsByTagName("idVuelo").item(0).getTextContent()));
                vuelo.setPrecio(Double.parseDouble(element.getElementsByTagName("precio").item(0).getTextContent()));
                vuelo.setProveedor(element.getElementsByTagName("proveedor").item(0).getTextContent());

                vuelosHTTP.add(vuelo);
            }
        }
    }
    
    public void verDetalles(int idAvion) throws IOException {
        boletosSelec = new ArrayList();
        AsientoXML1 asientosList = new AsientoXML1();
        detallesAsientos = asientosList.listaAsientos(idAvion);
        FacesContext.getCurrentInstance().getExternalContext().redirect("detallesAsientos2.xhtml");        
    }
    
    
    public void agregarBoleto(int idAsiento){
        boletosSelec.add(idAsiento);
        System.out.println("LISTA DE BOLETOS SELECCIONADOS");
        System.out.println("- - - - - - - - - - - - - - -");
        for(Integer boletoId : boletosSelec){
            System.out.println(boletoId);
        }
        System.out.println("- - - - - - - - - - - - - - -");
        comprarBoletos += ","+idAsiento;
        System.out.println("Actualizando lista");
        System.out.println(comprarBoletos);
    }
     public void quitarBoleto(int idAsiento){
        boletosSelec.remove(Integer.valueOf(idAsiento));
    }
    public boolean checarBoletoRes(int idAsiento){
        for(Integer boletoId : boletosSelec){
            if(boletoId==idAsiento){
                return boletoId==idAsiento;
            }
        }
        return false;
    }
    public boolean checarBoletoDis(int idAsiento){
        for(Integer boletoId : boletosSelec){
            if(boletoId==idAsiento){
                return false;
            }
        }
        return true;
    }
    public void generarCompra(){
        String ventaERP="";
        for(Integer idBoleto : boletosSelec){
            ventaERP += ","+idBoleto;            
        }
        ventaERP = ventaERP.substring(1, ventaERP.length());
        System.out.println("Cadena para POST hacia ERP:\n"+ventaERP);
        
    }
}

