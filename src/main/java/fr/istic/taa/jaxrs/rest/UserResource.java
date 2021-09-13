package fr.istic.taa.jaxrs.rest;

import domain.Utilisateur;
import fr.istic.taa.jaxrs.domain.Pet;
import io.swagger.v3.oas.annotations.Parameter;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/user")
@Produces({"application/json", "application/xml"})
public class UserResource {


    @GET
    @Path("/{userId}")
    public Utilisateur getUserById(@PathParam("userId") Long userId)  {
        //faire appel à la dao
        // return user
        return new Utilisateur("gghggg");
    }

    @POST
    @Consumes("application/json")
    public Response addUser(
            @Parameter(description = "User object that needs to be added to the store", required = true) Utilisateur user) {
        // add pet
        return Response.ok().entity("SUCCESS").build();
    }

}
