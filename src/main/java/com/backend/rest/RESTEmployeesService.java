package com.backend.rest;

import com.backend.model.Employee;
import com.backend.model.RESTResponse;
import com.backend.helper.MessageLoader;
import com.backend.service.services.EmployeesService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.backend.consts.RESTMessages;
import com.backend.service.interfaces.IRESTEmployeesService;

import java.util.List;

@Default
@RequestScoped
public class RESTEmployeesService implements IRESTEmployeesService {

    private static final Logger LOG = LogManager.getLogger(RESTEmployeesService.class);

    @Inject
    MessageLoader message;

    @Override
    public Response getEmployees() {

        LOG.debug("Received getEmployees request.");
        RESTResponse response = new RESTResponse();

        List<Employee> employees = EmployeesService.getEmployees();

        if (employees == null) {
            LOG.debug("Employees not found.");
            response.setMessageText(message.get(RESTMessages.UNSUCCESSFUL_SEARCH_EMPLOYEES));
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }

        for (Employee employee : employees) {
            response.addDataItem(employee);
        }
        LOG.debug("Found employees: {}", employees);

        response.setMessageText(message.get(RESTMessages.SUCCESSFUL_SEARCH_EMPLOYEES));
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @Override
    public Response getEmployee(int id) {

        LOG.debug("Received id: {}", id);
        RESTResponse response = new RESTResponse();

        Employee employee = EmployeesService.getEmployee(id);

        if (employee == null) {
            LOG.debug("Employee not found.");
            response.setMessageText(message.get(RESTMessages.UNSUCCESSFUL_DETAIL_EMPLOYEE));
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
        LOG.debug("Found employee: {}", employee);

        response.addDataItem(employee);
        response.setMessageText(message.get(RESTMessages.SUCCESSFUL_DETAIL_EMPLOYEE));
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @Override
    public Response insertEmployee(Employee employee) {

        LOG.debug("Received insertEmployee request with body: {}", employee);
        RESTResponse response = new RESTResponse();

        Integer id = EmployeesService.insertEmployee(employee);

        if (id == null) {
            LOG.debug("Employee not created.");
            response.setMessageText(message.get(RESTMessages.UNSUCCESSFUL_CREATION_EMPLOYEE));
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
        Employee newEmployee = EmployeesService.getEmployee(id);
        LOG.debug("Created new employee: {}", newEmployee);

        response.addDataItem(newEmployee);
        response.setMessageText(message.get(RESTMessages.SUCCESSFUL_CREATION_EMPLOYEE));
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @Override
    public Response updateEmployee(int id, Employee employee) {

        LOG.debug("Received updateEmployee request with id: {}.", id);
        RESTResponse response = new RESTResponse();

        Integer rows = EmployeesService.updateEmployee(id, employee);

        if (rows == null) {
            LOG.debug("Employee not updated.");
            response.setMessageText(message.get(RESTMessages.UNSUCCESSFUL_UPDATE_EMPLOYEE));
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
        Employee updatedEmployee = EmployeesService.getEmployee(id);
        LOG.debug("Updated employee: {}", updatedEmployee);

        response.addDataItem(updatedEmployee);
        response.setMessageText(message.get(RESTMessages.SUCCESSFUL_UPDATE_EMPLOYEE));
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @Override
    public Response deleteEmployee(int id) {

        LOG.debug("Received deleteEmployee request with id: {}", id);
        RESTResponse response = new RESTResponse();

        Integer rows = EmployeesService.deleteEmployee(id);

        if (rows == null) {
            LOG.debug("Employee not deleted.");
            response.setMessageText(message.get(RESTMessages.UNSUCCESSFUL_DELETE));
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
        LOG.debug("Employee deleted.");

        response.setMessageText(message.get(RESTMessages.SUCCESSFUL_DELETE));
        return Response.status(Response.Status.OK).entity(response).build();
    }
}
