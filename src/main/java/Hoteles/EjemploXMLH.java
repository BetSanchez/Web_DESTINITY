/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hoteles;

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
import java.util.Date;
import java.util.List;

/**
 *
 * @author bet10
 */
@Named
@SessionScoped
public class EjemploXMLH implements Serializable {
    
    private String fechaEntradaStr;
    private String fechaSalidaStr;

    // Getters y setters para fechaEntradaStr y fechaSalidaStr
    public String getFechaEntradaStr() {
        return fechaEntradaStr;
    }

    public void setFechaEntradaStr(String fechaEntradaStr) {
        this.fechaEntradaStr = fechaEntradaStr;
    }

    public String getFechaSalidaStr() {
        return fechaSalidaStr;
    }

    public void setFechaSalidaStr(String fechaSalidaStr) {
        this.fechaSalidaStr = fechaSalidaStr;
    }
   

    private List<ServiciosHoteles> hotelHTTP;
    private ServiciosHoteles hotel;

    private List<Habitacion> detallesHabitacion;
    private List<Integer> boletosOferta, boletosSelec;

    private List<Servicios> detallesServicios;
    private List<Integer> boletosOferta2, boletosSelec2;

    private double precioActual; // Nuevo campo para almacenar el precio actual
    private double impuestoActual; // Nuevo campo para almacenar el impuesto actual
    private double servicio;  //Nuevo campo para el servicio 

    String proveedorSeleccionado;

    public String getProveedorSeleccionado() {
        return proveedorSeleccionado;
    }

    public void setProveedorSeleccionado(String proveedorSeleccionado) {
        this.proveedorSeleccionado = proveedorSeleccionado;
    }

    String comprarBoletos = "";
    String comprarBoletos2 = "";

    public double getPrecioActual() {
        return precioActual;
    }

    public double getImpuestoActual() {
        return impuestoActual;
    }

    public double getServicio() {
        return servicio;
    }

    public void setPrecioActual(double precioActual) {
        this.precioActual = precioActual;
    }

    public void setImpuestoActual(double impuestoActual) {
        this.impuestoActual = impuestoActual;
    }

    public void setServicio(double servicio) {
        this.servicio = servicio;
    }

    public List<ServiciosHoteles> getHotelHTTP() {
        return hotelHTTP;
    }

    public ServiciosHoteles getHotel() {
        return hotel;
    }

    public List<Habitacion> getDetallesHabitacion() {
        return detallesHabitacion;
    }

    public List<Integer> getBoletosOferta() {
        return boletosOferta;
    }

    public List<Integer> getBoletosSelec() {
        return boletosSelec;
    }

    public String getComprarBoletos() {
        return comprarBoletos;
    }

    public void setHotelHTTP(List<ServiciosHoteles> hotelHTTP) {
        this.hotelHTTP = hotelHTTP;
    }

    public void setHotel(ServiciosHoteles hotel) {
        this.hotel = hotel;
    }

    public void setDetallesHabitacion(List<Habitacion> detallesHabitacion) {
        this.detallesHabitacion = detallesHabitacion;
    }

    public void setBoletosOferta(List<Integer> boletosOferta) {
        this.boletosOferta = boletosOferta;
    }

    public void setBoletosSelec(List<Integer> boletosSelec) {
        this.boletosSelec = boletosSelec;
    }

    public void setComprarBoletos(String comprarBoletos) {
        this.comprarBoletos = comprarBoletos;
    }

    // para servicios
    public List<Servicios> getDetallesServicios() {
        return detallesServicios;
    }

    public List<Integer> getBoletosOferta2() {
        return boletosOferta2;
    }

    public List<Integer> getBoletosSelec2() {
        return boletosSelec2;
    }

    public String getComprarBoletos2() {
        return comprarBoletos2;
    }

    public void setDetallesServicios(List<Servicios> detallesServicios) {
        this.detallesServicios = detallesServicios;
    }

    public void setBoletosOferta2(List<Integer> boletosOferta2) {
        this.boletosOferta2 = boletosOferta2;
    }

    public void setBoletosSelec2(List<Integer> boletosSelec2) {
        this.boletosSelec2 = boletosSelec2;
    }

    public void setComprarBoletos2(String comprarBoletos2) {
        this.comprarBoletos2 = comprarBoletos2;
    }

    public List<ServiciosHoteles> listaHoteles() {
        hotel = new ServiciosHoteles();
        hotelHTTP = new ArrayList<>();
        try {
            // Obtener los XML desde la URL especificada de HOTELES LUIS PEDRO
            //url de GIL y LUIS
            //String xmlContent = sendGetRequest("https://7b31-187-190-159-12.ngrok-free.app/Proveedores1_Alojamientos/resources/Hoteles/HotelesDisponibles");
            // url de LIMON
            String xmlContent = sendGetRequest("http://10.224.1.6:8080/VuelosHSBC/resources/VentasDestinity/serviciosHospedajes");
            // Parsear y mostrar el contenido del XML
            parseAndDisplayXML(xmlContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hotelHTTP;
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

        NodeList nodeList = document.getElementsByTagName("filteredAlojamiento");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                ServiciosHoteles hotel = new ServiciosHoteles();
                // Obtener datos del vuelo
                hotel.setIdAlojamiento(Integer.parseInt(element.getElementsByTagName("idAlojamiento").item(0).getTextContent()));
                hotel.setNombre(element.getElementsByTagName("nombre").item(0).getTextContent());
                hotel.setEstadoNombre(element.getElementsByTagName("estadoNombre").item(0).getTextContent());
                hotel.setTelefono(element.getElementsByTagName("telefono").item(0).getTextContent());
                hotel.setProveedor(element.getElementsByTagName("proveedor").item(0).getTextContent());
                hotelHTTP.add(hotel);
            }
        }
    }

