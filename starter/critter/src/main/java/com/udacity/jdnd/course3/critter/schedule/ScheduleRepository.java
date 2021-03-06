package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    /*
    @Query(value = "select f from Schedule f WHERE :employeeId in elements(f.employeeIds)")
    List<Schedule> findMed(long employeeId);
    List<Schedule> getAllByPetIdsContains(long petId);

     */

    List<Schedule> getAllByPetsIn(List<Pet> pets);
    List<Schedule> getAllByPetsContains(Pet pet);
    List<Schedule> getAllByEmployeesContains(Employee employee);
}
