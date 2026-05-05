package com.code_exercise_106.demo.Util;

import com.code_exercise_106.demo.Model.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class CSVReader {

    public static Map<Long, Employee> readEmployees(String filePath) throws Exception {
        Map<Long, Employee> map = new HashMap<>();

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");

            Long id = Long.parseLong(parts[0]);
            String firstName = parts[1];
            String lastName = parts[2];
            double salary = Double.parseDouble(parts[3]);
            Long managerId = parts.length > 4 && !parts[4].isEmpty()
                    ? Long.parseLong(parts[4])
                    : null;

            Employee emp = new Employee(id, firstName, lastName, salary, managerId);
            map.put(id, emp);
        }

        br.close();
        return map;
    }
}
