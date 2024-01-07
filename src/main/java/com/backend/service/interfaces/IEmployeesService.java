package com.backend.service.interfaces;

import com.backend.exception.RESTException;
import com.backend.model.Employee;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for internal operations for managing employee data in the database.
 */
public interface IEmployeesService {

    /**
     * Retrieves all employees from the database.
     *
     * @return List of Employee objects representing all employees in the database
     */
    List<Employee> getEmployees() throws SQLException, RESTException;

    /**
     * Retrieves an employee from the database based on the provided ID.
     *
     * @param id ID of the employee to retrieve
     * @return Employee object representing the employee with the specified ID, or null if not found
     */
    Employee getEmployee(int id) throws SQLException, RESTException;

    /**
     * Inserts a new employee into the database.
     *
     * @param employee Employee object representing the employee to be inserted
     * @return The ID of the newly inserted employee, or null if insertion failed
     */
    Integer insertEmployee(Employee employee) throws SQLException, RESTException;

    /**
     * Updates an existing employee in the database based on the provided ID.
     *
     * @param id       ID of the employee to update
     * @param employee Employee object representing the updated employee information
     * @return The number of rows affected by the update, or null if update failed
     */
    Integer updateEmployee(int id, Employee employee) throws SQLException, RESTException;

    /**
     * Deletes an employee from the database based on the provided ID.
     *
     * @param id ID of the employee to be deleted
     * @return The number of rows affected by the deletion, or null if deletion failed
     */
    Integer deleteEmployee(int id) throws SQLException, RESTException;
}
