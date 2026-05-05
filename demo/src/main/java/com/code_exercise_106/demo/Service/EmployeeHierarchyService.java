package com.code_exercise_106.demo.Service;

import com.code_exercise_106.demo.Model.Employee;

import java.util.Map;
import java.util.logging.Logger;

public class EmployeeHierarchyService {

    private static final Logger log = Logger.getLogger(EmployeeHierarchyService.class.getName());
    private Map<Long, Employee> employeeMap;

    public EmployeeHierarchyService(Map<Long, Employee> employeeMap) {
        this.employeeMap = employeeMap;
    }

    // Build hierarchy
    public Employee buildHierarchy() {
        Employee ceo = null;

        for (Employee emp : employeeMap.values()) {
            if (emp.getManagerId() == null) {
                ceo = emp;
            } else {
                Employee manager = employeeMap.get(emp.getManagerId());
                manager.getSubordinates().add(emp);
            }
        }

        return ceo;
    }

    // Depth check
    public void checkReportingDepth(Employee ceo) {
        dfs(ceo, 0);
    }

    private void dfs(Employee emp, int depth) {
        if (depth > 4) {
//            System.out.println(emp.getFullName() +
//                    " has too long reporting line by " + (depth - 4));

            log.info(emp.getFullName() + " has too long reporting line by " + (depth - 4));
        }

        for (Employee sub : emp.getSubordinates()) {
            dfs(sub, depth + 1);
        }
    }
}
