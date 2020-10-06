package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /*
    @Query("SELECT e FROM Employee e WHERE e.skills IN :skill")
    public List<Employee> getEmployeeBySkillAndAvailability(Set<EmployeeSkill> skill);

     */


    List<Employee> getAllBySkills(EmployeeSkill skill);
}
