package com.backend.service.services;

import com.backend.db.DBConnectionV2;
import com.backend.model.Employee;
import com.backend.consts.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class providing internal operations for managing employee data in the database.
 *
 * This class contains methods to interact with the database, including retrieving, inserting, updating, and deleting employee records.
 */
public class InternalEmployeesService {

    /**
     * Retrieves all employees from the database.
     *
     * @return List of Employee objects representing all employees in the database
     */
    public List<Employee> getEmployees() {

        String sql = "SELECT * FROM employees";

        try (Connection connection = DBConnectionV2.getConnection()) {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setFetchSize(1);

            ResultSet rs = ps.executeQuery();
            List<Employee> employees = new ArrayList<>();

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("department"),
                        rs.getInt("salary")
                );
                employees.add(employee);
            }

            return employees;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves an employee from the database based on the provided ID.
     *
     * @param id ID of the employee to retrieve
     * @return Employee object representing the employee with the specified ID, or null if not found
     */
    public Employee getEmployee(int id) {

        String sql = "SELECT * FROM employees WHERE id = ?";

        try (Connection connection = DBConnectionV2.getConnection()) {

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setFetchSize(1);
            ps.setInt(Constants.INDEX_ONE, id);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            Employee employee = new Employee(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("department"),
                    rs.getInt("salary")
            );

            return employee;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Inserts a new employee into the database.
     *
     * @param employee Employee object representing the employee to be inserted
     * @return The ID of the newly inserted employee, or null if insertion failed
     */
    public Integer insertEmployee(Employee employee) {

        String sql = "INSERT INTO employees (first_name, last_name, email, department, salary) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnectionV2.getConnection()) {

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setFetchSize(1);

            ps.setString(Constants.INDEX_ONE, employee.getFirstName());
            ps.setString(Constants.INDEX_TWO, employee.getLastName());
            ps.setString(Constants.INDEX_THREE, employee.getEmail());
            ps.setString(Constants.INDEX_FOUR, employee.getDepartment());
            ps.setInt(Constants.INDEX_FIVE, employee.getSalary());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(Constants.INDEX_ONE);
                }
            }

            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates an existing employee in the database based on the provided ID.
     *
     * @param id       ID of the employee to update
     * @param employee Employee object representing the updated employee information
     * @return The number of rows affected by the update, or null if update failed
     */
    public Integer updateEmployee(int id, Employee employee) {

        String sqlSelect = "SELECT id FROM employees WHERE id = ?";
        String sqlUpdate = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, department = ?, salary = ? WHERE id = ?";

        try (Connection connection = DBConnectionV2.getConnection()) {

            PreparedStatement select = connection.prepareStatement(sqlSelect);
            select.setFetchSize(1);
            select.setInt(Constants.INDEX_ONE, id);
            ResultSet resultSet = select.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            PreparedStatement update = connection.prepareStatement(sqlUpdate);
            update.setFetchSize(1);
            update.setString(Constants.INDEX_ONE, employee.getFirstName());
            update.setString(Constants.INDEX_TWO, employee.getLastName());
            update.setString(Constants.INDEX_THREE, employee.getEmail());
            update.setString(Constants.INDEX_FOUR, employee.getDepartment());
            update.setInt(Constants.INDEX_FIVE, employee.getSalary());

            return update.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deletes an employee from the database based on the provided ID.
     *
     * @param id ID of the employee to be deleted
     * @return The number of rows affected by the deletion, or null if deletion failed
     */
    public Integer deleteEmployee(int id) {

        String sqlSelect = "SELECT id FROM employees WHERE id = ?";
        String sqlDelete = "DELETE FROM employees WHERE id = ?";

        try (Connection connection = DBConnectionV2.getConnection()) {

            PreparedStatement select = connection.prepareStatement(sqlSelect);
            select.setFetchSize(1);
            select.setInt(Constants.INDEX_ONE, id);
            ResultSet resultSet = select.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            PreparedStatement delete = connection.prepareStatement(sqlDelete);
            delete.setFetchSize(1);
            delete.setInt(Constants.INDEX_ONE, id);

            return delete.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
