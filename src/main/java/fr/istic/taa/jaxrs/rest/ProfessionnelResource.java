package fr.istic.taa.jaxrs.rest;


import dao.ProfessionnelDao;
import dao.UtilisateurDao;
import domain.Professionnel;
import domain.Utilisateur;
import fr.istic.taa.jaxrs.dao.generic.EntityManagerHelper;
import io.swagger.v3.oas.annotations.Parameter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/prof")
@Produces({"application/json", "application/xml"})
public class ProfessionnelResource {

    @GET
    @Path("/{profId}")
    public Professionnel getProfById(@PathParam("profId") Long profId)  {
        //faire appel à la dao
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("dev");
        EntityManager manager = factory.createEntityManager();
        ProfessionnelDao professionnelDao = new ProfessionnelDao(manager);

        //return new Professionnel("gghggg");
        return professionnelDao.professionnelsParId(profId);
    }

    @POST
    @Consumes("application/json")
    public Response addProfessionnel(
            @Parameter(description = "Professionnel object that needs to be added to the store", required = true) Professionnel prof) {
        // add pet
        return Response.ok().entity("SUCCESS").build();
    }

}
