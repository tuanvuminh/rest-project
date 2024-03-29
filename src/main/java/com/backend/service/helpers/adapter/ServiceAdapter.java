package com.backend.service.helpers.adapter;

import com.backend.exception.RESTException;
import com.backend.model.Employee;
import com.backend.service.services.EmployeesService;

import java.util.List;

/**
 * Adapter class providing simplified access to employee-related operations.
 *
 * This class adapts the interface of EmployeesService to provide a simplified and consistent API for
 * interacting with employee-related operations. Clients can use the methods in this class without directly
 * interacting with the internal details of EmployeesService.
 */
public class ServiceAdapter {

    private ServiceAdapter() {
        super();
    }

    /**
     * Returns a new instance of the EmployeesService class.
     */
    private static EmployeesService getEmployeeService() {
        return new EmployeesService();
    }

    public static List<Employee> getEmployees() throws RESTException {
        return getEmployeeService().getEmployees();
    }

    public static Employee getEmployee(int id) throws RESTException {
        return getEmployeeService().getEmployee(id);
    }

    public static Integer insertEmployee(Employee employee) throws RESTException {
        return getEmployeeService().insertEmployee(employee);
    }

    public static Integer updateEmployee(int id, Employee employee) throws RESTException {
        return getEmployeeService().updateEmployee(id, employee);
    }

    public static Integer deleteEmployee(int id) throws RESTException {
        return getEmployeeService().deleteEmployee(id);
    }
}
