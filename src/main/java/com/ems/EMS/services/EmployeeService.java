package com.ems.EMS.services;

import com.ems.EMS.beans.Employee;
import com.ems.EMS.controllers.AddResponse;
import com.ems.EMS.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;


    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }


    public Employee getEmployeeById(int id) {

        List<Employee> employees = employeeRepository.findAll();

        return employees.stream().filter(emp -> emp.getEmpId() == id)
                .findFirst()
                .orElse(null);

    }


    public Employee getEmployeeByFirstName(String firstName) {

        List<Employee> employees = employeeRepository.findAll();

        return employees.stream().filter(name -> name.getFirstName()
                        .equalsIgnoreCase(firstName))
                .findFirst()
                .orElse(null);

    }


    public Employee addEmployee(Employee employee) {
        employeeRepository.save(employee);
        return employee;
    }

    public Employee updateEmployee(Employee employee) {

        employeeRepository.save(employee);
        return employee;
    }


    public void deleteEmployee(Employee employee) {

        employeeRepository.delete(employee);
    }



//
//    static HashMap<Integer, Employee> employeeMap;
//
//
//    public EmployeeService() {
//        employeeMap = new HashMap<>();
//
//        Employee emp1 = new Employee(1, "Jeanna", "Spring");
//        Employee emp2 = new Employee(2, "Salem", "Royce");
//        Employee emp3 = new Employee(3, "Kelsi", "Gib");
//
//        employeeMap.put(1, emp1);
//        employeeMap.put(2, emp2);
//        employeeMap.put(3, emp3);
//
//    }
//
//    public List<Employee> getAllEmployees() {
//        return new ArrayList<>(employeeMap.values());
//    }
//
//
//    public Employee getEmployeeById(int id) {
//        return employeeMap.get(id);
//    }
//
//
//    public Employee getEmployeeByFirstName(String firstName) {
//
//        for (int i : employeeMap.keySet()) {
//            if (employeeMap.get(i).getFirstName().equals(firstName)) {
//                return employeeMap.get(i);
//            }
//        }
//
//        return null;
//        }
//
//
//    public Employee addEmployee(Employee employee) {
//        employee.setEmpId(getMaxId() + 1);
//        employeeMap.put(employee.getEmpId(), employee);
//        return employee;
//    }
//
//    public static int getMaxId() {
//        return employeeMap.keySet()
//                .stream().max(Integer::compareTo).get();
//    }
//
//    public Employee updateEmployee(Employee employee) {
//        if (employee.getEmpId() > 0) {
//            employeeMap.put(employee.getEmpId(), employee);
//        }
//
//        return employee;
//    }
//
//
//    public AddResponse deleteEmployee(int id) {
//        employeeMap.remove(id);
//        AddResponse res = new AddResponse();
//        res.setMessage("Employee id deleted!");
//        res.setId(id);
//        return res;
//    }

}