    public void verDetalles(int idAlojamiento) throws IOException {
        boletosSelec = new ArrayList();
        HabitacionXML habitacionesList = new HabitacionXML();
        detallesHabitacion = habitacionesList.listaHabitaciones(idAlojamiento);
        boletosSelec2 = new ArrayList();
        ServiciosXML serviciosList = new ServiciosXML();
        detallesServicios = serviciosList.listaServicios(idAlojamiento);
        FacesContext.getCurrentInstance().getExternalContext().redirect("detallesHabitaciones.xhtml");
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

    public void generarCompra(String valorDeC, String numCuenta, String fechaEntradaStr, String fechaSalidaStr) {
        
        
        String ventaERP = "";
        for (Integer idBoleto : boletosSelec) {
            ventaERP += "," + idBoleto;
        }
        ventaERP = ventaERP.substring(1, ventaERP.length());
        
        String ventaERP2 = "";
        for (Integer idBoleto2 : boletosSelec2) {
            ventaERP2 += "," + idBoleto2;
        }
        ventaERP2 = ventaERP2.substring(1, ventaERP2.length());

        double[] costoImpuesto = costoHabitaciones(ventaERP);
        double costoHabitacion = costoImpuesto[0];
        double impuestoHabitacion = costoImpuesto[1];

        System.out.println("Cliente: " + valorDeC);
        System.out.println("Cuenta: " + numCuenta);
        System.out.println("Habitaciones: " + ventaERP);
        System.out.println("Costo habitaciones: " + costoHabitacion);
        System.out.println("Impuesto habitaciones: " + impuestoHabitacion);
        System.out.println("Servicios: "+ventaERP2);
        System.out.println("Total servicios: "+ costoServicios(ventaERP2));
        double totalProveedores = costoHabitacion+costoServicios(ventaERP2);
        System.out.println("Total proveedores:"+totalProveedores);
        System.out.println("Fecha entrada: "+ fechaEntradaStr);
        System.out.println("Fecha Salida: "+ fechaSalidaStr);

        try {

            String respuesta = sendPostRequest("http://10.224.1.6:8080/VuelosHSBC/resources/VentasDestinity/generarVentaHospedajes/"
            +numCuenta+"/"+impuestoHabitacion+"/"+totalProveedores+"/"+ventaERP+"/"+ventaERP2+"/"+fechaEntradaStr+"/"+fechaSalidaStr+"/"+"HappyPlace");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // para SERVICIOS
    public void agregarBoleto2(int idAsiento2) {
        boletosSelec2.add(idAsiento2);
        for (Integer boletoId2 : boletosSelec2) {
        }
        comprarBoletos2 += "," + idAsiento2;
    }

    public void quitarBoleto2(int idAsiento2) {
        boletosSelec2.remove(Integer.valueOf(idAsiento2));
    }

    public boolean checarBoletoRes2(int idAsiento2) {
        for (Integer boletoId2 : boletosSelec2) {
            if (boletoId2 == idAsiento2) {
                return boletoId2 == idAsiento2;
            }
        }
        return false;
    }

    public boolean checarBoletoDis2(int idAsiento2) {
        for (Integer boletoId2 : boletosSelec2) {
            if (boletoId2 == idAsiento2) {
                return false;
            }
        }
        return true;
    }

    public double[] costoHabitaciones(String ventaERP) {
        double totalHabitaciones = 0;
        double ImpuestoHabitaciones = 0;
        String[] idHabitaciones = ventaERP.split(",");

        for (String id : idHabitaciones) {
            for (Habitacion habitacion : detallesHabitacion) {
                if (String.valueOf(habitacion.getIdHabitacion()).equals(id)) {
                    totalHabitaciones += habitacion.getCosto();
                    ImpuestoHabitaciones += habitacion.getCosto() * 0.40;
                }
            }
        }

        double[] resultado = new double[2];
        resultado[0] = totalHabitaciones;
        resultado[1] = ImpuestoHabitaciones;

        return resultado;
    }

    public double costoServicios(String ventaERP2) {
        double totalServicios = 0;
        String[] idServicios = ventaERP2.split(",");

        for (String id : idServicios) {
            for (Servicios servicio : detallesServicios) {
                if (String.valueOf(servicio.getIdServicio()).equals(id)) {
                    totalServicios += servicio.getCosto();
                }
            }
        }

        return totalServicios;
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
