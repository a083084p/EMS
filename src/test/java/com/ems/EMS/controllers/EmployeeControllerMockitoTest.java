package com.ems.EMS.controllers;

import com.ems.EMS.beans.Employee;
import com.ems.EMS.services.EmployeeService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.when;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {EmployeeControllerMockitoTest.class})
class EmployeeControllerMockitoTest {

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    EmployeeController employeeController;

    List<Employee> testEmployees;
    Employee employee;

    @Test @Order(1)
    public void test_getEmployees() {

        testEmployees = new ArrayList<>();
        testEmployees.add(new Employee(1, "FirstName_1", "LastName_1"));
        testEmployees.add(new Employee(2, "FirstName_2", "LastName_2"));

        when(employeeService.getAllEmployees()).thenReturn(testEmployees);
        ResponseEntity<List<Employee>> res = employeeController.getEmployees();

        Assertions.assertEquals(HttpStatus.FOUND, res.getStatusCode());
        Assertions.assertEquals(2, Objects.requireNonNull(res.getBody()).size());

    }

    @Test @Order(2)
    public void test_getEmployeeById() {

        employee = new Employee(2, "FirstName_2", "LastName_2");

        when(employeeService.getEmployeeById(2)).thenReturn(employee);
        ResponseEntity<Employee> res = employeeController.getEmployeeById(2);

        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
        Assertions.assertEquals(2, Objects.requireNonNull(res.getBody()).getEmpId());
    }

    @Test @Order(3)
    public void test_getEmployeeByFirstName() {

        employee = new Employee(2, "FirstName_2", "LastName_2");

        when(employeeService.getEmployeeByFirstName("FirstName_2")).thenReturn(employee);
        ResponseEntity<Employee> res = employeeController.getEmployeeByFirstName("FirstName_2");

        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
        Assertions.assertEquals("FirstName_2", Objects.requireNonNull(res.getBody()).getFirstName());

    }

    @Test @Order(4)
    public void test_addEmployee() {

        employee = new Employee(3, "FirstName_3", "LastName_3");

        when(employeeService.addEmployee(employee)).thenReturn(employee);
        ResponseEntity<Employee> res = employeeController.addEmployee(employee);

        Assertions.assertEquals(HttpStatus.CREATED, res.getStatusCode());
        Assertions.assertEquals(employee, res.getBody());
    }

    @Test @Order(5)
    public void test_updateEmployee() {

        employee = new Employee(3, "FirstName_3", "LastName_3");

        when(employeeService.getEmployeeById(3)).thenReturn(employee);
        when(employeeService.updateEmployee(employee)).thenReturn(employee);
        ResponseEntity<Employee> res = employeeController.updateEmployee(3, employee);

        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
        Assertions.assertEquals(3, Objects.requireNonNull(res.getBody()).getEmpId());
        Assertions.assertEquals("FirstName_3", res.getBody().getFirstName());
        Assertions.assertEquals("LastName_3", res.getBody().getLastName());
    }

    @Test @Order(6)
    public void test_deleteEmployee() {

        employee = new Employee(3, "FirstName_3", "LastName_3");

        when(employeeService.getEmployeeById(3)).thenReturn(employee);
        ResponseEntity<Employee> res = employeeController.deleteEmployee(3);

        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());

    }
}