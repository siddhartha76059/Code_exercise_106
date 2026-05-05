package com.code_exercise_106.demo.Util;

import com.code_exercise_106.demo.Model.Employee;
import com.code_exercise_106.demo.Util.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CSVReaderTest {

    // Helper to create temp CSV file
    private File createTempCSV(String content) throws Exception {
        File file = File.createTempFile("employees", ".csv");
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.close();
        return file;
    }

    @Test
    void testReadEmployees_basicParsing() throws Exception {

        String data =
                "1,John,Doe,60000,\n" +
                        "2,Alice,Smith,50000,1\n" +
                        "3,Bob,Brown,40000,1\n";

        File file = createTempCSV(data);

        Map<Long, Employee> result = CSVReader.readEmployees(file.getAbsolutePath());

        assertEquals(3, result.size());

        Employee emp1 = result.get(1L);
        assertEquals("John Doe", emp1.getFullName());
        assertNull(emp1.getManagerId());

        Employee emp2 = result.get(2L);
        assertEquals(1L, emp2.getManagerId());
    }

    @Test
    void testReadEmployees_handlesEmptyManagerId() throws Exception {

        String data = "1,John,Doe,60000,\n";

        File file = createTempCSV(data);

        Map<Long, Employee> result = CSVReader.readEmployees(file.getAbsolutePath());

        Employee emp = result.get(1L);

        assertNull(emp.getManagerId());
    }

    @Test
    void testReadEmployees_multipleRecords() throws Exception {

        String data =
                "10,Rahul,Sharma,70000,\n" +
                        "20,Priya,Nair,50000,10\n" +
                        "30,Amit,Kapoor,40000,20\n";

        File file = createTempCSV(data);

        Map<Long, Employee> result = CSVReader.readEmployees(file.getAbsolutePath());

        assertEquals(3, result.size());
        assertEquals(10L, result.get(20L).getManagerId());
        assertEquals(20L, result.get(30L).getManagerId());
    }

    @Test
    void testReadEmployees_invalidFile() {
        assertThrows(Exception.class, () -> {
            CSVReader.readEmployees("invalid_file.csv");
        });
    }

    @Test
    void testReadEmployees_invalidDataFormat() throws Exception {

        String data = "1,John,Doe,INVALID,\n";

        File file = createTempCSV(data);

        assertThrows(NumberFormatException.class, () -> {
            CSVReader.readEmployees(file.getAbsolutePath());
        });
    }
}