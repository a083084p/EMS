package com.ems.EMS.services;

import com.ems.EMS.beans.Employee;
import com.ems.EMS.repositories.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {EmployeeServiceMockitoTest.class})
class EmployeeServiceMockitoTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    public List<Employee> testEmployees;

    @Test @Order(1)
    public void test_getAllEmployees() {

        testEmployees = new ArrayList<>();
        testEmployees.add(new Employee(1, "FirstName_1", "LastName_1"));
        testEmployees.add(new Employee(2, "FirstName_2", "LastName_2"));

        when(employeeRepository.findAll()).thenReturn(testEmployees);

        Assertions.assertEquals(2, employeeService.getAllEmployees().size());

    }

    @Test @Order(2)
    public void test_getEmployeeById() {

        testEmployees = new ArrayList<>();
        testEmployees.add(new Employee(1, "FirstName_1", "LastName_1"));
        testEmployees.add(new Employee(2, "FirstName_2", "LastName_2"));

        when(employeeRepository.findAll()).thenReturn(testEmployees);

        Assertions.assertEquals(1, employeeService.getEmployeeById(1).getEmpId());

    }

    @Test @Order(3)
    public void test_getEmployeeByFirstName() {

        testEmployees = new ArrayList<>();
        testEmployees.add(new Employee(1, "FirstName_1", "LastName_1"));
        testEmployees.add(new Employee(2, "FirstName_2", "LastName_2"));

        when(employeeRepository.findAll()).thenReturn(testEmployees);

        Assertions.assertEquals("FirstName_1",employeeService.getEmployeeByFirstName("FirstName_1").getFirstName());

    }

    @Test @Order(4)
    public void test_addEmployee() {

        Employee employee = new Employee(3, "FirstName_3", "LastName_3");

        when(employeeRepository.save(employee)).thenReturn(employee);

        Assertions.assertEquals(employee, employeeService.addEmployee(employee));

    }

    @Test @Order(5)
    public void test_updateEmployee() {
        Employee employee = new Employee(3, "FirstName_3", "LastName_3");

        when(employeeRepository.save(employee)).thenReturn(employee);

        Assertions.assertEquals(employee, employeeService.updateEmployee(employee));

    }

    @Test @Order(6)
    public void test_deleteEmployee() {
        Employee employee = new Employee(3, "FirstName_3", "LastName_3");
        employeeService.deleteEmployee(employee);

        verify(employeeRepository, times(1)).delete(eq(employee));
        verify(employeeRepository).delete(eq(employee));

    }
}