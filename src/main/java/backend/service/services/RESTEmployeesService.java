package backend.service.services;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Default;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import backend.consts.RESTMessages;
import backend.model.Employee;
import backend.model.RESTResponse;
import backend.service.interfaces.IRESTEmployeesService;
import backend.service.loader.Message;

import java.util.List;

@Default
@RequestScoped
public class RESTEmployeesService implements IRESTEmployeesService {

    private final Logger LOG = LogManager.getLogger(RESTEmployeesService.class);

    @Override
    public Response getEmployees() {

        RESTResponse response = new RESTResponse();

        List<Employee> employees = EmployeesService.getEmployees();

        if (employees == null) {
            LOG.debug("Employees not found.");
            response.setMessageText(Message.get(RESTMessages.UNSUCCESSFUL_SEARCH_EMPLOYEES));
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }

        for (Employee employee : employees) {

            response.addDataItem(employee);
        }

        LOG.debug("Found employees: {}", employees);
        response.setMessageText(Message.get(RESTMessages.SUCCESSFUL_SEARCH_EMPLOYEES));
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @Override
    public Response getEmployee(int id) {

        LOG.debug("Received id: {}", id);
        RESTResponse response = new RESTResponse();

        Employee employee = EmployeesService.getEmployee(id);

        if (employee == null) {
            LOG.debug("Employee not found.");
            response.setMessageText(Message.get(RESTMessages.UNSUCCESSFUL_DETAIL_EMPLOYEE));
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }

        LOG.debug("Found employee: {}", employee);
        response.addDataItem(employee);
        response.setMessageText(Message.get(RESTMessages.SUCCESSFUL_DETAIL_EMPLOYEE));

        return Response.status(Response.Status.OK).entity(response).build();
    }

    @Override
    public Response insertEmployee(Employee employee) {

        RESTResponse response = new RESTResponse();

        Integer id = EmployeesService.insertEmployee(employee);

        if (id == null) {
            LOG.debug("Employee not created.");
            response.setMessageText(Message.get(RESTMessages.UNSUCCESSFUL_CREATION_EMPLOYEE));
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }

        Employee newEmployee = EmployeesService.getEmployee(id);
        LOG.debug("Created new employee: {}", newEmployee);

        response.addDataItem(newEmployee);
        response.setMessageText(Message.get(RESTMessages.SUCCESSFUL_CREATION_EMPLOYEE));

        return Response.status(Response.Status.OK).entity(response).build();
    }

    @Override
    public Response updateEmployee(int id, Employee employee) {

        RESTResponse response = new RESTResponse();

        Integer rows = EmployeesService.updateEmployee(id, employee);

        if (rows == null) {
            LOG.debug("Employee not updated.");
            response.setMessageText(Message.get(RESTMessages.UNSUCCESSFUL_UPDATE_EMPLOYEE));
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }

        Employee updatedEmployee = EmployeesService.getEmployee(id);
        LOG.debug("Updated employee: {}", updatedEmployee);

        response.addDataItem(updatedEmployee);
        response.setMessageText(Message.get(RESTMessages.SUCCESSFUL_UPDATE_EMPLOYEE));

        return Response.status(Response.Status.OK).entity(response).build();
    }

    @Override
    public Response deleteEmployee(int id) {

        RESTResponse response = new RESTResponse();

        Integer rows = EmployeesService.deleteEmployee(id);

        if (rows == null) {
            LOG.debug("Employee not deleted.");
            response.setMessageText(Message.get(RESTMessages.UNSUCCESSFUL_DELETE));
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }

        LOG.debug("Employee deleted.");
        response.setMessageText(Message.get(RESTMessages.SUCCESSFUL_DELETE));

        return Response.status(Response.Status.OK).entity(response).build();
    }
}
