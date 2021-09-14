package fr.istic.taa.jaxrs.rest;

import dao.DepartementDao;
import dao.ProfessionnelDao;
import domain.Departement;
import domain.Professionnel;
import io.swagger.v3.oas.annotations.Parameter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/dept")
@Produces({"application/json", "application/xml"})
public class DepartementResource {
    @GET
    @Path("/{deptId}")
    public Departement getDepartementById(@PathParam("deptId") Long deptId)  {
        //faire appel à la dao
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("dev");
        EntityManager manager = factory.createEntityManager();
        DepartementDao departementDao = new DepartementDao(manager);

        return departementDao.departementsParId(deptId);
    }

    @POST
    @Consumes("application/json")
    public Response addDepartement(
            @Parameter(description = "Departement object that needs to be added to the store", required = true) Departement dept) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("dev");
        EntityManager manager = factory.createEntityManager();
        DepartementDao departementDao = new DepartementDao(manager);
        departementDao.addDepartement(dept);
        return Response.ok().entity(dept).build();

    }

}
