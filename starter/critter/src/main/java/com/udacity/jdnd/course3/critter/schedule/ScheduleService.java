package com.udacity.jdnd.course3.critter.schedule;


import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {
    ScheduleRepository scheduleRepository;
    PetService petService;
    EmployeeService employeeService;
    CustomerService customerService;
    EmployeeRepository employeeRepository;

    public ScheduleService(EmployeeRepository employeeRepository, ScheduleRepository scheduleRepository, PetService petService, EmployeeService employeeService, CustomerService customerService) {
        this.scheduleRepository = scheduleRepository;
        this.petService = petService;
        this.employeeService = employeeService;
        this.customerService = customerService;
        this.employeeRepository = employeeRepository;
    }

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleByEmployeeId(long employeeId) {
        Employee empl = employeeRepository.getOne(employeeId);
        return scheduleRepository.getAllByEmployeesContains(empl);
    }

    public List<Schedule> getScheduleByPetId(long petId) {
        Pet pet = petService.getPetById(petId);
        return scheduleRepository.getAllByPetsContains(pet);
    }

    public List<Schedule> getScheduleByCustomerId(long customerId) {
        List<Pet> petOfCustomer = petService.getPetsByOwner(customerId);
        List<Schedule> schedules = new ArrayList<>();
        ArrayList<Long> loadedScheduleIds = new ArrayList<>();
        for (Pet pet : petOfCustomer) {
            List<Schedule> petSchedules = getScheduleByPetId(pet.getId());
            for (Schedule schedule : petSchedules) {
                if(!loadedScheduleIds.contains(schedule.getId())) {
                    loadedScheduleIds.add(schedule.getId());
                    schedules.add(schedule);
                }

            }
        }
        return schedules;
    }
}
