package Vuelos;

//import com.mycompany.probarvuelos.ProveedorVuelosAsientos.Asiento;
//import com.mycompany.probarvuelos.ProveedorVuelosAsientos.AsientoXML;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class EjemploXML implements Serializable {

    private List<ServiciosVuelos> vuelosHTTP;
    private ServiciosVuelos vuelo;

    private List<Asiento> detallesAsientos;
    private List<Integer> boletosOferta, boletosSelec;

    private double precioActual; // Nuevo campo para almacenar el precio actual
    private double impuestoActual; // Nuevo campo para almacenar el impuesto actual

    String proveedorSeleccionado;

    public String getProveedoSeleccionado() {
        return proveedorSeleccionado;
    }

    public void setProveedoSeleccionado(String proveedoSeleccionado) {
        this.proveedorSeleccionado = proveedoSeleccionado;
    }

    String comprarBoletos = "";

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

    public List<Asiento> getDetallesAsientos() {
        return detallesAsientos;
    }

    public void setDetallesAsientos(List<Asiento> detalleAsiento) {
        this.detallesAsientos = detalleAsiento;
    }

    public ServiciosVuelos getVuelo() {
        return vuelo;
    }

    public void setVuelo(ServiciosVuelos vuelo) {
        this.vuelo = vuelo;
    }

    public List<ServiciosVuelos> getVuelosHTTP() {
        return vuelosHTTP;
    }

    public void setVuelosHTTP(List<ServiciosVuelos> vuelosHTTP) {
        this.vuelosHTTP = vuelosHTTP;
    }

    public List<ServiciosVuelos> listaVuelos() {
        vuelo = new ServiciosVuelos();
        vuelosHTTP = new ArrayList<>();
        try {
            // Obtener los XML desde la URL especificada
            //direccion DARWIN
            //String xmlContent = sendGetRequest("https://021d-2806-2f0-9020-f1e5-889a-5e3c-1f0b-7ad2.ngrok-free.app/RavenAirlines/resources/Vuelos/VuelosDisponibles");
            //direccion ISAAC
            //String xmlContent = sendGetRequest("Proveedores4/resources/mxtecnm.toluca.proveedores4.entities.vuelo/exportar");
            //direccion LIMON
            String xmlContent = sendGetRequest("http://10.224.1.6:8080/VuelosHSBC/resources/VentasDestinity/serviciosVuelos");
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

        NodeList nodeList = document.getElementsByTagName("serviciosVuelos");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                ServiciosVuelos vuelo = new ServiciosVuelos();

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

    public void verDetalles(int idAvion, double precio, double impuesto, String proveedor) throws IOException {
        proveedorSeleccionado = proveedor;
        boletosSelec = new ArrayList();
        System.out.println("Proveedor: "+proveedor);
        AsientoXML asientosList = new AsientoXML();
        detallesAsientos = asientosList.listaAsientos(idAvion, proveedor);
        precioActual = precio; // Asignar el precio a la variable de instancia
        impuestoActual = impuesto; // Asignar el impuesto a la variable de instancia
        FacesContext.getCurrentInstance().getExternalContext().redirect("detallesAsientos.xhtml");
    }

    public void agregarBoleto(int idAsiento) {
        boletosSelec.add(idAsiento);
        for (Integer boletoId : boletosSelec) {
            System.out.println(boletoId);
        }
        comprarBoletos += "," + idAsiento;
        System.out.println(comprarBoletos);
    }

    public void quitarBoleto(int idAsiento) {
        boletosSelec.remove(Integer.valueOf(idAsiento));
    }

    public boolean checarBoletoRes(int idAsiento) {
        for (Integer boletoId : boletosSelec) {
            if (boletoId == idAsiento) {
                return boletoId == idAsiento;
            }
        }
        return false;
    }

    public boolean checarBoletoDis(int idAsiento) {
        for (Integer boletoId : boletosSelec) {
            if (boletoId == idAsiento) {
                return false;
            }
        }
        return true;
    }

    public void generarCompra(int idCliente, String numCuenta) {
        String ventaERP = "";
        for (Integer idBoleto : boletosSelec) {
            ventaERP += "," + idBoleto;
        }

        ventaERP = ventaERP.substring(1, ventaERP.length());

        // Calcular el total y el impuesto e imprimirlo
        int totalBoletos = boletosSelec.size();
        double precioProveedores = totalBoletos * precioActual; //precioProveedores
        double precioVenta = totalBoletos * impuestoActual; //precioVenta
        System.out.println("----------VENTA A ERP --------------------------");
        System.out.println("Boletos: "+ventaERP);
        System.out.println("ID Cliente: "+idCliente);
        System.out.println("Cuenta: "+numCuenta);
        System.out.println("Precio Proveddores: "+precioProveedores);
        System.out.println("Precio Venta: "+precioVenta);
        System.out.println("Proveedor:"+proveedorSeleccionado);

        //idCliente+"/"+numCuenta+"/"+ventaERP+"/"+totalPrecio+"/"+totalImpuesto
        try {

            String respuesta = sendPostRequest("http://10.224.1.6:8080/VuelosHSBC/resources/VentasDestinity/generarVentaVuelos/" + numCuenta + "/" + precioVenta + "/" + precioProveedores + "/" + ventaERP + "/" + proveedorSeleccionado);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String sendPostRequest(String address) throws Exception {
        StringBuilder response = new StringBuilder();

        URL url = new URL(address);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } else {
            System.out.println("POST request not worked");
        }

        return response.toString();
    }

}
