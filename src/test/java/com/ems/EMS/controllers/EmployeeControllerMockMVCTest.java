package com.ems.EMS.controllers;

import com.ems.EMS.beans.Employee;
import com.ems.EMS.services.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "com.ems.EMS")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = {EmployeeControllerMockMVCTest.class})
class EmployeeControllerMockMVCTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    EmployeeController employeeController;

    List<Employee> testEmployees;
    Employee employee;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test @Order(1)
    public void test_getEmployees() throws Exception {
        testEmployees = new ArrayList<>();
        testEmployees.add(new Employee(1, "FirstName_1", "LastName_1"));
        testEmployees.add(new Employee(2, "FirstName_2", "LastName_2"));

        when(employeeService.getAllEmployees()).thenReturn(testEmployees);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/getEmployees"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test @Order(2)
    public void test_getEmployeeById() throws Exception {

        employee = new Employee(2, "FirstName_2", "LastName_2");
        when(employeeService.getEmployeeById(2)).thenReturn(employee);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/getEmployee/{id}", 2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".empId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath(".firstName").value("FirstName_2"))
                .andExpect(MockMvcResultMatchers.jsonPath(".lastName").value("LastName_2"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test @Order(3)
    public void test_getEmployeeByFirstName() throws Exception {

        employee = new Employee(2, "FirstName_2", "LastName_2");
        when(employeeService.getEmployeeByFirstName("FirstName_2")).thenReturn(employee);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/getEmployee/employeeName").param("firstName", "FirstName_2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".empId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath(".firstName").value("FirstName_2"))
                .andExpect(MockMvcResultMatchers.jsonPath(".lastName").value("LastName_2"))
                .andDo(MockMvcResultHandlers.print());


    }

    @Test @Order(4)
    public void test_addEmployee() throws Exception {

        employee = new Employee(3, "FirstName_3", "LastName_3");

        when(employeeService.addEmployee(employee)).thenReturn(employee);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(employee);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post( "/addEmployee")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test @Order(5)
    public void test_updateEmployee() throws Exception {

        employee = new Employee(3, "FirstName_3_updated", "LastName_3_updated");

        when(employeeService.getEmployeeById(3)).thenReturn(employee);
        when(employeeService.updateEmployee(employee)).thenReturn(employee);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(employee);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/updateEmployee/{id}", 3)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".firstName").value("FirstName_3_updated"))
                .andExpect(MockMvcResultMatchers.jsonPath(".lastName").value("LastName_3_updated"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test @Order(6)
    public void test_deleteEmployee() throws Exception {

        employee = new Employee(3, "FirstName_3", "LastName_3");
        when(employeeService.getEmployeeById(3)).thenReturn(employee);

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/deleteEmployee/{id}", 3))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}