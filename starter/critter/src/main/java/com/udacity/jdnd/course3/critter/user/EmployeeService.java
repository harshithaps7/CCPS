package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeService {
    EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee empl) {
        return employeeRepository.save(empl);
    }

    public Employee getEmployeeByID(Long id) {
        //System.out.println("Inside service : " + id);
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found, ID: " + id));
    }

}
