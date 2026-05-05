package com.code_exercise_106.demo;

import com.code_exercise_106.demo.Model.Employee;
import com.code_exercise_106.demo.Service.EmployeeHierarchyService;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.logging.*;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeHierarchyServiceTest {

    // Create simple hierarchy
    private Map<Long, Employee> createBasicHierarchy() {
        Map<Long, Employee> map = new HashMap<>();

        Employee ceo = new Employee(1L, "John", "CEO", 100000, null);
        Employee manager = new Employee(2L, "Elon", "Musk", 80000, 1L);
        Employee emp1 = new Employee(3L, "Jeff", "Bezos", 50000, 2L);
        Employee emp2 = new Employee(4L, "Donald", "Trump", 40000, 2L);

        map.put(1L, ceo);
        map.put(2L, manager);
        map.put(3L, emp1);
        map.put(4L, emp2);

        return map;
    }

    //Capture logs
    private List<String> captureLogs(Runnable action) {
        Logger logger = Logger.getLogger(EmployeeHierarchyService.class.getName());
        List<String> logs = new ArrayList<>();

        Handler handler = new Handler() {
            @Override
            public void publish(LogRecord record) {
                logs.add(record.getMessage());
            }
            @Override public void flush() {}
            @Override public void close() {}
        };

        logger.addHandler(handler);
        logger.setUseParentHandlers(false);

        action.run();

        logger.removeHandler(handler);

        return logs;
    }

    // CEO detection
    @Test
    void testBuildHierarchy_returnsCorrectCEO() {
        Map<Long, Employee> map = createBasicHierarchy();
        EmployeeHierarchyService service = new EmployeeHierarchyService(map);

        Employee ceo = service.buildHierarchy();

        assertNotNull(ceo);
        assertEquals(1L, ceo.getId());
    }

    //  Subordinate linking
    @Test
    void testBuildHierarchy_linksSubordinatesCorrectly() {
        Map<Long, Employee> map = createBasicHierarchy();
        EmployeeHierarchyService service = new EmployeeHierarchyService(map);

        Employee ceo = service.buildHierarchy();

        assertEquals(1, ceo.getSubordinates().size());

        Employee manager = ceo.getSubordinates().get(0);
        assertEquals(2, manager.getSubordinates().size());
    }

    // Single employee (edge case)
    @Test
    void testBuildHierarchy_singleEmployee() {
        Map<Long, Employee> map = new HashMap<>();

        Employee ceo = new Employee(1L, "Sid", "Rauta", 100000, null);
        map.put(1L, ceo);

        EmployeeHierarchyService service = new EmployeeHierarchyService(map);

        Employee result = service.buildHierarchy();

        assertEquals(ceo, result);
        assertTrue(result.getSubordinates().isEmpty());
    }

    // Depth violation
    @Test
    void testCheckReportingDepth_detectsViolation() {
        Map<Long, Employee> map = new HashMap<>();

        // Create chain > 4 levels
        Employee ceo = new Employee(1L, "Sid", "Rout", 100000, null);
        Employee e1 = new Employee(2L, "Lina", "singh", 50000, 1L);
        Employee e2 = new Employee(3L, "Rahul", "Verma", 50000, 2L);
        Employee e3 = new Employee(4L, "Priya", "Iyer", 50000, 3L);
        Employee e4 = new Employee(5L, "Jetha", "Lal", 50000, 4L);
        Employee e5 = new Employee(6L, "Daya", "Bhabi", 50000, 5L); // violation

        map.put(1L, ceo);
        map.put(2L, e1);
        map.put(3L, e2);
        map.put(4L, e3);
        map.put(5L, e4);
        map.put(6L, e5);

        EmployeeHierarchyService service = new EmployeeHierarchyService(map);
        Employee root = service.buildHierarchy();

        List<String> logs = captureLogs(() -> service.checkReportingDepth(root));

        assertFalse(logs.isEmpty());
        assertTrue(logs.get(0).contains("too long reporting line"));
    }

    // No depth violation
    @Test
    void testCheckReportingDepth_noViolation() {
        Map<Long, Employee> map = createBasicHierarchy();
        EmployeeHierarchyService service = new EmployeeHierarchyService(map);

        Employee root = service.buildHierarchy();

        List<String> logs = captureLogs(() -> service.checkReportingDepth(root));

        assertTrue(logs.isEmpty());
    }
}
