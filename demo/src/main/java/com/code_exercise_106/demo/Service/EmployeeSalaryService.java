package com.code_exercise_106.demo.Service;

import com.code_exercise_106.demo.Model.Employee;

import java.util.Map;
import java.util.logging.Logger;

public class EmployeeSalaryService {

    private static final Logger log = Logger.getLogger(EmployeeSalaryService.class.getName());
    private Map<Long, Employee> employeeMap;

    public EmployeeSalaryService(Map<Long, Employee> employeeMap) {
        this.employeeMap = employeeMap;
    }

    // Salary validation
    public void validateSalaries() {
        for (Employee manager : employeeMap.values()) {
            if (manager.getSubordinates().isEmpty()) continue;

            double avg = manager.getSubordinates()
                    .stream()
                    .mapToDouble(Employee::getSalary)
                    .average()
                    .orElse(0);

            double min = avg * (20/100); // Min salary should be > 20% of their subordinates
            double max = avg * (50/100); // Max salary should be < 50% of their subordinates

            if (manager.getSalary() < min) {
//                System.out.println(manager.getFullName() +
//                        " earns LESS by " + (min - manager.getSalary()));
                log.info(manager.getFullName() + " earns LESS by " + (min - manager.getSalary()));
            } else if (manager.getSalary() > max) {
//                System.out.println(manager.getFullName() +
//                        " earns MORE by " + (manager.getSalary() - max));
                log.info(manager.getFullName() + " earns MORE by " + (manager.getSalary() - max));
            }
        }
    }
}
