package com.backend.service.interfaces;

import com.backend.model.Employee;
import jakarta.ws.rs.core.Response;

/**
 * Interface for RESTful operations related to employees.
 */
public interface IRESTEmployeesService {

    /**
     * Retrieves all employees.
     *
     * @return JSON response containing the list of employees
     */
    Response getEmployees();

    /**
     * Retrieves an employee with the specified ID.
     *
     * @param id ID of the employee to retrieve
     * @return JSON response containing the employee information
     */
    Response getEmployee(int id);

    /**
     * Inserts an employee into the system.
     *
     * @param employee Employee to be inserted
     * @return JSON response from the server indicating success or failure
     */
    Response insertEmployee(Employee employee);

    /**
     * Updates an employee with the given ID.
     *
     * @param id       ID of the employee to update
     * @param employee Updated employee object
     * @return JSON response from the server indicating success or failure
     */
    Response updateEmployee(int id, Employee employee);

    /**
     * Deletes an employee with the given ID.
     *
     * @param id ID of the employee to be deleted
     * @return JSON response from the server indicating success or failure
     */
    Response deleteEmployee(int id);
}