package fr.istic.taa.jaxrs.rest;

import dao.UtilisateurDao;
import domain.Utilisateur;
import fr.istic.taa.jaxrs.domain.Pet;
import io.swagger.v3.oas.annotations.Parameter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/user")
@Produces({"application/json", "application/xml"})
public class UserResource {


    @GET
    @Path("/{userId}")
    public Utilisateur getUserById(@PathParam("userId") Long userId)  {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("dev");
        EntityManager manager = factory.createEntityManager();
        UtilisateurDao utilisateurDao = new UtilisateurDao(manager);
        return utilisateurDao.searchUserById(userId);
    }


    @POST
    @Consumes("application/json")
    public Response addUser(
            @Parameter(description = "User that needs to be added", required = true) Utilisateur user) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("dev");
        EntityManager manager = factory.createEntityManager();
        UtilisateurDao utilisateurDao = new UtilisateurDao(manager);
        utilisateurDao.addUser(user);
        return Response.ok().entity(user).build();
    }

}
