package com.backend.service.interfaces;

import com.backend.exception.RESTException;
import com.backend.model.Employee;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

/**
 * Interface for REST operations related to employees.
 */
public interface IRESTEmployeesService {

    /**
     * Retrieves all employees.
     *
     * @return JSON response containing the list of employees
     */
    Response getEmployees() throws RESTException;

    /**
     * Retrieves an employee with the specified ID.
     *
     * @param id ID of the employee to retrieve
     * @return JSON response containing the employee information
     */
    Response getEmployee(int id) throws RESTException;

    /**
     * Inserts an employee into the system.
     *
     * @param employee Employee to be inserted
     * @return JSON response from the server indicating success or failure
     */
    Response insertEmployee(Employee employee) throws RESTException;

    /**
     * Updates an employee with the given ID.
     *
     * @param id       ID of the employee to update
     * @param employee Updated employee object
     * @return JSON response from the server indicating success or failure
     */
    Response updateEmployee(int id, Employee employee) throws RESTException;

    /**
     * Deletes an employee with the given ID.
     *
     * @param id ID of the employee to be deleted
     * @return JSON response from the server indicating success or failure
     */
    Response deleteEmployee(int id) throws RESTException;
}