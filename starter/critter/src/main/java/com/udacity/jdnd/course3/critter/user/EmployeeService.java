package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = getEmployeeByID(employeeId);
        employee.setDaysAvailable(daysAvailable);
        saveEmployee(employee);
    }

    public List<Employee> getEmployeeBySkillAndDate(Set<EmployeeSkill> skills, LocalDate date) {

        DayOfWeek dayOfWeek = date.getDayOfWeek();
        List<Employee> employees = new ArrayList<Employee>();
        for(EmployeeSkill skill : skills) {
            List<Employee> resultSet = employeeRepository.getAllBySkills(skill);
            for (Employee empl : resultSet) {
                if (!employees.contains(empl) && empl.getDaysAvailable().contains(dayOfWeek) && empl.getSkills().containsAll(skills)) {
                    employees.add(empl);
                }
            }
        }
        return employees;
    }
}
