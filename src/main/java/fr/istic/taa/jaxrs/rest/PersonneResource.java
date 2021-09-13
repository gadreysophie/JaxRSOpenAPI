package fr.istic.taa.jaxrs.rest;

import domain.Personne;
import domain.Utilisateur;
import io.swagger.v3.oas.annotations.Parameter;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/personne")
@Produces({"application/json", "application/xml"})
public class PersonneResource {

    @GET
    @Path("/{personneId}")
    public Personne getPersonneById(@PathParam("personneId") Long personneId)  {
        // return user
        return new Personne() {
            @Override
            public Long getId() {
                return super.getId();
            }
        };
    }

    @POST
    @Consumes("application/json")
    public Response addPersonne(
            @Parameter(description = "Personne object that needs to be added to the store", required = true) Personne personne) {
        // add pet
        return Response.ok().entity("SUCCESS").build();
    }


}
