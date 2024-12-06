/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import mx.tecnm.toluca.destinity.Clientes;

/**
 *
 * @author bet10
 */
@Stateless
@Path("mx.tecnm.toluca.destinity.clientes")
public class ClientesFacadeREST extends AbstractFacade<Clientes> {

    @PersistenceContext(unitName = "destinityPU")
    private EntityManager em;

    public ClientesFacadeREST() {
        super(Clientes.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Clientes entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Clientes entity) {
        super.edit(entity);
    }
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Clientes find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
        
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Clientes> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Clientes> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    // metodos para manipulacion de base de datos
    
     public Clientes findByCorreoAndPassword(String correo, String password) {
        Query query = em.createQuery("SELECT c FROM Clientes c WHERE c.correo = :correo AND c.password = :password");
        query.setParameter("correo", correo);
        query.setParameter("password", password);
        return (Clientes) query.getSingleResult();
    }

    public Clientes findByCorreo(String correo) {
        return em.createQuery("SELECT c FROM Clientes c WHERE c.correo = :correo", Clientes.class)
                .setParameter("correo", correo)
                .getSingleResult();
    }
    
    
    public String findByCorreoNumCuenta(String correo) {
    Query query = em.createQuery("SELECT c.numCuenta FROM Clientes c WHERE c.correo = :correo");
    query.setParameter("correo", correo);
    return (String) query.getSingleResult();
}
    

    
}
