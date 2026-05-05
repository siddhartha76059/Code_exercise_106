package com.code_exercise_106.demo.Model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void testConstructorAndGetters() {
        Employee emp = new Employee(1L, "John", "Doe", 60000, null);

        assertEquals(1L, emp.getId());
        assertEquals(60000, emp.getSalary());
        assertNull(emp.getManagerId());
    }

    @Test
    void testGetFullName() {
        Employee emp = new Employee(2L, "Alice", "Smith", 50000, 1L);

        assertEquals("Alice Smith", emp.getFullName());
    }

    @Test
    void testSubordinatesListInitiallyEmpty() {
        Employee emp = new Employee(3L, "Bob", "Brown", 40000, 1L);

        assertNotNull(emp.getSubordinates());
        assertTrue(emp.getSubordinates().isEmpty());
    }

    @Test
    void testAddSubordinates() {
        Employee manager = new Employee(1L, "Donald", "Trump", 80000, null);
        Employee emp1 = new Employee(2L, "Oggy", "Shing", 40000, 1L);
        Employee emp2 = new Employee(3L, "Dee", "cochorose", 45000, 1L);

        manager.getSubordinates().add(emp1);
        manager.getSubordinates().add(emp2);

        List<Employee> subs = manager.getSubordinates();

        assertEquals(2, subs.size());
        assertEquals(emp1, subs.get(0));
        assertEquals(emp2, subs.get(1));
    }

    @Test
    void testGetFullNameHandlesNulls() {
        Employee emp = new Employee(4L, null, null, 30000, null);

        // This shows current behavior (not ideal, but predictable)
        assertEquals("null null", emp.getFullName());
    }
}