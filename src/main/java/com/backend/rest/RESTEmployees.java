package com.backend.rest;

import com.backend.security.ClientCertificateVerifier;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.backend.model.Employee;
import com.backend.service.interfaces.IRESTEmployeesService;

/**
 * REST interface for managing employee data.
 * <p>
 * This class provides endpoints for retrieving, inserting, updating, and deleting employee information.
 */
@Named
@RequestScoped
@Path("v1/employees")
public class RESTEmployees {

    /**
     * HttpServletRequest for accessing client's request details.
     */
    @Context
    HttpServletRequest request;

    /**
     * Service for REST Employees interface.
     */
    @Inject
    IRESTEmployeesService service;

    /**
     * ClientCertificateVerifier for verifying client certificates.
     */
    @Inject
    ClientCertificateVerifier verifier;

    /**
     * Method maps the GET method. Retrieves all employees.
     *
     * @return JSON response containing the list of employees
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployees() {
        return service.getEmployees();
    }

    /**
     * Method maps the GET method. Retrieves an employee with the specified ID.
     *
     * @param id ID of the employee to retrieve
     * @return JSON response containing the employee information
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(@PathParam("id") int id) {
        return service.getEmployee(id);
    }

    /**
     * Method maps the POST method. Inserts an employee into the system.
     *
     * @param employee Employee to be inserted
     * @return JSON response from the server indicating success or failure
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertEmployee(Employee employee) {
        return service.insertEmployee(employee);
    }

    /**
     * Method maps the PUT method. Updates an employee with the given ID.
     *
     * @param id       ID of the employee to update
     * @param employee Updated employee object
     * @return JSON response from the server indicating success or failure
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("id") int id, Employee employee) {
        return service.updateEmployee(id, employee);
    }

    /**
     * Method maps the DELETE method. Deletes an employee with the given ID.
     *
     * @param id ID of the employee to be deleted
     * @return JSON response from the server indicating success or failure
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEmployee(@PathParam("id") int id) {
        return service.deleteEmployee(id);
    }
}
