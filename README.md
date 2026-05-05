# Code_exercise_106

# Employee Hierarchy & Salary Analysis

## Overview

This project is a simple Java-based solution to analyze an organization’s structure using a CSV file.  

It focuses on two main checks:

- Whether managers are paid within an expected range compared to their direct subordinates  
- Whether the reporting hierarchy is too deep  

The goal is to keep the implementation clean, readable, and easy to test.

---

## What the Application Does

### 1. Reads Employee Data from CSV
- Parses a CSV file containing employee details  
- Converts each row into an `Employee` object  
- Stores everything in a `Map<Long, Employee>` for quick access  

---

### 2. Builds the Organization Hierarchy
- Links employees to their managers  
- Forms a tree-like structure in memory  
- Identifies the CEO (employee without a manager)

---

### 3. Validates Manager Salaries
For each manager:
- Calculates the average salary of **direct subordinates**
- Checks if the manager earns:
  - At least **20% more than the average**
  - At most **50% more than the average**

If not, it logs:
- Managers earning less than expected  
- Managers earning more than expected  

---

### 4. Checks Reporting Depth
- Traverses the hierarchy using DFS  
- Flags employees who are more than 4 levels below the CEO  

---

## Project Structure
 - src/main/java/com/code_exercise_106/demo/
     - Model/
         - Employee.java
           
     - Service/
         - EmployeeHierarchyService.java
         - EmployeeSalaryService.java
           
     - Util/
         - CSVReader.java
           
     - Main.java

  
---

## Input Format

CSV format:
Id,FirstName,LastName,Salary,ManagerId

Example:
123,Joe,Doe,60000,
124,Martin,Chekov,45000,123
125,Bob,Ronstad,47000,123
300,Alice,Hasacat,50000,124
305,Brett,Hardleaf,34000,300

- `ManagerId` is empty for the CEO  
- Every other employee must have a valid manager 

---

## Testing

Unit tests are added for:

- CSV parsing  
- Hierarchy building  
- Salary validation  
- Depth validation  

The tests cover both normal scenarios and edge cases like:
- Managers with no subordinates  
- Deep reporting chains  
- Invalid data  

---

## Assumptions

While implementing this, I made a few reasonable assumptions:

- There is only **one CEO** (only one employee without a manager)  
- Every `managerId` refers to a valid employee  
- The hierarchy does not contain cycles  
- CSV data is well-formed  
- Salary values are valid numbers  

---

## Limitations

A few things I intentionally kept simple:

- No validation for invalid or broken hierarchies  
- No handling of duplicate employee IDs  
- CSV parsing is basic (no external library used)  
- Results are logged instead of returned as structured output  

---

## Possible Improvements

If I had more time, I would:

- Add input validation (invalid manager IDs, cycles, etc.)  
- Return structured results instead of logging  
- Use a proper CSV parsing library  
- Expose this as a REST API  
- Add integration tests  

---

## Complexity

All operations are linear:

- CSV parsing → O(n)  
- Hierarchy building → O(n)  
- Salary validation → O(n)  
- Depth check → O(n)  

Overall: **O(n)**

---

## Final Thoughts

I focused on keeping the solution simple and readable rather than over-engineering it.  
The core idea was to build the hierarchy once and reuse it for all validations, which keeps the logic efficient and easy to follow.

---

**Author:** Siddhartha Rauta
