package com.backend.service.services;

import com.backend.db.DBConnectionV2;
import com.backend.exception.RESTException;
import com.backend.model.Employee;
import com.backend.service.interfaces.IEmployeesService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.backend.consts.Constants.*;

/**
 * Service class providing internal operations for managing employee data in the database.
 *
 * This class contains methods to interact with the database, including retrieving, inserting, updating, and deleting employee records.
 */
public class EmployeesService implements IEmployeesService {

    /**
     * @inheritDoc
     */
    @Override
    public List<Employee> getEmployees() throws RESTException {

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
            throw new RESTException("Failed to retrieve employees. " + e.getLocalizedMessage());
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public Employee getEmployee(int id) throws RESTException {

        String sql = "SELECT * FROM employees WHERE id = ?";

        try (Connection connection = DBConnectionV2.getConnection()) {

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setFetchSize(1);
            ps.setInt(INDEX_ONE, id);

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
            throw new RESTException("Failed to retrieve employee. " + e.getLocalizedMessage());
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public Integer insertEmployee(Employee employee) throws RESTException {

        String sql = "INSERT INTO employees (first_name, last_name, email, department, salary) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnectionV2.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setFetchSize(1);
            ps.setString(INDEX_ONE, employee.getFirstName());
            ps.setString(INDEX_TWO, employee.getLastName());
            ps.setString(INDEX_THREE, employee.getEmail());
            ps.setString(INDEX_FOUR, employee.getDepartment());
            ps.setInt(INDEX_FIVE, employee.getSalary());

            int rows = ps.executeUpdate();

            if (rows > 0) {

                ResultSet generatedKeys = ps.getGeneratedKeys();

                if (generatedKeys.next()) {
                    return generatedKeys.getInt(INDEX_ONE);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RESTException("Failed to insert employee or retrieve generated key. " + e.getLocalizedMessage());
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public Integer updateEmployee(int id, Employee employee) throws RESTException {

        String sqlSelect = "SELECT id FROM employees WHERE id = ?";
        String sqlUpdate = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, department = ?, salary = ? WHERE id = ?";

        try (Connection connection = DBConnectionV2.getConnection();
             PreparedStatement select = connection.prepareStatement(sqlSelect);
             PreparedStatement update = connection.prepareStatement(sqlUpdate)) {

            select.setFetchSize(1);
            select.setInt(INDEX_ONE, id);
            ResultSet resultSet = select.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            update.setFetchSize(1);
            update.setString(INDEX_ONE, employee.getFirstName());
            update.setString(INDEX_TWO, employee.getLastName());
            update.setString(INDEX_THREE, employee.getEmail());
            update.setString(INDEX_FOUR, employee.getDepartment());
            update.setInt(INDEX_FIVE, employee.getSalary());
            update.setInt(INDEX_SIX, id);

            return update.executeUpdate();

        } catch (SQLException e) {
            throw new RESTException("Failed to update employee. " + e.getLocalizedMessage());
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public Integer deleteEmployee(int id) throws RESTException {

        String sqlSelect = "SELECT id FROM employees WHERE id = ?";
        String sqlDelete = "DELETE FROM employees WHERE id = ?";

        try (Connection connection = DBConnectionV2.getConnection();
             PreparedStatement select = connection.prepareStatement(sqlSelect);
             PreparedStatement delete = connection.prepareStatement(sqlDelete)) {

            select.setFetchSize(1);
            select.setInt(INDEX_ONE, id);
            ResultSet resultSet = select.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            delete.setFetchSize(1);
            delete.setInt(INDEX_ONE, id);

            return delete.executeUpdate();

        } catch (SQLException e) {
            throw new RESTException("Failed to delete employee. " + e.getLocalizedMessage());
        }
    }
}
