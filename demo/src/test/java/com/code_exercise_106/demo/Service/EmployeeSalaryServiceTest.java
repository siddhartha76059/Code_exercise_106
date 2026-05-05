package com.code_exercise_106.demo.Service;

import com.code_exercise_106.demo.Model.Employee;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.logging.*;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeSalaryServiceTest {

    private Map<Long, Employee> createTestDataForLessCase() {
        Map<Long, Employee> map = new HashMap<>();

        Employee manager = new Employee(1L, "Donald", "Trump", 50000, null);
        Employee emp1 = new Employee(2L, "Narendra", "Modi", 40000, 1L);
        Employee emp2 = new Employee(3L, "Xi", "Xingping", 50000, 1L);

        map.put(1L, manager);
        map.put(2L, emp1);
        map.put(3L, emp2);

        return map;
    }

    private Map<Long, Employee> createTestDataForMoreCase() {
        Map<Long, Employee> map = new HashMap<>();

        Employee manager = new Employee(1L, "Putin", "Put", 200000, null);
        Employee emp1 = new Employee(2L, "amit", "saha", 30000, 1L);
        Employee emp2 = new Employee(3L, "Jay", "Saha", 35000, 1L);

        map.put(1L, manager);
        map.put(2L, emp1);
        map.put(3L, emp2);

        return map;
    }

    private List<String> captureLogs(Runnable action) {
        Logger logger = Logger.getLogger(EmployeeSalaryService.class.getName());
        List<String> logs = new ArrayList<>();

        Handler handler = new Handler() {
            @Override
            public void publish(LogRecord record) {
                logs.add(record.getMessage());
            }
            @Override public void flush() {}
            @Override public void close() throws SecurityException {}
        };

        logger.addHandler(handler);
        logger.setUseParentHandlers(false);

        action.run();

        logger.removeHandler(handler);

        return logs;
    }

    @Test
    void testSalaryLessThanExpected() {
        Map<Long, Employee> map = createTestDataForLessCase();

        // Build hierarchy first
        EmployeeHierarchyService hierarchyService = new EmployeeHierarchyService(map);
        hierarchyService.buildHierarchy();

        EmployeeSalaryService salaryService = new EmployeeSalaryService(map);

        List<String> logs = captureLogs(salaryService::validateSalaries);

        assertEquals(1, logs.size());
        assertTrue(logs.get(0).contains("LESS"));
    }

    @Test
    void testSalaryMoreThanExpected() {
        Map<Long, Employee> map = createTestDataForMoreCase();

        EmployeeHierarchyService hierarchyService = new EmployeeHierarchyService(map);
        hierarchyService.buildHierarchy();

        EmployeeSalaryService salaryService = new EmployeeSalaryService(map);

        List<String> logs = captureLogs(salaryService::validateSalaries);

        assertEquals(1, logs.size());
        assertTrue(logs.get(0).contains("MORE"));
    }

    @Test
    void testNoViolation() {
        Map<Long, Employee> map = new HashMap<>();

        Employee manager = new Employee(1L, "Yogi", "Aditya", 60000, null);
        Employee emp1 = new Employee(2L, "Rahul", "Gandhi", 40000, 1L);
        Employee emp2 = new Employee(3L, "Ramesh", "Jena", 50000, 1L);

        map.put(1L, manager);
        map.put(2L, emp1);
        map.put(3L, emp2);

        EmployeeHierarchyService hierarchyService = new EmployeeHierarchyService(map);
        hierarchyService.buildHierarchy();

        EmployeeSalaryService salaryService = new EmployeeSalaryService(map);

        List<String> logs = captureLogs(salaryService::validateSalaries);

        assertTrue(logs.isEmpty());
    }

    @Test
    void testManagerWithNoSubordinatesIgnored() {
        Map<Long, Employee> map = new HashMap<>();

        Employee manager = new Employee(1L, "Sid", "Rauta", 100000, null);
        map.put(1L, manager);

        EmployeeSalaryService salaryService = new EmployeeSalaryService(map);

        List<String> logs = captureLogs(salaryService::validateSalaries);

        assertTrue(logs.isEmpty());
    }
}
