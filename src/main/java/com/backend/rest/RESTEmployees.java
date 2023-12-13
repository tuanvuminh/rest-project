package com.backend.rest;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.backend.model.Employee;
import com.backend.service.interfaces.IRESTEmployeesService;

@Named
@RequestScoped
@Path("v1/employees")
public class RESTEmployees {

    @Inject
    IRESTEmployeesService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployees() {
        return service.getEmployees();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(@PathParam("id") int id) {
        return service.getEmployee(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertEmployee(Employee employee) {
        return service.insertEmployee(employee);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("id") int id, Employee employee) {
        return service.updateEmployee(id, employee);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEmployee(@PathParam("id") int id) {
        return service.deleteEmployee(id);
    }
}
