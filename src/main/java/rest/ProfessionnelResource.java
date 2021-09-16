package rest;

import dao.ProfessionnelDao;
import domain.Professionnel;
import io.swagger.v3.oas.annotations.Parameter;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/prof")
@Produces({"application/json", "application/xml"})
public class ProfessionnelResource {

    @GET
    @Path("/{profId}")
    public Professionnel getProfById(@PathParam("profId") Long profId)  {
        ProfessionnelDao professionnelDao = new ProfessionnelDao();
        return professionnelDao.searchProfessionnelById(profId);
    }

    @POST
    @Consumes("application/json")
    public Response addProfessionnel(
            @Parameter(description = "Professionnel object that needs to be added to the store", required = true) Professionnel prof) {
        ProfessionnelDao professionnelDao = new ProfessionnelDao();
        professionnelDao.addProf(prof);
        return Response.ok().entity(prof).build();

    }

    @DELETE
    @Path("/{profId}")
    public Response deleteProfById(@PathParam("profId") Long profId)  {
        ProfessionnelDao profDao = new ProfessionnelDao();
        profDao.deleteProfById(profId);
        return Response.ok().build();
    }

}
