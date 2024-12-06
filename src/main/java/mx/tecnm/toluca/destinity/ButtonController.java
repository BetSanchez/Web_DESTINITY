/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.tecnm.toluca.destinity;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import service.ClientesFacadeREST;

/**
 *
 * @author bet10
 */
@SessionScoped
@Named

public class ButtonController implements Serializable {
        

    @Inject
    ClientesFacadeREST cf;
    @Inject
    Clientes c;

    private boolean isLoggedIn;

    public String getNumCuenta() {
        return c.getNumCuenta();
    }

    public boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    private String repeatedPassword; // new property to hold the repeated password 

    public ClientesFacadeREST getCf() {
        return cf;
    }

    public Clientes getC() {
        return c;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setCf(ClientesFacadeREST cf) {
        this.cf = cf;
    }

    public void setC(Clientes c) {
        this.c = c;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public String insertar() {

        if (!c.getPassword().equals(repeatedPassword)) {
            FacesContext.getCurrentInstance().addMessage("confirmPassword", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Las contraseñas no coinciden, intentelo nuevamente", "Por favor, verifique que las contraseñas sean iguales"));
            return "";
        } else {
            try {
                c.setEstatus("1");
                System.out.println("Datos insertados");
                System.out.println("Id " + c.getIdCliente());
                System.out.println("Nombre: " + c.getNombre());
                System.out.println("A paterno: " + c.getAPaterno());
                System.out.println("A materno: " + c.getAMaterno());
                System.out.println("correo: " + c.getCorreo());
                System.out.println("password: " + c.getPassword());
                System.out.println("telefono: " + c.getTelefono());
                System.out.println("direccion: " + c.getDireccion());
                System.out.println("num cuenta:" + c.getNumCuenta());
                System.out.println("estatus: " + c.getEstatus());
                cf.create(c);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Registro exitoso", "El usuario ha sido registrado correctamente. Por favor, inicie sesión para acceder al sistema."));
                return "Principal_1.xhtml";
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Error el correo ya existe", "Ocurrió un error al registrar el usuario."));
            }
            return "";
        }
    }

    public void limpiarCampos1() {
        c.setNombre("");
        c.setAPaterno("");
        c.setAMaterno("");
        c.setDireccion("");
        c.setTelefono("");
        c.setCorreo("");
        c.setPassword("");
        repeatedPassword = "";
    }

    public void limpiarCampos2() {
        c.setDireccion("");
        c.setTelefono("");
        c.setPassword("");
        repeatedPassword = "";
    }

    public String validarLogin() {
        try {
            Clientes cliente = cf.findByCorreoAndPassword(c.getCorreo(), c.getPassword());
            if (cliente != null) {
                isLoggedIn = true;
                return "Principal_1.xhtml";
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Correo electrónico o contraseña incorrectos. Intentelo nuevamente.", "ERROR - login"));
                return "";
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Correo electrónico o contraseña incorrectos", "Ocurrió un error al validar el usuario."));
            return "";
        }
    }

    public void mostrarValorDeC2() {
        try {
            Clientes cliente = cf.findByCorreo(c.getCorreo()); //buscando el correo en la base
            if (cliente != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Valor de c: " + cliente.getIdCliente()));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No se encontró el cliente con el correo electrónico " + c.getCorreo()));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error al buscar el cliente: " + e.getMessage()));
        }
    }
//
//    public void mostrarNumCuenta() {
//        try {
//            String numCuenta = cf.findByCorreoNumCuenta(c.getCorreo());
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Número de cuenta: " + numCuenta));
//        } catch (Exception e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error al buscar el número de cuenta: " + e.getMessage()));
//        }
//    }
    public Integer mostrarValorDeC() {
        try {
            Clientes cliente = cf.findByCorreo(c.getCorreo()); //buscando el correo en la base
            if (cliente != null) {
                return cliente.getIdCliente();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No se encontró el cliente con el correo electrónico " + c.getCorreo()));
                return null;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error al buscar el cliente: " + e.getMessage()));
            return null;
        }
    }

    public String mostrarNumCuenta() {
        try {
            return cf.findByCorreoNumCuenta(c.getCorreo());
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error al buscar el número de cuenta: " + e.getMessage()));
            return null;
        }
    }

    public String actualizarDatos() {

        if (!c.getPassword().equals(repeatedPassword)) {
            FacesContext.getCurrentInstance().addMessage("confirmPassword", new FacesMessage(FacesMessage.SEVERITY_WARN, "Las contraseñas no coinciden, intentelo nuevamente", "Por favor, verifique que las contraseñas sean iguales"));
            return "";
        } else {

            try {
                Clientes cliente = cf.findByCorreo(c.getCorreo());
                if (cliente != null) {
                    cliente.setDireccion(c.getDireccion());
                    cliente.setTelefono(c.getTelefono());
                    cliente.setPassword(c.getPassword());
                    cf.edit(cliente);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos actualizados correctamente", "Los datos del usuario han sido actualizados correctamente."));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontró el usuario con el correo electrónico proporcionado."));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error al actualizar los datos del usuario."));
            }
            limpiarCampos2();
            return "Principal_1.xhtml";
        }
    }

    public String logout() {
        isLoggedIn = false;
        limpiarCampos1();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "Principal_1.xhtml"; // redirect to login page
    }
    
    

}
