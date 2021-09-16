package rest;

import dao.RdvDao;
import domain.Rdv;
import io.swagger.v3.oas.annotations.Parameter;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/rdv")
@Produces({"application/json", "application/xml"})
public class RdvResource {

    @GET
    @Path("/{rdvId}")
    public Rdv getRdvById(@PathParam("rdvId") Long rdvId)  {
        RdvDao rdvDao = new RdvDao();
        return rdvDao.searchRdvById(rdvId);
    }

    @POST
    @Consumes("application/json")
    public Response addRdv(
            @Parameter(description = "Rdv object that needs to be added to the store", required = true) Rdv rdv) {
        RdvDao rdvDao = new RdvDao();
        rdvDao.addRdv(rdv);
        return Response.ok().entity(rdv).build();
    }

    @DELETE
    @Path("/{rdvId}")
    public Response deleteRdvById(@PathParam("rdvId") Long rdvId)  {
        RdvDao rdvDao = new RdvDao();
        rdvDao.deleteRdvById(rdvId);
        return Response.ok().build();
    }


}
