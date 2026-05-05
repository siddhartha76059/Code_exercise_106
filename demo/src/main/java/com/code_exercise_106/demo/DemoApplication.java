package com.code_exercise_106.demo;

import com.code_exercise_106.demo.Model.Employee;
import com.code_exercise_106.demo.Service.EmployeeHierarchyService;
import com.code_exercise_106.demo.Service.EmployeeSalaryService;
import com.code_exercise_106.demo.Util.CSVReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws Exception {
		String filePath = "employees.csv";

		// 1. Read CSV
		Map<Long, Employee> employeeMap = CSVReader.readEmployees(filePath);

		// 2. Initialize employee hierarchy service
		EmployeeHierarchyService hierarchyService = new EmployeeHierarchyService(employeeMap);

		// 3.  Initialize employee salary service
		EmployeeSalaryService salaryService = new EmployeeSalaryService(employeeMap);

		// 4. Build hierarchy
		Employee ceo = hierarchyService.buildHierarchy();

		System.out.println("The CEO of the organization - " + ceo.getFullName());

		// 4. Run validations
		//System.out.println("\n=== Reporting Depth ===");
		hierarchyService.checkReportingDepth(ceo);

		//System.out.println("\n=== Salary Validation ===");
		salaryService.validateSalaries();

	}

}
