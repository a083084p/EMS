package com.ems.EMS.controllers;

import com.ems.EMS.beans.Employee;
import com.ems.EMS.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping(path = "/getEmployees")
    public ResponseEntity<List<Employee>> getEmployees() {

        try {
            List<Employee> employees = employeeService.getAllEmployees();
            return new ResponseEntity<>(employees, HttpStatus.FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/getEmployee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value="id") int id) {

        try {
            Employee employee = employeeService.getEmployeeById(id);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(path = "/getEmployee/employeeName")
    public ResponseEntity<Employee> getEmployeeByFirstName(@RequestParam(value="firstName") String firstName) {

        try {
            Employee employee = employeeService.getEmployeeByFirstName(firstName);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/addEmployee")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {

        try {
            employee = employeeService.addEmployee(employee);
            return new ResponseEntity<>(employee, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping(path = "/updateEmployee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") int id, @RequestBody Employee employee) {

        try {
            Employee existingEmployee = employeeService.getEmployeeById(id);
            existingEmployee.setEmpId(id);
            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());

            Employee updatedEmployee = employeeService.updateEmployee(existingEmployee);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @DeleteMapping(path = "/deleteEmployee/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable(value="id") int id) {

        Employee employee = null;

        try {
            employee = employeeService.getEmployeeById(id);
            employeeService.deleteEmployee(employee);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

}
