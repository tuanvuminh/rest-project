package backend.service.services;

import backend.db.DatabaseConnection;
import backend.model.Employee;
import backend.consts.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeesService {

    private static final Connection connection = DatabaseConnection.getConnection();

    public static List<Employee> getEmployees() {

        String sql = "SELECT * FROM employees";

        try {
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

    public static Employee getEmployee(int id) {

        String sql = "SELECT * FROM employees WHERE id = ?";

        try {
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

    public static Integer insertEmployee(Employee employee) {

        String sql = "INSERT INTO employees (first_name, last_name, email, department, salary) VALUES (?, ?, ?, ?, ?)";

        try {
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

    public static Integer updateEmployee(int id, Employee employee) {

        String sqlSelect = "SELECT id FROM employees WHERE id = ?";
        String sqlUpdate = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, department = ?, salary = ? WHERE id = ?";

        try {
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

    public static Integer deleteEmployee(int id) {

        String sqlSelect = "SELECT id FROM employees WHERE id = ?";
        String sqlDelete = "DELETE FROM employees WHERE id = ?";

        try {
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
