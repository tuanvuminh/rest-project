package com.backend.service.services;

import com.backend.helper.adapter.EmployeesServiceAdapter;
import com.backend.model.Employee;
import com.backend.model.RESTResponse;
import com.backend.helper.loader.MessageLoaderV2;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.backend.service.interfaces.IRESTEmployeesService;

import java.util.List;

import static com.backend.consts.RESTMessages.*;

/**
 * Service implementation for managing employee data through REST operations.
 *
 * This service class provides methods to retrieve, insert, update, and delete employee information using REST web services.
 */
@Default
@RequestScoped
public class RESTEmployeesService implements IRESTEmployeesService {

    private static final Logger LOG = LogManager.getLogger(RESTEmployeesService.class);

    @Inject
    MessageLoaderV2 message;

    /**
     * @inheritDoc
     */
    @Override
    public Response getEmployees() {

        LOG.debug("Received getEmployees request.");
        RESTResponse response = new RESTResponse();

        List<Employee> employees = EmployeesServiceAdapter.getEmployees();

        if (employees == null) {
            LOG.debug("Employees not found.");
            response.setMessageText(message.get(UNSUCCESSFUL_SEARCH_EMPLOYEES));
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }

        for (Employee employee : employees) {
            response.addDataItem(employee);
        }
        LOG.debug("Found employees: {}", employees);

        response.setMessageText(message.get(SUCCESSFUL_SEARCH_EMPLOYEES));
        return Response.status(Response.Status.OK).entity(response).build();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Response getEmployee(int id) {

        LOG.debug("Received id: {}", id);
        RESTResponse response = new RESTResponse();

        Employee employee = EmployeesServiceAdapter.getEmployee(id);

        if (employee == null) {
            LOG.debug("Employee not found.");
            response.setMessageText(message.get(UNSUCCESSFUL_DETAIL_EMPLOYEE));
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
        LOG.debug("Found employee: {}", employee);

        response.addDataItem(employee);
        response.setMessageText(message.get(SUCCESSFUL_DETAIL_EMPLOYEE));
        return Response.status(Response.Status.OK).entity(response).build();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Response insertEmployee(Employee employee) {

        LOG.debug("Received insertEmployee request with body: {}", employee);
        RESTResponse response = new RESTResponse();

        Integer id = EmployeesServiceAdapter.insertEmployee(employee);

        if (id == null) {
            LOG.debug("Employee not created.");
            response.setMessageText(message.get(UNSUCCESSFUL_CREATION_EMPLOYEE));
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
        Employee newEmployee = EmployeesServiceAdapter.getEmployee(id);
        LOG.debug("Created new employee: {}", newEmployee);

        response.addDataItem(newEmployee);
        response.setMessageText(message.get(SUCCESSFUL_CREATION_EMPLOYEE));
        return Response.status(Response.Status.OK).entity(response).build();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Response updateEmployee(int id, Employee employee) {

        LOG.debug("Received updateEmployee request with id: {}.", id);
        RESTResponse response = new RESTResponse();

        Integer rows = EmployeesServiceAdapter.updateEmployee(id, employee);

        if (rows == null) {
            LOG.debug("Employee not updated.");
            response.setMessageText(message.get(UNSUCCESSFUL_UPDATE_EMPLOYEE));
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
        Employee updatedEmployee = EmployeesServiceAdapter.getEmployee(id);
        LOG.debug("Updated employee: {}", updatedEmployee);

        response.addDataItem(updatedEmployee);
        response.setMessageText(message.get(SUCCESSFUL_UPDATE_EMPLOYEE));
        return Response.status(Response.Status.OK).entity(response).build();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Response deleteEmployee(int id) {

        LOG.debug("Received deleteEmployee request with id: {}", id);
        RESTResponse response = new RESTResponse();

        Integer rows = EmployeesServiceAdapter.deleteEmployee(id);

        if (rows == null) {
            LOG.debug("Employee not deleted.");
            response.setMessageText(message.get(UNSUCCESSFUL_DELETE));
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
        LOG.debug("Employee deleted.");

        response.setMessageText(message.get(SUCCESSFUL_DELETE));
        return Response.status(Response.Status.OK).entity(response).build();
    }
}
