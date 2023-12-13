package com.backend.service.services;

import com.backend.model.Employee;

import java.util.List;

public class EmployeesService {

    private EmployeesService() {
        super();
    }

    private static InternalEmployeesService getService() {
        return new InternalEmployeesService();
    }

    public static List<Employee> getEmployees() {
        return getService().getEmployees();
    }

    public static Employee getEmployee(int id) {
        return getService().getEmployee(id);
    }

    public static Integer insertEmployee(Employee employee) {
        return getService().insertEmployee(employee);
    }

    public static Integer updateEmployee(int id, Employee employee) {
        return getService().updateEmployee(id, employee);
    }

    public static Integer deleteEmployee(int id) {
        return getService().deleteEmployee(id);
    }
}
