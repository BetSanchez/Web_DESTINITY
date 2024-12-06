/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.tecnm.toluca.destinity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LeerXML {

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:8080/CRM_2/resources/mx.tecnm.toluca.crm_2.clientes");
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("GET");

        int codigoEstado = conexion.getResponseCode();

        if (codigoEstado == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            String inputLine;
            StringBuffer contenido = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                contenido.append(inputLine);
            }
            in.close();

            Document documento = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new java.io.ByteArrayInputStream(contenido.toString().getBytes()));
            NodeList listaClientes = documento.getElementsByTagName("clientes");

            for (int i = 0; i < listaClientes.getLength(); i++) {
                Node nodo = listaClientes.item(i);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;

                    System.out.println("ID: " + elemento.getElementsByTagName("id").item(0).getTextContent());
                    System.out.println("Nombre: " + elemento.getElementsByTagName("nombre").item(0).getTextContent());
                    System.out.println("Apellido Materno: " + elemento.getElementsByTagName("AMaterno").item(0).getTextContent());
                    System.out.println("Apellido Paterno: " + elemento.getElementsByTagName("APaterno").item(0).getTextContent());
                    System.out.println("Correo: " + elemento.getElementsByTagName("correo").item(0).getTextContent());
                    System.out.println("Password: " + elemento.getElementsByTagName("password").item(0).getTextContent());
                    System.out.println("Telefono: " + elemento.getElementsByTagName("telefono").item(0).getTextContent());
                    System.out.println("Direccion: " + elemento.getElementsByTagName("direccion").item(0).getTextContent());
                    System.out.println("Estatus: " + elemento.getElementsByTagName("estatus").item(0).getTextContent());
                    System.out.println("------------------------------------------------");
                }
            }
        } else {
            System.out.println("Error al conectar con el servidor");
        }
    }
}
