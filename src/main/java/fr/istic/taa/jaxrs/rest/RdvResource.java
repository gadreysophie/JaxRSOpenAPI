package fr.istic.taa.jaxrs.rest;


import dao.ProfessionnelDao;
import dao.RdvDao;
import domain.Professionnel;
import domain.Rdv;
import io.swagger.v3.oas.annotations.Parameter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/rdv")
@Produces({"application/json", "application/xml"})
public class RdvResource {

    @GET
    @Path("/{rdvId}")
    public Rdv getRdvById(@PathParam("rdvId") Long rdvId)  {
        //faire appel à la dao
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("dev");
        EntityManager manager = factory.createEntityManager();
        RdvDao rdvDao = new RdvDao(manager);

        return rdvDao.rdvsParId(rdvId);
    }

    @POST
    @Consumes("application/json")
    public Response addRdv(
            @Parameter(description = "Rdv object that needs to be added to the store", required = true) Rdv rdv) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("dev");
        EntityManager manager = factory.createEntityManager();
        RdvDao rdvDao = new RdvDao(manager);
        rdvDao.addRdv(rdv);
        return Response.ok().entity(rdv).build();

    }

}
