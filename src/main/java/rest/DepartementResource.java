package rest;

import dao.DepartementDao;
import domain.Departement;
import io.swagger.v3.oas.annotations.Parameter;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/dept")
@Produces({"application/json", "application/xml"})
public class DepartementResource {

    @GET
    @Path("/{deptId}")
    public Departement getDepartementById(@PathParam("deptId") Long deptId)  {
        DepartementDao departementDao = new DepartementDao();
        return departementDao.searchDepartementById(deptId);
    }

    @POST
    @Consumes("application/json")
    public Response addDepartement(
            @Parameter(description = "Departement object that needs to be added to the store", required = true) Departement dept) {
        DepartementDao departementDao = new DepartementDao();
        departementDao.addDepartement(dept);
        return Response.ok().entity(dept).build();

    }

    @DELETE
    @Path("/{deptId}")
    public Response deleteDepartById(@PathParam("deptId") Long deptId)  {
        DepartementDao depDao = new DepartementDao();
        depDao.deleteDepartById(deptId);
        return Response.ok().build();
    }
}
