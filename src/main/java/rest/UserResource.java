package rest;

import dao.UtilisateurDao;
import domain.Utilisateur;
import io.swagger.v3.oas.annotations.Parameter;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/user")
@Produces({"application/json", "application/xml"})
public class UserResource {

    @GET
    @Path("/{userId}")
    public Utilisateur getUserById(@PathParam("userId") Long userId)  {
        UtilisateurDao utilisateurDao = new UtilisateurDao();
        return utilisateurDao.searchUserById(userId);
    }

    @POST
    @Consumes("application/json")
    public Response addUser(
            @Parameter(description = "User that needs to be added", required = true) Utilisateur user) {
        UtilisateurDao utilisateurDao = new UtilisateurDao();
        utilisateurDao.addUser(user);
        return Response.ok().entity(user).build();
    }

    @DELETE
    @Path("/{userId}")
    public Response deleteUserById(@PathParam("userId") Long userId)  {
        UtilisateurDao userDao = new UtilisateurDao();
        userDao.deleteUserById(userId);
        return Response.ok().build();
    }

}
