package com.code_exercise_106.demo.Model;

import java.util.ArrayList;
import java.util.List;

public class Employee {

    private Long id;
    private String firstName;
    private String lastName;
    private double salary;
    private Long managerId;

    private List<Employee> subordinates = new ArrayList<>();

    public Employee(Long id, String firstName, String lastName, double salary, Long managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
    }

    public Long getId() { return id; }
    public double getSalary() { return salary; }
    public Long getManagerId() { return managerId; }
    public List<Employee> getSubordinates() { return subordinates; }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}